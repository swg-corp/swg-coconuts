package com.swg.coconuts.web.vote.export;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.primefaces.model.StreamedContent;

import com.swg.coconuts.backend.domain.Kecamatan;
import com.swg.coconuts.initiator.report.VoteMap;
import com.swg.coconuts.report.service.KecamatanReportService;
import com.swg.coconuts.report.xls.VoteMapExporter.KelurahanVoteMapExporter;
import com.swg.coconuts.web.util.KelurahanXlsDownloader;
import com.swg.coconuts.web.util.XlsDownloader;

@ManagedBean
@SessionScoped
public class ExportKelurahanBean implements Serializable{
	private static final long serialVersionUID = -6059837210705044654L;
	
	@ManagedProperty("#{kelurahanVoteMapExporterImpl}")
	private KelurahanVoteMapExporter exporter;
	@ManagedProperty("#{kecamatanReportServiceImpl}")
	private KecamatanReportService reportService;
	private StreamedContent content;
	private Kecamatan kecamatan;
	private boolean dialogVisible;
	
	public String display(){
		VoteMap map=reportService.getVoteFor(kecamatan);
		dialogVisible=((kecamatan!=null) && (!map.getVoteResult().isEmpty()));
		return "downloadKelurahan";
	}


	public StreamedContent getContent() {
		XlsDownloader downloader=new KelurahanXlsDownloader(exporter, kecamatan);
		content=downloader.getFile();
		return content;
	}

	public void setContent(StreamedContent content) {
		this.content = content;
	}

	
	public void setDialogVisible(boolean dialogVisible) {
		this.dialogVisible = dialogVisible;
	}
	
	public boolean isDialogVisible() {
		return dialogVisible;
	}
	
	public void clear(){
		content=null;
		dialogVisible=false;
	}


	public KelurahanVoteMapExporter getExporter() {
		return exporter;
	}


	public void setExporter(KelurahanVoteMapExporter exporter) {
		this.exporter = exporter;
	}


	public Kecamatan getKecamatan() {
		return kecamatan;
	}


	public void setKecamatan(Kecamatan kecamatan) {
		this.kecamatan = kecamatan;
	}
	
	public void setReportService(KecamatanReportService reportService) {
		this.reportService = reportService;
	}
	
	public KecamatanReportService getReportService() {
		return reportService;
	}
	

}
