package com.ait.pa.service.imp.excelUtil;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.HashMap;
import java.util.Map;

/**
 * Rebuilds an HSSFWorkbook (.xls, produced by the legacy JXLS1 template engine)
 * as an equivalent XSSFWorkbook (.xlsx): cell values, styles/fonts, merged
 * regions, column widths, embedded pictures and cell comments are copied.
 * Minor legacy drawing artifacts (form controls, OLE objects, free shapes)
 * carry no report content and are intentionally skipped.
 */
public class HssfToXssf {

    public static Workbook convert(Workbook src) {
        if (!(src instanceof HSSFWorkbook)) {
            return src; // already xlsx (or something else) - nothing to do
        }
        XSSFWorkbook dst = new XSSFWorkbook();
        Map<Integer, CellStyle> styleCache = new HashMap<>();
        Map<Integer, Font> fontCache = new HashMap<>();

        for (int s = 0; s < src.getNumberOfSheets(); s++) {
            Sheet srcSheet = src.getSheetAt(s);
            Sheet dstSheet = dst.createSheet(src.getSheetName(s));

            int maxCol = 0;
            for (Row r : srcSheet) maxCol = Math.max(maxCol, r.getLastCellNum());
            for (int c = 0; c < maxCol; c++) {
                dstSheet.setColumnWidth(c, srcSheet.getColumnWidth(c));
            }

            for (int m = 0; m < srcSheet.getNumMergedRegions(); m++) {
                CellRangeAddress region = srcSheet.getMergedRegion(m);
                dstSheet.addMergedRegion(new CellRangeAddress(
                        region.getFirstRow(), region.getLastRow(), region.getFirstColumn(), region.getLastColumn()));
            }

            for (Row srcRow : srcSheet) {
                Row dstRow = dstSheet.createRow(srcRow.getRowNum());
                dstRow.setHeight(srcRow.getHeight());
                for (Cell srcCell : srcRow) {
                    Cell dstCell = dstRow.createCell(srcCell.getColumnIndex());
                    copyCellStyle(src, dst, srcCell, dstCell, styleCache, fontCache);
                    copyCellValue(srcCell, dstCell);
                }
            }

            copyDrawingShapes(dst, srcSheet, dstSheet);
        }

        copyDefinedNames(src, dst);
        return dst;
    }

    private static void copyDefinedNames(Workbook src, Workbook dst) {
        for (Name srcName : src.getAllNames()) {
            try {
                Name dstName = dst.createName();
                dstName.setNameName(srcName.getNameName());
                dstName.setRefersToFormula(srcName.getRefersToFormula());
                if (srcName.getSheetIndex() >= 0) {
                    dstName.setSheetIndex(srcName.getSheetIndex());
                }
            } catch (RuntimeException e) {
                // skip names the source workbook itself could not resolve/duplicate names
            }
        }
    }

    private static void copyDrawingShapes(Workbook dstWb, Sheet srcSheet, Sheet dstSheet) {
        Drawing<?> srcDrawing = srcSheet.getDrawingPatriarch();
        if (srcDrawing == null) return;
        Drawing<?> dstDrawing = dstSheet.createDrawingPatriarch();
        CreationHelper helper = dstWb.getCreationHelper();
        for (Object shapeObj : srcDrawing) {
            if (shapeObj instanceof Picture) {
                Picture srcPic = (Picture) shapeObj;
                PictureData pictureData = srcPic.getPictureData();
                int pictureIdx = dstWb.addPicture(pictureData.getData(), pictureData.getPictureType());
                ClientAnchor srcAnchor = srcPic.getClientAnchor();
                ClientAnchor dstAnchor = dstDrawing.createAnchor(
                        srcAnchor.getDx1(), srcAnchor.getDy1(), srcAnchor.getDx2(), srcAnchor.getDy2(),
                        srcAnchor.getCol1(), srcAnchor.getRow1(), srcAnchor.getCol2(), srcAnchor.getRow2());
                dstDrawing.createPicture(dstAnchor, pictureIdx);
            } else if (shapeObj instanceof Comment) {
                try {
                    Comment srcComment = (Comment) shapeObj;
                    Row targetRow = dstSheet.getRow(srcComment.getRow());
                    Cell targetCell = targetRow == null ? null : targetRow.getCell(srcComment.getColumn());
                    if (targetCell == null || targetCell.getCellComment() != null) {
                        continue; // legacy file has an overlapping/duplicate comment; keep the first one
                    }
                    ClientAnchor srcAnchor = srcComment.getClientAnchor();
                    ClientAnchor dstAnchor = helper.createClientAnchor();
                    dstAnchor.setCol1(srcAnchor.getCol1());
                    dstAnchor.setRow1(srcAnchor.getRow1());
                    dstAnchor.setCol2(srcAnchor.getCol2());
                    dstAnchor.setRow2(srcAnchor.getRow2());
                    Comment dstComment = dstDrawing.createCellComment(dstAnchor);
                    dstComment.setString(helper.createRichTextString(srcComment.getString().getString()));
                    dstComment.setAuthor(srcComment.getAuthor());
                    dstComment.setVisible(srcComment.isVisible());
                    targetCell.setCellComment(dstComment);
                } catch (RuntimeException e) {
                    // legacy file has an inconsistent/duplicate comment; skip it rather than fail the whole report
                }
            }
            // other legacy drawing artifacts (form controls, OLE objects, free shapes)
            // carry no report content and are intentionally not copied
        }
    }

