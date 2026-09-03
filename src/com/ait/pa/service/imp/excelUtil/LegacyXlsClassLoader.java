package com.ait.pa.service.imp.excelUtil;

import java.io.File;
import java.io.FilenameFilter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Child-first classloader isolating the legacy POI 3.8 / JXLS 1.0 stack
 * (jars in WEB-INF/isolated-lib) from the POI 5.x stack used by the rest of
 * the webapp (WEB-INF/lib). Both define org.apache.poi.* / net.sf.jxls.*
 * classes with incompatible APIs (e.g. Cell#getCellType() int vs enum), so
 * they cannot share one classloader. Only this isolated stack still knows
 * how to render the project's .xls report templates (jx:forEach/jx:if tags
 * written directly in cells) exactly as before; see ExcelTemplateUtil.
 */
public class LegacyXlsClassLoader extends URLClassLoader {

    private static final String[] CHILD_FIRST_PREFIXES = {
            "org.apache.poi.",
            "net.sf.jxls."
    };

    private static volatile LegacyXlsClassLoader instance;

    private LegacyXlsClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    public static LegacyXlsClassLoader getInstance(File isolatedLibDir) throws MalformedURLException {
        if (instance == null) {
            synchronized (LegacyXlsClassLoader.class) {
                if (instance == null) {
                    File[] jars = isolatedLibDir.listFiles(new FilenameFilter() {
                        public boolean accept(File dir, String name) {
                            return name.toLowerCase().endsWith(".jar");
                        }
                    });
                    if (jars == null || jars.length == 0) {
                        throw new IllegalStateException("Isolated lib dir not found or empty: " + isolatedLibDir);
                    }
                    URL[] urls = new URL[jars.length];
                    for (int i = 0; i < jars.length; i++) {
                        urls[i] = jars[i].toURI().toURL();
                    }
                    instance = new LegacyXlsClassLoader(urls, LegacyXlsClassLoader.class.getClassLoader());
                }
            }
        }
        return instance;
    }

    @Override
    protected synchronized Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        Class<?> loaded = findLoadedClass(name);
        if (loaded == null) {
            boolean childFirst = false;
            for (String prefix : CHILD_FIRST_PREFIXES) {
                if (name.startsWith(prefix)) {
                    childFirst = true;
                    break;
                }
            }
            if (childFirst) {
                try {
                    loaded = findClass(name);
                } catch (ClassNotFoundException e) {
                    loaded = super.loadClass(name, false);
                }
            } else {
                loaded = super.loadClass(name, false);
            }
        }
        if (resolve) {
            resolveClass(loaded);
        }
        return loaded;
    }
}
