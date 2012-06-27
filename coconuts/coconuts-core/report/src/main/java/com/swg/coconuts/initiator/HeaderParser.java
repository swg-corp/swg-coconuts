package com.swg.coconuts.initiator;

import org.apache.poi.ss.usermodel.Sheet;

import com.swg.coconuts.initiator.xls.Header;

public interface HeaderParser {

	public Header parseHeader(Sheet sheet);
}
