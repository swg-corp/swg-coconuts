package com.swg.coconuts.web.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Workbook;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.swg.coconuts.backend.domain.Kabupaten;
import com.swg.coconuts.initiator.report.VoteMap;
import com.swg.coconuts.report.xls.VoteMapExporter;

public class KecamatanXlsDownloader implements XlsDownloader{

	private final VoteMapExporter<Kabupaten> exporter;
	private final Kabupaten kabupaten;
	
	public KecamatanXlsDownloader(VoteMapExporter<Kabupaten> exporter,
			Kabupaten kabupaten) {
		this.exporter = exporter;
		this.kabupaten = kabupaten;
	}

	@Override
	public StreamedContent getFile() {
		StreamedContent content = null;
		//ExternalContext context=FacesContext.getCurrentInstance().getExternalContext();
		Workbook workbook=null;
		try{
			//String downloadFile=context.getRealPath("test.xls");
			workbook=exporter.export(new ArrayList<VoteMap>(exporter.getData(kabupaten)));
			String filePath=ServletUtil.getDownloadPath(kabupaten.getName()+".xls");
			workbook.write(new FileOutputStream(filePath));
			content=new DefaultStreamedContent(new FileInputStream(filePath), 
					"application/vnd.ms-excel", kabupaten.getName()+".xls");
		}catch(IOException e){
			e.printStackTrace();
		}
		return content;
	}

}
