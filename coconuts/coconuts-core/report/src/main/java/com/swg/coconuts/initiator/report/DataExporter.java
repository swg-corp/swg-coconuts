/**
 * 
 */
package com.swg.coconuts.initiator.report;

import java.io.IOException;
import java.util.Collection;

import org.apache.poi.ss.usermodel.Workbook;

/**
 * @author satriaprayoga
 *
 */
public interface DataExporter<T,E> {

	T export(Collection<E> source) throws IOException;
	
	public interface WorkbookExporter<T extends Workbook,E> extends DataExporter<T, E>{
		
	}
}
