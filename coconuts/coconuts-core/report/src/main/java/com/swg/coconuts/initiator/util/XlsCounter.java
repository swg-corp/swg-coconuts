package com.swg.coconuts.initiator.util;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

/**
 * Utilitas sederhana untuk perhitungan jumlah kolom, baris, dan cell pada sebuah sheet
 * @author satriaprayoga
 *
 */
public interface XlsCounter {

	boolean isEmptyRow(Row row);
	
	Integer countRow(Sheet sheet);
	
	Integer countCell(Row row);
}
