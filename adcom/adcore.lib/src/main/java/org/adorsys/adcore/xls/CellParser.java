package org.adorsys.adcore.xls;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.ss.usermodel.Cell;

public class CellParser {

	public static String parseString(Cell cell) {
		if(cell ==null) return null;
		int cellType = -1;
		if(cell.getCellType()==Cell.CELL_TYPE_FORMULA){
			cellType = cell.getCachedFormulaResultType();
			return cell.getStringCellValue().trim();
		} else {
			cellType = cell.getCellType();
		}
		String val = null;
		if(cellType==Cell.CELL_TYPE_NUMERIC){
			val = new BigDecimal(cell.getNumericCellValue()).toString();
		} else if (cellType==Cell.CELL_TYPE_STRING){
			val = cell.getStringCellValue().trim();
		} else if (cellType==Cell.CELL_TYPE_BOOLEAN){
			val = new Boolean(cell.getBooleanCellValue()).toString();
		}
		return val;
	}

	public static BigDecimal parseNumber(Cell cell) {
		if(cell ==null) return null;
		int cellType = -1;
		if(cell.getCellType()==Cell.CELL_TYPE_FORMULA){
			cellType = cell.getCachedFormulaResultType();
		} else {
			cellType = cell.getCellType();
		}
		BigDecimal val = null;
		if(cellType==Cell.CELL_TYPE_NUMERIC){
			val = new BigDecimal(cell.getNumericCellValue());
		} else if (cellType==Cell.CELL_TYPE_STRING){
			val = new BigDecimal(cell.getStringCellValue().trim());
		}
		return val;
	}

	public static Boolean parseBoolean(Cell cell) {
		if(cell ==null) return null;
		int cellType = -1;
		if(cell.getCellType()==Cell.CELL_TYPE_FORMULA){
			cellType = cell.getCachedFormulaResultType();
		} else {
			cellType = cell.getCellType();
		}
		Boolean val = null;
		if(cellType==Cell.CELL_TYPE_BOOLEAN){
			val = cell.getBooleanCellValue();
		} else if (cellType==Cell.CELL_TYPE_STRING){
			val = new Boolean(cell.getStringCellValue().trim());
		} 
		return val;
	}

	public static Date parseDate(Cell cell, String... patterns) {
		if(cell ==null) return null;
		int cellType = -1;
		if(cell.getCellType()==Cell.CELL_TYPE_FORMULA){
			cellType = cell.getCachedFormulaResultType();
		} else {
			cellType = cell.getCellType();
		}
		Date val = null;
		if(cellType==Cell.CELL_TYPE_NUMERIC){
			val = new Date(new BigDecimal(cell.getNumericCellValue()).longValue());
		} else if (cellType==Cell.CELL_TYPE_STRING){
			try {
				val = DateUtils.parseDate(cell.getStringCellValue().trim(), patterns);
			} catch (ParseException e) {
				throw new IllegalStateException(e);
			}
		}  else {
			try {
				val = cell.getDateCellValue();
			} catch(RuntimeException ex){
				// noop
			}
		}
		return val;
	}
}
