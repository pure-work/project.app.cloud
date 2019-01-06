package com.gozi.core.base.util;

import org.apache.poi.hssf.usermodel.HSSFCell;

import java.text.DecimalFormat;

public class PoiUtil {
	/**
     * 获得EXCEL中CELL的字符串值
     * @param cell
     * @return
     */
    public static String getExcelStringValue(HSSFCell cell) {
		String result = "";
		DecimalFormat df = new DecimalFormat("#");
		switch (cell.getCellType()) {
			case HSSFCell.CELL_TYPE_STRING:
				result = cell.getRichStringCellValue().getString().trim();
				break;
			case HSSFCell.CELL_TYPE_NUMERIC:
				result = df.format(cell.getNumericCellValue()).toString();
				break;
			default:
				result = "";
		}
		return result;
	}
}
