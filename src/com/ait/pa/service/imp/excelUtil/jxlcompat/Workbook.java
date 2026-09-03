package com.ait.pa.service.imp.excelUtil.jxlcompat;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * Stand-in for jxl.Workbook#getWorkbook(File), backed by POI's
 * WorkbookFactory so uploaded .xls AND .xlsx files are both readable
 * (jxl itself only ever understood .xls).
 */
public class Workbook {

    private final org.apache.poi.ss.usermodel.Workbook poiWorkbook;

    private Workbook(org.apache.poi.ss.usermodel.Workbook poiWorkbook) {
        this.poiWorkbook = poiWorkbook;
    }

    public static Workbook getWorkbook(File file) throws IOException {
        try (InputStream is = new FileInputStream(file)) {
            return new Workbook(WorkbookFactory.create(is));
        } catch (Exception e) {
            throw new IOException("Cannot read uploaded workbook: " + file, e);
        }
    }

    public Sheet getSheet(int index) {
        return new Sheet(poiWorkbook.getSheetAt(index));
    }
}
