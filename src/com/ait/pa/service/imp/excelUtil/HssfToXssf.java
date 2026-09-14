package com.ait.pa.service.imp.excelUtil;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.DataValidationConstraint.ValidationType;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Rebuilds an HSSFWorkbook (.xls, produced by the legacy JXLS1 template engine)
 * as an equivalent XSSFWorkbook (.xlsx): cell values, styles/fonts, merged
 * regions, column widths, embedded pictures, cell comments and data
 * validations (dropdown lists etc.) are copied.
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
            copyDataValidations(srcSheet, dstSheet);
        }

        copyDefinedNames(src, dst);
        return dst;
    }

    private static void copyDataValidations(Sheet srcSheet, Sheet dstSheet) {
        List<? extends DataValidation> validations;
        try {
            validations = srcSheet.getDataValidations();
        } catch (RuntimeException e) {
            // the legacy .xls sheet can contain a record POI no longer recognizes
            // (UnknownRecord) at a position where RecordOrderer can't place/find the
            // DV table (IllegalStateException) - skip validations for this sheet
            // rather than failing the whole export
            return;
        }
        if (validations.isEmpty()) return;
        DataValidationHelper helper = dstSheet.getDataValidationHelper();
        for (DataValidation srcValidation : validations) {
            try {
                DataValidationConstraint srcConstraint = srcValidation.getValidationConstraint();
                DataValidationConstraint dstConstraint = copyConstraint(helper, srcConstraint);
                if (dstConstraint == null) continue; // ANY / unsupported or unreadable constraint

                DataValidation dstValidation = helper.createValidation(dstConstraint, srcValidation.getRegions());
                dstValidation.setEmptyCellAllowed(srcValidation.getEmptyCellAllowed());
                dstValidation.setShowErrorBox(srcValidation.getShowErrorBox());
                dstValidation.setShowPromptBox(srcValidation.getShowPromptBox());
                // XSSFDataValidation's suppressDropDownArrow setter writes the OOXML
                // showDropDown attribute inverted from HSSF's semantics (verified: on
                // POI 5.2.2, setSuppressDropDownArrow(false) - i.e. "don't suppress" -
                // actually emits showDropDown="true", which the OOXML spec defines as
                // suppressed/hidden). Negate when carrying the flag over so a visible
                // in-cell dropdown in the .xls source stays visible in the .xlsx output.
                dstValidation.setSuppressDropDownArrow(!srcValidation.getSuppressDropDownArrow());
                if (srcValidation.getErrorBoxText() != null) {
                    dstValidation.createErrorBox(srcValidation.getErrorBoxTitle(), srcValidation.getErrorBoxText());
                }
                if (srcValidation.getPromptBoxText() != null) {
                    dstValidation.createPromptBox(srcValidation.getPromptBoxTitle(), srcValidation.getPromptBoxText());
                }
                dstSheet.addValidationData(dstValidation);
            } catch (RuntimeException e) {
                // the legacy HSSF round-trip can leave a validation whose formula
                // POI can no longer reconstruct (e.g. a literal-value text-length
                // constraint) - skip just that one rather than losing the whole sheet
            }
        }
    }

    private static DataValidationConstraint copyConstraint(DataValidationHelper helper, DataValidationConstraint c) {
        String[] explicitValues = c.getExplicitListValues();
        if (explicitValues != null) {
            return helper.createExplicitListConstraint(explicitValues);
        }
        String formula1 = c.getFormula1();
        if (formula1 == null || formula1.trim().isEmpty()) {
            return null; // nothing usable to enforce
        }
        switch (c.getValidationType()) {
            case ValidationType.LIST:
                // formula1 here is the named range / sheet formula (e.g. "dept"),
                // which ExcelExportCtroller.createName() repoints to TemplateCode.
                return helper.createFormulaListConstraint(formula1);
            case ValidationType.INTEGER:
                return helper.createIntegerConstraint(c.getOperator(), formula1, c.getFormula2());
            case ValidationType.DECIMAL:
                return helper.createDecimalConstraint(c.getOperator(), formula1, c.getFormula2());
            case ValidationType.TEXT_LENGTH:
                return helper.createTextLengthConstraint(c.getOperator(), formula1, c.getFormula2());
            case ValidationType.DATE:
                return helper.createDateConstraint(c.getOperator(), formula1, c.getFormula2(), null);
            case ValidationType.TIME:
                return helper.createTimeConstraint(c.getOperator(), formula1, c.getFormula2());
            case ValidationType.FORMULA:
                return helper.createCustomConstraint(formula1);
            default:
                return null;
        }
    }

    private static void copyDefinedNames(Workbook src, Workbook dst) {
        Set<String> created = new HashSet<>();
        for (Name srcName : src.getAllNames()) {
            String formula;
            try {
                formula = srcName.getRefersToFormula();
            } catch (RuntimeException e) {
                // some legacy HSSFName entries are function/command names rather than
                // plain named ranges - getRefersToFormula() rejects those outright
                continue;
            }
            if (formula == null || formula.trim().isEmpty()) {
                // Legacy JXLS output leaves behind orphaned sheet-scoped duplicates
                // (same name, no formula) alongside the real global name. Writing
                // these through corrupts the .xlsx (Excel then strips ALL named
                // ranges on open: "Removed Records: Named range").
                continue;
            }
            String key = srcName.getNameName().toLowerCase() + "@" + srcName.getSheetIndex();
            if (!created.add(key)) {
                continue; // duplicate name at the same scope - keep the first
            }
            try {
                Name dstName = dst.createName();
                dstName.setNameName(srcName.getNameName());
                if (srcName.getSheetIndex() >= 0) {
                    dstName.setSheetIndex(srcName.getSheetIndex());
                }
                dstName.setRefersToFormula(formula);
            } catch (RuntimeException e) {
                // still invalid in the destination workbook - skip it
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
            if (dstStyle instanceof XSSFCellStyle) {
                final XSSFCellStyle dstXStyle = (XSSFCellStyle) dstStyle;
                setIfResolved(srcWb, srcStyle.getTopBorderColor(), new java.util.function.Consumer<XSSFColor>() {
                    public void accept(XSSFColor c) { dstXStyle.setTopBorderColor(c); }
                });
                setIfResolved(srcWb, srcStyle.getBottomBorderColor(), new java.util.function.Consumer<XSSFColor>() {
                    public void accept(XSSFColor c) { dstXStyle.setBottomBorderColor(c); }
                });
                setIfResolved(srcWb, srcStyle.getLeftBorderColor(), new java.util.function.Consumer<XSSFColor>() {
                    public void accept(XSSFColor c) { dstXStyle.setLeftBorderColor(c); }
                });
                setIfResolved(srcWb, srcStyle.getRightBorderColor(), new java.util.function.Consumer<XSSFColor>() {
                    public void accept(XSSFColor c) { dstXStyle.setRightBorderColor(c); }
                });
            }

            dstStyle.setFillPattern(srcStyle.getFillPattern());
            dstStyle.setFillForegroundColor(srcStyle.getFillForegroundColor());
            dstStyle.setFillBackgroundColor(srcStyle.getFillBackgroundColor());
            if (dstStyle instanceof XSSFCellStyle) {
                final XSSFCellStyle dstXStyle2 = (XSSFCellStyle) dstStyle;
                setIfResolved(srcWb, srcStyle.getFillForegroundColor(), new java.util.function.Consumer<XSSFColor>() {
                    public void accept(XSSFColor c) { dstXStyle2.setFillForegroundColor(c); }
                });
                setIfResolved(srcWb, srcStyle.getFillBackgroundColor(), new java.util.function.Consumer<XSSFColor>() {
                    public void accept(XSSFColor c) { dstXStyle2.setFillBackgroundColor(c); }
                });
            }

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
                if (dstFont instanceof XSSFFont) {
                    XSSFColor resolved = resolveXssfColor(srcWb, srcFont.getColor());
                    if (resolved != null) {
                        ((XSSFFont) dstFont).setColor(resolved);
                    }
                }
                fontCache.put(fontKey, dstFont);
            }
            dstStyle.setFont(dstFont);

            styleCache.put(key, dstStyle);
        }
        dstCell.setCellStyle(dstStyle);
    }

    /**
     * The plain index-based border/fill setters (called above) place the
     * source's HSSFPalette slot number into the XSSF style verbatim. That
     * slot number is only meaningful together with the specific HSSFWorkbook
     * that defined it (custom palette slots don't carry a fixed universal
     * RGB); reading it back later resolves the index against XSSF's own
     * default indexed-color table instead, giving the wrong color for any
     * slot that was redefined to a custom RGB. Overlaying the actual
     * resolved RGB (fetched from srcWb's own palette right here, while it's
     * still available) fixes that; when the index is a standard, unmodified
     * palette color the RGB matches anyway, so this is a no-op for ordinary
     * legacy .xls templates.
     */
    private static void setIfResolved(Workbook srcWb, short colorIndex, java.util.function.Consumer<XSSFColor> setter) {
        XSSFColor resolved = resolveXssfColor(srcWb, colorIndex);
        if (resolved != null) {
            setter.accept(resolved);
        }
    }

    private static XSSFColor resolveXssfColor(Workbook srcWb, short colorIndex) {
        if (!(srcWb instanceof HSSFWorkbook) || colorIndex < 0) {
            return null;
        }
        HSSFColor legacyColor = ((HSSFWorkbook) srcWb).getCustomPalette().getColor(colorIndex);
        if (legacyColor == null) {
            return null;
        }
        short[] triplet = legacyColor.getTriplet();
        if (triplet == null) {
            return null;
        }
        return new XSSFColor(new byte[] { (byte) triplet[0], (byte) triplet[1], (byte) triplet[2] }, null);
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
