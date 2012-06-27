package com.swg.coconuts.backend.init;

import org.apache.poi.ss.usermodel.Workbook;

public interface UploadExcelService {

	public void processUpload(Workbook workbook);
}
