package com.swg.coconuts.web.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.poi.ss.usermodel.Workbook;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.swg.coconuts.backend.domain.Kecamatan;
import com.swg.coconuts.initiator.report.VoteMap;
import com.swg.coconuts.report.xls.VoteMapExporter;

public class KelurahanXlsDownloader implements XlsDownloader{

	private final VoteMapExporter<Kecamatan> exporter;
	private final Kecamatan kecamatan;
	
	public KelurahanXlsDownloader(VoteMapExporter<Kecamatan> exporter,Kecamatan kecamatan) {
		this.exporter = exporter;
		this.kecamatan = kecamatan;
	}



	@Override
	public StreamedContent getFile() {
		StreamedContent content = null;
		//ExternalContext context=FacesContext.getCurrentInstance().getExternalContext();
		Workbook workbook=null;
		try{
			//String downloadFile=context.getRealPath("test.xls");
			Collection<VoteMap> maps=exporter.getData(kecamatan);
			if(!maps.isEmpty() && maps!=null){
				workbook=exporter.export(new ArrayList<VoteMap>(maps));
				String filePath=ServletUtil.getDownloadPath(kecamatan.getName()+".xls");
				workbook.write(new FileOutputStream(filePath));
				content=new DefaultStreamedContent(new FileInputStream((filePath)), 
						"application/vnd.ms-excel", kecamatan.getName()+".xls");
			}
			
		}catch(IOException e){
			e.printStackTrace();
		}
		return content;
	}

}
