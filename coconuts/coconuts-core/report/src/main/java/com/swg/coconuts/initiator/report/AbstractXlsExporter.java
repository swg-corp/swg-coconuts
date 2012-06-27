/**
 * 
 */
package com.swg.coconuts.initiator.report;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;


/**
 * @author satriaprayoga
 *
 */
public abstract class AbstractXlsExporter<T> implements XlsDataExporter<T>{
	
	private final Map<Integer,String> columnsString;
	private final Workbook workbook;
	
	private Sheet sheet;
	
	public AbstractXlsExporter() {
		this.columnsString=new HashMap<Integer, String>();
		workbook=new HSSFWorkbook();
	}
	
	public void initColumns(){
		configureHeader(this.columnsString);
	
		sheet=workbook.createSheet();
		Row row=sheet.createRow(0);
		
		Font font = sheet.getWorkbook().createFont();
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);         
        font.setFontHeightInPoints((short)11);
        font.setColor(IndexedColors.WHITE.getIndex());
        
		HSSFCellStyle headerCellStyle = (HSSFCellStyle) sheet.getWorkbook().createCellStyle();       
		headerCellStyle.setFillBackgroundColor(HSSFColor.GREY_25_PERCENT.index);
		headerCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		headerCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		headerCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);        
		headerCellStyle.setFont(font);
		headerCellStyle.setBorderBottom(CellStyle.BORDER_THIN);  
		headerCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		headerCellStyle.setBorderRight(CellStyle.BORDER_THIN);
		headerCellStyle.setBorderTop(CellStyle.BORDER_THIN); 
		for(int col=0;col<columnsString.keySet().size();col++){
		
			sheet.setColumnWidth(col, 8000);
			Cell cell=row.createCell(col);
			cell.setCellStyle(headerCellStyle);
			cell.setCellValue(this.columnsString.get(col));
		
		}
		
	}

	@Override
	public Workbook export(Collection<T> data) throws IOException {
		initColumns();
		int count=1;
		for(T d:data){
			if(d!=null){
				configureRow(d, sheet, count);
				count++;
			}
		
		}
		return workbook;

	}
	
	protected HSSFCellStyle getBodyStyle(){
		HSSFCellStyle bodyCS = (HSSFCellStyle) sheet.getWorkbook().createCellStyle();
		bodyCS.setAlignment(CellStyle.ALIGN_CENTER);
		bodyCS.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		bodyCS.setBorderBottom(CellStyle.BORDER_THIN);  
        bodyCS.setBorderLeft(CellStyle.BORDER_THIN);
        bodyCS.setBorderRight(CellStyle.BORDER_THIN);
        bodyCS.setBorderTop(CellStyle.BORDER_THIN);
        return bodyCS;
	}

	protected abstract void configureHeader(Map<Integer,String> columnTitle);
	protected abstract void configureRow(Object data,Sheet sheet2,int rowNumber);
}
