package com.ait.pa.service.imp.excelUtil.jxlcompat;

import java.util.Date;

/** Stand-in for jxl.DateCell. */
public class DateCell extends Cell {

    DateCell(org.apache.poi.ss.usermodel.Cell poiCell) {
        super(poiCell);
    }

    public Date getDate() {
        return poiCell.getDateCellValue();
    }
}
