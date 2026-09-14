package com.ait.pa.service.imp.excelUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Drop-in replacement for the old:
 *   XLSTransformer transformer = new XLSTransformer();
 *   Workbook wb = transformer.transformXLS(is, datamap);
 *
 * Runs the legacy JXLS 1.0 / POI 3.8 engine in an isolated classloader
 * (see LegacyXlsClassLoader) so template processing (jx:forEach / jx:if
 * tags, etc.) is byte-for-byte unchanged from before, then converts the
 * resulting HSSFWorkbook to a real XSSFWorkbook so callers get .xlsx.
 *
 * Report templates may themselves be authored either as a legacy .xls
 * (what the JXLS 1.0 engine natively understands) or as a modern .xlsx
 * (edited directly in Excel). Either is accepted transparently: an .xlsx
 * template is detected by its file signature and bridged through
 * {@link XssfToHssf} to legacy bytes before being handed to the engine.
 */
public class ExcelTemplateUtil {

    private static volatile Method transformMethod;

    public static Workbook transformXLS(InputStream is, Map<String, Object> datamap) throws Exception {
        byte[] templateBytes = readAll(is);
        if (isOoxml(templateBytes)) {
            HSSFWorkbook legacy;
            try (InputStream xlsxIs = new ByteArrayInputStream(templateBytes)) {
                legacy = XssfToHssf.convert(new XSSFWorkbook(xlsxIs));
            }
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            legacy.write(bos);
            templateBytes = bos.toByteArray();
        }
        byte[] xlsBytes = (byte[]) getTransformMethod().invoke(null, templateBytes, datamap);
        Workbook hssf = WorkbookFactory.create(new ByteArrayInputStream(xlsBytes));
        return HssfToXssf.convert(hssf);
    }

    /**
     * Drop-in replacement for the old
     *   transformer.transformXLS(templateFileName, datamap, destFileName)
     * that reads the template from templateFileName and writes the
     * resulting .xlsx straight to destFileName.
     *
     * templateFileName may be given either WITHOUT extension or with a
     * legacy ".xls"/".xlsx" suffix already appended (most existing callers
     * append ".xls" themselves); either form is normalized before being
     * resolved by {@link #resolveTemplatePath}, which prefers .xlsx over
     * .xls when both exist.
     */
    public static void transformXLS(String templateFileName, Map<String, Object> datamap, String destFileName)
            throws Exception {
        String basePathNoExt = stripKnownExcelExtension(templateFileName);
        Workbook wb;
        try (InputStream is = new FileInputStream(resolveTemplatePath(basePathNoExt))) {
            wb = transformXLS(is, datamap);
        }
        try (OutputStream os = new FileOutputStream(destFileName)) {
            wb.write(os);
        }
    }

    private static String stripKnownExcelExtension(String path) {
        String lower = path.toLowerCase();
        if (lower.endsWith(".xlsx") || lower.endsWith(".xls")) {
            return path.substring(0, path.lastIndexOf('.'));
        }
        return path;
    }

    /**
     * Resolves a report template given its path WITHOUT extension, preferring
     * a modern .xlsx file over the legacy .xls if both are present.
     */
    public static String resolveTemplatePath(String basePathNoExt) throws FileNotFoundException {
        File xlsx = new File(basePathNoExt + ".xlsx");
        if (xlsx.isFile()) {
            return xlsx.getPath();
        }
        File xls = new File(basePathNoExt + ".xls");
        if (xls.isFile()) {
            return xls.getPath();
        }
        throw new FileNotFoundException("Report template not found (tried " + xlsx.getPath() + " and " + xls.getPath() + ")");
    }

    private static boolean isOoxml(byte[] bytes) {
        return bytes.length >= 4 && bytes[0] == 0x50 && bytes[1] == 0x4B && bytes[2] == 0x03 && bytes[3] == 0x04;
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
