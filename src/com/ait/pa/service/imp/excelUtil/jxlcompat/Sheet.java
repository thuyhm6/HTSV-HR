package com.ait.pa.service.imp.excelUtil.jxlcompat;

import org.apache.poi.ss.usermodel.DateUtil;

/** Stand-in for jxl.Sheet backed by a POI sheet. */
public class Sheet {

    private final org.apache.poi.ss.usermodel.Sheet poiSheet;
    private final int columnCount;

    Sheet(org.apache.poi.ss.usermodel.Sheet poiSheet) {
        this.poiSheet = poiSheet;
        int maxCols = 0;
        for (org.apache.poi.ss.usermodel.Row row : poiSheet) {
            maxCols = Math.max(maxCols, row.getLastCellNum());
        }
        this.columnCount = Math.max(maxCols, 0);
    }

    public int getRows() {
        return poiSheet.getLastRowNum() + 1;
    }

    public Cell[] getRow(int rowIndex) {
        org.apache.poi.ss.usermodel.Row poiRow = poiSheet.getRow(rowIndex);
        Cell[] cells = new Cell[columnCount];
        for (int c = 0; c < columnCount; c++) {
            org.apache.poi.ss.usermodel.Cell poiCell = poiRow == null ? null : poiRow.getCell(c);
            cells[c] = wrap(poiCell);
        }
        return cells;
    }

    public Cell getCell(int col, int row) {
        org.apache.poi.ss.usermodel.Row poiRow = poiSheet.getRow(row);
        org.apache.poi.ss.usermodel.Cell poiCell = poiRow == null ? null : poiRow.getCell(col);
        return wrap(poiCell);
    }

    private static Cell wrap(org.apache.poi.ss.usermodel.Cell poiCell) {
        if (poiCell != null
                && poiCell.getCellType() == org.apache.poi.ss.usermodel.CellType.NUMERIC
                && DateUtil.isCellDateFormatted(poiCell)) {
            return new DateCell(poiCell);
        }
        return new Cell(poiCell);
    }
}
