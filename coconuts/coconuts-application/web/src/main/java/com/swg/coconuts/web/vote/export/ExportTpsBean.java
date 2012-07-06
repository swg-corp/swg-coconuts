package com.swg.coconuts.web.vote.export;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.primefaces.model.StreamedContent;

import com.swg.coconuts.backend.domain.Kelurahan;
import com.swg.coconuts.initiator.report.VoteMap;
import com.swg.coconuts.report.service.KelurahanReportService;
import com.swg.coconuts.report.xls.VoteMapExporter.TpsVoteMapExporter;
import com.swg.coconuts.web.util.TpsXlsDownloader;
import com.swg.coconuts.web.util.XlsDownloader;

@ManagedBean
@SessionScoped
public class ExportTpsBean implements Serializable{

	private static final long serialVersionUID = 5956735570323099291L;
	
	@ManagedProperty("#{tpsVoteMapExporterImpl}")
	private TpsVoteMapExporter exporter;
	private StreamedContent content;
	private Kelurahan kelurahan;
	private boolean dialogVisible;
	@ManagedProperty("#{kelurahanReportServiceImpl}")
	private KelurahanReportService reportService;
	
	public String display(){
		VoteMap map=reportService.getVoteFor(kelurahan);
		dialogVisible=((kelurahan!=null) && (!map.getVoteResult().isEmpty()));
		return "downloadTps";
	}

	public TpsVoteMapExporter getExporter() {
		return exporter;
	}

	public void setExporter(TpsVoteMapExporter exporter) {
		this.exporter = exporter;
	}

	public StreamedContent getContent() {
		XlsDownloader downloader=new TpsXlsDownloader(exporter, kelurahan);
		content=downloader.getFile();
		return content;
	}

	public void setContent(StreamedContent content) {
		this.content = content;
	}

	public Kelurahan getKelurahan() {
		return kelurahan;
	}

	public void setKelurahan(Kelurahan kelurahan) {
		this.kelurahan = kelurahan;
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
	
	public void setReportService(KelurahanReportService reportService) {
		this.reportService = reportService;
	}
	
	public KelurahanReportService getReportService() {
		return reportService;
	}

}