    private static void copyCellStyle(Workbook srcWb, Workbook dstWb, Cell srcCell, Cell dstCell,
            Map<Integer, CellStyle> styleCache, Map<Integer, Font> fontCache) {
        CellStyle srcStyle = srcCell.getCellStyle();
        int key = srcStyle.getIndex();
        CellStyle dstStyle = styleCache.get(key);
        if (dstStyle == null) {
            dstStyle = dstWb.createCellStyle();

            short srcFmtIdx = srcStyle.getDataFormat();
            if (srcFmtIdx < 164) {
                // built-in format: same numeric id in both HSSF and OOXML numFmt schemes
                dstStyle.setDataFormat(srcFmtIdx);
            } else {
                dstStyle.setDataFormat(dstWb.createDataFormat().getFormat(srcStyle.getDataFormatString()));
            }

            dstStyle.setAlignment(srcStyle.getAlignment());
            dstStyle.setVerticalAlignment(srcStyle.getVerticalAlignment());
            dstStyle.setWrapText(srcStyle.getWrapText());
            dstStyle.setRotation(srcStyle.getRotation());
            dstStyle.setIndention(srcStyle.getIndention());

            dstStyle.setBorderTop(srcStyle.getBorderTop());
            dstStyle.setBorderBottom(srcStyle.getBorderBottom());
            dstStyle.setBorderLeft(srcStyle.getBorderLeft());
            dstStyle.setBorderRight(srcStyle.getBorderRight());
            dstStyle.setTopBorderColor(srcStyle.getTopBorderColor());
            dstStyle.setBottomBorderColor(srcStyle.getBottomBorderColor());
            dstStyle.setLeftBorderColor(srcStyle.getLeftBorderColor());
            dstStyle.setRightBorderColor(srcStyle.getRightBorderColor());

            dstStyle.setFillPattern(srcStyle.getFillPattern());
            dstStyle.setFillForegroundColor(srcStyle.getFillForegroundColor());
            dstStyle.setFillBackgroundColor(srcStyle.getFillBackgroundColor());

            dstStyle.setLocked(srcStyle.getLocked());
            dstStyle.setHidden(srcStyle.getHidden());

            int fontKey = srcStyle.getFontIndexAsInt();
            Font dstFont = fontCache.get(fontKey);
            if (dstFont == null) {
                Font srcFont = srcWb.getFontAt(fontKey);
                dstFont = dstWb.createFont();
                dstFont.setFontName(srcFont.getFontName());
                dstFont.setFontHeightInPoints(srcFont.getFontHeightInPoints());
                dstFont.setBold(srcFont.getBold());
                dstFont.setItalic(srcFont.getItalic());
                dstFont.setStrikeout(srcFont.getStrikeout());
                dstFont.setUnderline(srcFont.getUnderline());
                dstFont.setTypeOffset(srcFont.getTypeOffset());
                dstFont.setColor(srcFont.getColor());
                fontCache.put(fontKey, dstFont);
            }
            dstStyle.setFont(dstFont);

            styleCache.put(key, dstStyle);
        }
        dstCell.setCellStyle(dstStyle);
    }

    private static void copyCellValue(Cell srcCell, Cell dstCell) {
        switch (srcCell.getCellType()) {
            case STRING:
                dstCell.setCellValue(srcCell.getStringCellValue());
                break;
            case NUMERIC:
                dstCell.setCellValue(srcCell.getNumericCellValue());
                break;
            case BOOLEAN:
                dstCell.setCellValue(srcCell.getBooleanCellValue());
                break;
            case FORMULA:
                dstCell.setCellFormula(srcCell.getCellFormula());
                break;
            case BLANK:
                dstCell.setBlank();
                break;
            default:
                break;
        }
    }
}
