package com.swg.coconuts.web.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Workbook;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.swg.coconuts.backend.domain.Provinsi;
import com.swg.coconuts.initiator.report.VoteMap;
import com.swg.coconuts.report.xls.VoteMapExporter;

public class KabupatenXlsDownloader implements XlsDownloader{
	
	private final VoteMapExporter<Provinsi> exporter;
	private final Provinsi provinsi;

	public KabupatenXlsDownloader(VoteMapExporter<Provinsi> exporter,
			Provinsi provinsi) {
		super();
		this.exporter = exporter;
		this.provinsi = provinsi;
	}

	@Override
	public StreamedContent getFile() {
		StreamedContent content = null;
		//ExternalContext context=FacesContext.getCurrentInstance().getExternalContext();
		Workbook workbook=null;
		try{
			//String downloadFile=context.getRealPath("test.xls");
			workbook=exporter.export(new ArrayList<VoteMap>(exporter.getData(provinsi)));
			String filePath=ServletUtil.getDownloadPath(provinsi.getName()+".xls");
			workbook.write(new FileOutputStream(filePath));
			content=new DefaultStreamedContent(new FileInputStream(filePath), 
					"application/vnd.ms-excel", provinsi.getName()+".xls");
		}catch(IOException e){
			e.printStackTrace();
		}
		return content;
	}

}
