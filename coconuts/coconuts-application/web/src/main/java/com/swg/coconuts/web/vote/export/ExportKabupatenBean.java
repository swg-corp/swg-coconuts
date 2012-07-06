package com.swg.coconuts.web.vote.export;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.primefaces.model.StreamedContent;

import com.swg.coconuts.backend.domain.Provinsi;
import com.swg.coconuts.initiator.report.VoteMap;
import com.swg.coconuts.report.service.ProvinsiReportService;
import com.swg.coconuts.report.xls.VoteMapExporter.KabupatenVoteMapExporter;
import com.swg.coconuts.web.util.KabupatenXlsDownloader;
import com.swg.coconuts.web.util.XlsDownloader;

@ManagedBean
@SessionScoped
public class ExportKabupatenBean implements Serializable{

	private static final long serialVersionUID = -4531842121342287685L;
	@ManagedProperty("#{kabupatenVoteMapExporterImpl}")
	private KabupatenVoteMapExporter exporter;
	@ManagedProperty("#{provinsiReportServiceImpl}")
	private ProvinsiReportService reportService;
	private StreamedContent content;
	private Provinsi provinsi;
	private boolean dialogVisible;
	
	public String display(){
		VoteMap map=reportService.getVoteFor(provinsi);
		dialogVisible=((provinsi!=null) && (!map.getVoteResult().isEmpty()));
		return "downloadKabupaten";
	}

	public StreamedContent getContent() {
		XlsDownloader downloader=new KabupatenXlsDownloader(exporter, provinsi);
		content=downloader.getFile();
		return content;
	}

	public KabupatenVoteMapExporter getExporter() {
		return exporter;
	}

	public void setExporter(KabupatenVoteMapExporter exporter) {
		this.exporter = exporter;
	}

	public Provinsi getProvinsi() {
		return provinsi;
	}

	public void setProvinsi(Provinsi provinsi) {
		this.provinsi = provinsi;
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

	public void setReportService(ProvinsiReportService reportService) {
		this.reportService = reportService;
	}
	
	public ProvinsiReportService getReportService() {
		return reportService;
	}

	

}
