package com.ait.pa.service.imp.excelUtil;

import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;

import java.util.HashMap;
import java.util.Map;

/**
 * Rebuilds an XSSFWorkbook (.xlsx, edited directly in Excel) as an equivalent
 * HSSFWorkbook (.xls): cell values, styles/fonts, merged regions, column
 * widths and row heights are copied. Used so a JXLS 1.0 report template can
 * be authored/maintained as a modern .xlsx file while still being fed to the
 * legacy (POI 3.8) template engine, which only understands the legacy binary
 * format.
 * Reverse counterpart of {@link HssfToXssf}. XSSF fill/border/font colors are
 * usually literal RGB or theme colors rather than legacy palette indices, so
 * the plain short-based getters (getFillForegroundColor() and friends) can't
 * be trusted here - they silently return 0 ("black") instead of throwing.
 * Colors are resolved from the actual XSSFColor/RGB value instead and mapped
 * onto a matching (or newly added) slot in the destination workbook's custom
 * HSSF palette, so real colors survive instead of turning solid black.
 */
public class XssfToHssf {

    // HSSF's custom palette only has 56 redefinable slots (indices 8-63); a
    // freshly created HSSFWorkbook has all of them pre-filled with the
    // standard color set, so HSSFPalette.addColor() has nowhere "free" to
    // put a new exact RGB and always throws. Slots are instead claimed
    // directly (highest index first, away from the commonly-implied
    // low-index standards like black/white/red/...) via setColorAtIndex();
    // nextCustomColorIndex threads that per-workbook allocation cursor
    // through a single convert() call.
    private static final short PALETTE_FIRST_COLOR_INDEX = 8;
    private static final short PALETTE_LAST_COLOR_INDEX = 63;

