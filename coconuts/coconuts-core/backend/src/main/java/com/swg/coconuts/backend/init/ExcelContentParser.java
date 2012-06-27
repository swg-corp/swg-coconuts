package com.swg.coconuts.backend.init;

import java.util.Iterator;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.swg.coconuts.initiator.ContentParser;
import com.swg.coconuts.initiator.param.CellParam;
import com.swg.coconuts.initiator.util.XlsContext;
import com.swg.coconuts.initiator.xls.Column;
import com.swg.coconuts.initiator.xls.Content;
import com.swg.coconuts.initiator.xls.Header;

@Component
public class ExcelContentParser implements ContentParser {
	
	private final Logger logger=Logger.getLogger(getClass());

	@Autowired
	private AreaTemplate template;
	
	@Autowired
	private VoteSenderTemplate voteSenderTemplate;
	
	@SuppressWarnings("unchecked")
	@Override
	public Content parseContent(Row row, Header header) {
		Content content=new Content();
		content.setHeader(header);
		Iterator<Cell> iterator=row.iterator();
		while(iterator.hasNext()){
			HSSFCell cell=(HSSFCell)iterator.next();
			CellParam<String> cellParam=null;
			if(cell.getCellType()==HSSFCell.CELL_TYPE_STRING){
				String value=cell.getStringCellValue();
				cellParam=(CellParam<String>) XlsContext.getParameterFactory().getCellParam(String.class, value);
			}else if(cell.getCellType()==HSSFCell.CELL_TYPE_NUMERIC){
				String value=String.valueOf(cell.getNumericCellValue());
				String zeros=".0";
				if(value.endsWith(zeros)){
					value=value.replace(zeros, "");
				}
				cellParam=(CellParam<String>)XlsContext.getParameterFactory().getCellParam(String.class, value);
			}else{
				break;
			}
			Column column=content.getHeader().getColumns().get(cell.getColumnIndex());
			logger.info("###### put key : "+column.getName()+" with params "+cellParam.getValue()+" #######");
			content.putData(column, cellParam);
			
		}
		voteSenderTemplate.doMap(content);
		template.doMap(content);
		return content;
	}

}
