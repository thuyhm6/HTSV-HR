package com.ait.pa.service.imp.excelUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * Drop-in replacement for the old:
 *   XLSTransformer transformer = new XLSTransformer();
 *   Workbook wb = transformer.transformXLS(is, datamap);
 *
 * Runs the legacy JXLS 1.0 / POI 3.8 engine in an isolated classloader
 * (see LegacyXlsClassLoader) so template processing (jx:forEach / jx:if
 * tags, etc.) is byte-for-byte unchanged from before, then converts the
 * resulting HSSFWorkbook to a real XSSFWorkbook so callers get .xlsx.
 */
public class ExcelTemplateUtil {

    private static volatile Method transformMethod;

    public static Workbook transformXLS(InputStream is, Map<String, Object> datamap) throws Exception {
        byte[] templateBytes = readAll(is);
        byte[] xlsBytes = (byte[]) getTransformMethod().invoke(null, templateBytes, datamap);
        Workbook hssf = WorkbookFactory.create(new ByteArrayInputStream(xlsBytes));
        return HssfToXssf.convert(hssf);
    }

    /**
     * Drop-in replacement for the old
     *   transformer.transformXLS(templateFileName, datamap, destFileName)
     * that reads the template from templateFileName and writes the
     * resulting .xlsx straight to destFileName.
     */
    public static void transformXLS(String templateFileName, Map<String, Object> datamap, String destFileName)
            throws Exception {
        Workbook wb;
        try (InputStream is = new FileInputStream(templateFileName)) {
            wb = transformXLS(is, datamap);
        }
        try (OutputStream os = new FileOutputStream(destFileName)) {
            wb.write(os);
        }
    }

    private static Method getTransformMethod() throws Exception {
        if (transformMethod == null) {
            synchronized (ExcelTemplateUtil.class) {
                if (transformMethod == null) {
                    File isolatedLibDir = new File(getWebInfDir(), "isolated-lib");
                    LegacyXlsClassLoader loader = LegacyXlsClassLoader.getInstance(isolatedLibDir);
                    Class<?> workerClass = loader.loadClass("com.ait.legacy.LegacyXlsTransformWorker");
                    transformMethod = workerClass.getMethod("transform", byte[].class, Map.class);
                }
            }
        }
        return transformMethod;
    }

    private static File getWebInfDir() {
        try {
            File location = new File(ExcelTemplateUtil.class.getProtectionDomain()
                    .getCodeSource().getLocation().toURI());
            // Depending on the classloader, location may be WEB-INF/classes,
            // a jar in WEB-INF/lib, or (as observed on some Tomcat/Eclipse
            // setups) this class's own package directory under WEB-INF/classes.
            // Walk up until we find the "classes" or "lib" root and return its
            // parent (WEB-INF), instead of assuming a fixed number of levels.
            for (File dir = location.isDirectory() ? location : location.getParentFile();
                    dir != null; dir = dir.getParentFile()) {
                if ("classes".equals(dir.getName()) || "lib".equals(dir.getName())) {
                    return dir.getParentFile();
                }
                if ("WEB-INF".equals(dir.getName())) {
                    return dir;
                }
            }
            throw new IllegalStateException("Cannot locate WEB-INF from codeSource: " + location);
        } catch (URISyntaxException e) {
            throw new IllegalStateException("Cannot resolve WEB-INF directory", e);
        }
    }

    private static byte[] readAll(InputStream is) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[8192];
        int n;
        while ((n = is.read(buf)) != -1) {
            bos.write(buf, 0, n);
        }
        return bos.toByteArray();
    }
}
