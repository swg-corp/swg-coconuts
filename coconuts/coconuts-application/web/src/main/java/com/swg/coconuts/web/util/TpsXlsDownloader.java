package com.swg.coconuts.web.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Workbook;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.swg.coconuts.backend.domain.Kelurahan;
import com.swg.coconuts.initiator.report.VoteMap;
import com.swg.coconuts.report.xls.VoteMapExporter;

public class TpsXlsDownloader implements XlsDownloader{

	private final VoteMapExporter<Kelurahan> exporter;
	private final Kelurahan kelurahan;
	
	public TpsXlsDownloader(VoteMapExporter<Kelurahan> exporter,Kelurahan kelurahan) {
		this.exporter = exporter;
		this.kelurahan = kelurahan;
	}

	@Override
	public StreamedContent getFile() {
		StreamedContent content = null;
		//ExternalContext context=FacesContext.getCurrentInstance().getExternalContext();
		Workbook workbook=null;
		try{
			//String downloadFile=context.getRealPath("test.xls");
			workbook=exporter.export(new ArrayList<VoteMap>(exporter.getData(kelurahan)));
			String filePath=ServletUtil.getDownloadPath(kelurahan.getName()+".xls");
			workbook.write(new FileOutputStream(filePath));
			content=new DefaultStreamedContent(new FileInputStream((filePath)), 
					"application/vnd.ms-excel", kelurahan.getName()+".xls");
		}catch(IOException e){
			e.printStackTrace();
		}
		return content;
	}
	
	
	

}