    public static HSSFWorkbook convert(Workbook src) {
        if (src instanceof HSSFWorkbook) {
            return (HSSFWorkbook) src; // already legacy format - nothing to do
        }
        HSSFWorkbook dst = new HSSFWorkbook();
        Map<Integer, CellStyle> styleCache = new HashMap<>();
        Map<Integer, Font> fontCache = new HashMap<>();
        short[] nextCustomColorIndex = { PALETTE_LAST_COLOR_INDEX };

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
                dstRow.setHeightInPoints(srcRow.getHeightInPoints());
                for (Cell srcCell : srcRow) {
                    Cell dstCell = dstRow.createCell(srcCell.getColumnIndex());
                    copyCellStyle(src, dst, srcCell, dstCell, styleCache, fontCache, nextCustomColorIndex);
                    copyCellValue(srcCell, dstCell);
                }
            }
        }
        return dst;
    }

    private static void copyCellStyle(Workbook srcWb, HSSFWorkbook dstWb, Cell srcCell, Cell dstCell,
            Map<Integer, CellStyle> styleCache, Map<Integer, Font> fontCache, short[] nextCustomColorIndex) {
        CellStyle srcStyle = srcCell.getCellStyle();
        XSSFCellStyle srcXStyle = (srcStyle instanceof XSSFCellStyle) ? (XSSFCellStyle) srcStyle : null;
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
            if (srcXStyle != null) {
                final CellStyle fDstStyle = dstStyle;
                setIfResolved(dstWb, srcXStyle.getTopBorderXSSFColor(), nextCustomColorIndex, new ShortColorSetter() {
                    public void set(short colorIndex) { fDstStyle.setTopBorderColor(colorIndex); }
                });
                setIfResolved(dstWb, srcXStyle.getBottomBorderXSSFColor(), nextCustomColorIndex, new ShortColorSetter() {
                    public void set(short colorIndex) { fDstStyle.setBottomBorderColor(colorIndex); }
                });
                setIfResolved(dstWb, srcXStyle.getLeftBorderXSSFColor(), nextCustomColorIndex, new ShortColorSetter() {
                    public void set(short colorIndex) { fDstStyle.setLeftBorderColor(colorIndex); }
                });
                setIfResolved(dstWb, srcXStyle.getRightBorderXSSFColor(), nextCustomColorIndex, new ShortColorSetter() {
                    public void set(short colorIndex) { fDstStyle.setRightBorderColor(colorIndex); }
                });
            }

            dstStyle.setFillPattern(srcStyle.getFillPattern());
            if (srcXStyle != null) {
                final CellStyle fDstStyle2 = dstStyle;
                setIfResolved(dstWb, srcXStyle.getFillForegroundXSSFColor(), nextCustomColorIndex, new ShortColorSetter() {
                    public void set(short colorIndex) { fDstStyle2.setFillForegroundColor(colorIndex); }
                });
                setIfResolved(dstWb, srcXStyle.getFillBackgroundXSSFColor(), nextCustomColorIndex, new ShortColorSetter() {
                    public void set(short colorIndex) { fDstStyle2.setFillBackgroundColor(colorIndex); }
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
                if (srcFont instanceof XSSFFont) {
                    final Font fDstFont = dstFont;
                    setIfResolved(dstWb, ((XSSFFont) srcFont).getXSSFColor(), nextCustomColorIndex, new ShortColorSetter() {
                        public void set(short colorIndex) { fDstFont.setColor(colorIndex); }
                    });
                } else {
                    try {
                        dstFont.setColor(srcFont.getColor());
                    } catch (RuntimeException e) {
                        // theme/RGB font color has no legacy palette index - leave default
                    }
                }
                fontCache.put(fontKey, dstFont);
            }
            dstStyle.setFont(dstFont);

            styleCache.put(key, dstStyle);
        }
        dstCell.setCellStyle(dstStyle);
    }

    private interface ShortColorSetter {
        void set(short colorIndex);
    }

    private static void setIfResolved(HSSFWorkbook dstWb, XSSFColor color, short[] nextCustomColorIndex, ShortColorSetter setter) {
        short idx = toHssfColorIndex(dstWb, color, nextCustomColorIndex);
        if (idx >= 0) {
            setter.set(idx);
        }
    }

    /**
     * Resolves an XSSFColor (literal RGB, indexed, or theme+tint) to a color
     * index in the destination HSSFWorkbook's fixed-size custom palette.
     * A freshly created HSSFWorkbook's 56 custom slots are all pre-filled
     * with the standard colors already (nothing is "free"), so an exact new
     * RGB is claimed by directly overwriting the next unclaimed slot
     * (tracked via nextCustomColorIndex, walking from the high end of the
     * range down) rather than via HSSFPalette.addColor(), which throws on
     * an untouched palette. Returns -1 if the color is absent/automatic or
     * its RGB can't be determined (caller should leave the destination
     * style's default).
     */
    private static short toHssfColorIndex(HSSFWorkbook dstWb, XSSFColor color, short[] nextCustomColorIndex) {
        if (color == null || color.isAuto()) {
            return -1;
        }
        if (color.isIndexed() && !color.isThemed()) {
            // legacy fixed-palette index (e.g. indexed="64" = automatic/black) - reuse directly
            return (short) color.getIndexed();
        }
        byte[] rgb;
        try {
            // not getRGB(): for a theme color that returns the theme's raw base
            // RGB and ignores the tint (e.g. "Accent 6, Lighter 80%" would come
            // back as plain, unlightened Accent 6) - getRGBWithTint() applies it
            rgb = color.getRGBWithTint();
        } catch (RuntimeException e) {
            rgb = null;
        }
        if (rgb == null || rgb.length < 3) {
            return -1;
        }
        byte r = rgb[0], g = rgb[1], b = rgb[2];
        HSSFPalette palette = dstWb.getCustomPalette();
        org.apache.poi.hssf.util.HSSFColor match = palette.findColor(r, g, b);
        if (match == null) {
            if (nextCustomColorIndex[0] >= PALETTE_FIRST_COLOR_INDEX) {
                short claimIndex = nextCustomColorIndex[0]--;
                palette.setColorAtIndex(claimIndex, r, g, b);
                match = palette.getColor(claimIndex);
            } else {
                // all 56 custom slots already claimed by other distinct colors - use the nearest one
                match = palette.findSimilarColor(r & 0xFF, g & 0xFF, b & 0xFF);
            }
        }
        return match == null ? -1 : match.getIndex();
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
