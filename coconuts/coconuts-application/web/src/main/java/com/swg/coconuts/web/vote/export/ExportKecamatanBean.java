package com.swg.coconuts.web.vote.export;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.primefaces.model.StreamedContent;

import com.swg.coconuts.backend.domain.Kabupaten;
import com.swg.coconuts.initiator.report.VoteMap;
import com.swg.coconuts.report.service.KabupatenReportService;
import com.swg.coconuts.report.xls.VoteMapExporter.KecamatanVoteMapExporter;
import com.swg.coconuts.web.util.KecamatanXlsDownloader;
import com.swg.coconuts.web.util.XlsDownloader;

@ManagedBean
@SessionScoped
public class ExportKecamatanBean implements Serializable{

	private static final long serialVersionUID = 5282160871161642853L;
	@ManagedProperty("#{kecamatanVoteMapExporterImpl}")
	private KecamatanVoteMapExporter exporter;
	@ManagedProperty("#{kabupatenReportServiceImpl}")
	private KabupatenReportService reportService;
	private StreamedContent content;
	private Kabupaten kabupaten;
	private boolean dialogVisible;
	
	public String display(){
		VoteMap map=reportService.getVoteFor(kabupaten);
		dialogVisible=((kabupaten!=null) && (!map.getVoteResult().isEmpty()));
		return "downloadKecamatan";
	}

	public StreamedContent getContent() {
		XlsDownloader downloader=new KecamatanXlsDownloader(exporter, kabupaten);
		content=downloader.getFile();
		return content;
	}

	public KecamatanVoteMapExporter getExporter() {
		return exporter;
	}

	public void setExporter(KecamatanVoteMapExporter exporter) {
		this.exporter = exporter;
	}

	public Kabupaten getKabupaten() {
		return kabupaten;
	}

	public void setKabupaten(Kabupaten kabupaten) {
		this.kabupaten = kabupaten;
	}

	public boolean isDialogVisible() {
		return dialogVisible;
	}

	public void setDialogVisible(boolean dialogVisible) {
		this.dialogVisible = dialogVisible;
	}

	public void setContent(StreamedContent content) {
		this.content = content;
	}
	
	public void clear(){
		content=null;
		dialogVisible=false;
	}
	
	public void setReportService(KabupatenReportService reportService) {
		this.reportService = reportService;
	}
	
	public KabupatenReportService getReportService() {
		return reportService;
	}


}
