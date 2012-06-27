package com.swg.coconuts.initiator;

import org.apache.poi.ss.usermodel.Row;

import com.swg.coconuts.initiator.xls.Content;
import com.swg.coconuts.initiator.xls.Header;

public interface ContentParser {

	Content parseContent(Row row,Header header);
}
