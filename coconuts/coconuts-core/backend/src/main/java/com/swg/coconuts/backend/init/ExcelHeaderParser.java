package com.swg.coconuts.backend.init;

import java.util.Iterator;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Component;

import com.swg.coconuts.initiator.HeaderParser;
import com.swg.coconuts.initiator.xls.Column;
import com.swg.coconuts.initiator.xls.Header;

@Component
public class ExcelHeaderParser implements HeaderParser {

	private final Logger logger=Logger.getLogger(getClass());
	
	@Override
	public Header parseHeader(Sheet sheet) {
		HSSFRow row=(HSSFRow)sheet.getRow(0);
		Iterator<Cell> iterator=row.cellIterator();
		Header header=new Header();
		while(iterator.hasNext()){
			HSSFCell cell=(HSSFCell)iterator.next();
			Integer cellNum=cell.getColumnIndex();
			String value=cell.getStringCellValue();
			Column column=new Column(value,cellNum);
			logger.info("############# parsing column: "+column.toString()+" #################");
			header.putColumn(column);
		}
		logger.info("################ column size: "+header.columnCount()+" ##################");
		return header;
	}

}
