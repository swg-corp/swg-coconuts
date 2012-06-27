package com.swg.coconuts.initiator.util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.ss.usermodel.Workbook;

/**
 * Interface untuk me-load sebuah file xls
 * @author satriaprayoga
 *
 */
public interface XlsLoader {

	/**
	 * load menggunakan nama file
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	Workbook loadWorkbook(String fileName) throws IOException;
	
	/**
	 * load menggunakan {@link InputStream}
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
	Workbook loadWorkbook(InputStream inputStream) throws IOException;
}
