package com.ait.legacy;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Map;

import net.sf.jxls.transformer.XLSTransformer;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * Runs the legacy JXLS 1.0 / POI 3.8 template engine, unchanged from the
 * behaviour the project has always relied on (jx:forEach / jx:if tags
 * written directly into cells). This class is compiled and loaded only
 * through LegacyXlsClassLoader (see WebRoot/WEB-INF/isolated-lib) so it
 * never touches the POI 5.x classes used by the rest of the webapp.
 *
 * Rebuild: javac -cp "isolated-lib/*" -d out src/com/ait/legacy/*.java
 *          jar cf legacy-xls-worker.jar -C out .
 */
public class LegacyXlsTransformWorker {

    public static byte[] transform(byte[] templateBytes, Map<String, Object> beans) throws Exception {
        XLSTransformer transformer = new XLSTransformer();
        try (InputStream is = new ByteArrayInputStream(templateBytes)) {
            Workbook wb = transformer.transformXLS(is, beans);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            wb.write(bos);
            return bos.toByteArray();
        }
    }
}
