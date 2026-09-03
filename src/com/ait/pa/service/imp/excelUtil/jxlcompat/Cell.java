package com.ait.pa.service.imp.excelUtil.jxlcompat;

import org.apache.poi.ss.usermodel.DataFormatter;

/**
 * Stand-in for jxl.Cell backed by a POI cell, so the many upload-parsing
 * methods in ExcelUtilSerImp (written against jxl's Workbook/Sheet/Cell API)
 * keep working unchanged while reading through POI's WorkbookFactory
 * (which, unlike jxl, understands both .xls and .xlsx).
 */
public class Cell {

    private static final DataFormatter FORMATTER = new DataFormatter();

    final org.apache.poi.ss.usermodel.Cell poiCell;

    Cell(org.apache.poi.ss.usermodel.Cell poiCell) {
        this.poiCell = poiCell;
    }

    public String getContents() {
        if (poiCell == null) {
            return "";
        }
        return FORMATTER.formatCellValue(poiCell);
    }

    public CellType getType() {
        if (poiCell == null || poiCell.getCellType() == org.apache.poi.ss.usermodel.CellType.BLANK) {
            return CellType.EMPTY;
        }
        return CellType.OTHER;
    }
}
