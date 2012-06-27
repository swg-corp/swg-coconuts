package com.swg.coconuts.initiator.report;

import org.apache.poi.ss.usermodel.Workbook;

import com.swg.coconuts.initiator.report.DataExporter.WorkbookExporter;

/**
 * 
 * @author satriaprayoga
 *
 */
public interface XlsDataExporter<E> extends WorkbookExporter<Workbook, E> {

}
