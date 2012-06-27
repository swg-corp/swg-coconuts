package com.swg.coconuts.backend.init;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.swg.coconuts.initiator.ContentParser;
import com.swg.coconuts.initiator.HeaderParser;
import com.swg.coconuts.initiator.util.XlsContext;
import com.swg.coconuts.initiator.xls.Header;

@Component
public class SimpleUploadExcelService implements UploadExcelService {

	@Autowired
	private HeaderParser headerParser;
	
	@Autowired
	private ContentParser contentParser;
	
	@Override
	public void processUpload(Workbook workbook) {
		HSSFSheet sheet=(HSSFSheet) workbook.getSheetAt(0);
		Header header=headerParser.parseHeader(sheet);
		int nrow=XlsContext.getCounter().countRow(sheet);
		for(int i=1;i<nrow;i++){
			HSSFRow row=(HSSFRow)sheet.getRow(i);
			if(XlsContext.getCounter().isEmptyRow(row))
				break;
			contentParser.parseContent(row, header);
			
		}
	}

}
