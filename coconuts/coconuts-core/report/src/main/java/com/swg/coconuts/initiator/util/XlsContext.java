package com.swg.coconuts.initiator.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.swg.coconuts.initiator.param.CellParam;
import com.swg.coconuts.initiator.param.NumberParam;
import com.swg.coconuts.initiator.param.ParameterFactory;
import com.swg.coconuts.initiator.param.TextParam;

/**
 * Class contextual sederhana yang berhubungan dengan pengolahan file xls
 * @author satriaprayoga
 *
 */
public final class XlsContext {
	
	public static ParameterFactory getParameterFactory(){
		return new XlsContext.DefaultParameterFactory();
	}
	
	public static XlsCounter getCounter(){
		return new XlsContext.DefaultXlsCounter();
	}
	
	public static XlsLoader getLoader(){
		return new XlsContext.DefaultXlsLoader();
	}

	static class DefaultParameterFactory implements ParameterFactory{

		@Override
		public <T extends Serializable> CellParam<?> getCellParam(Class<?> clazz,Object value){
			if(clazz.getName()==String.class.getName() && value instanceof String){
				TextParam parameter=new TextParam();
				parameter.setValue((String) value);
				return parameter;
			}else if(clazz.getName()==Double.class.getName() && value instanceof Double){
				NumberParam parameter=new NumberParam();
				parameter.setValue((Double)value);
				return parameter;
			}else{
				throw new IllegalArgumentException("invalid type");
			}
		}
		
	}
	
	static class DefaultXlsCounter implements XlsCounter{

		public Integer countRow(Sheet sheet) {
			int first=sheet.getFirstRowNum();
			int last=sheet.getLastRowNum();
			return last-first;
		}

		public Integer countCell(Row row) {
			int first=row.getFirstCellNum();
			int last=row.getLastCellNum();
			return last-first;
		}

		public boolean isEmptyRow(Row row) {
			boolean empty=false;
			if(row.getCell(0)==null || row.getCell(0).getCellType()==Cell.CELL_TYPE_BLANK)
				empty=true;
			return empty;
		}
		
	}
	
	static class DefaultXlsLoader implements XlsLoader{
		public Workbook loadWorkbook(String file) throws IOException{
			InputStream inputStream=new FileInputStream(new File(file));
			HSSFWorkbook workbook=new HSSFWorkbook(inputStream);
			return workbook;
		}

		
		public Workbook loadWorkbook(InputStream inputStream) throws IOException {
			HSSFWorkbook workbook=new HSSFWorkbook(inputStream);
			return workbook;
		}
	}
}
