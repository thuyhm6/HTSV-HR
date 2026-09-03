package com.ait.pa.service.imp.excelUtil.jxlcompat;

/**
 * Minimal stand-in for jxl.CellType, covering only the values this codebase
 * actually compares against ({@link #EMPTY}, {@link #DATE}).
 */
public final class CellType {
    public static final CellType EMPTY = new CellType();
    public static final CellType DATE = new CellType();
    public static final CellType OTHER = new CellType();

    private CellType() {
    }
}
