package com.swg.coconuts.web.vote;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.primefaces.component.autocomplete.AutoComplete;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.chart.CartesianChartModel;

import com.swg.coconuts.backend.domain.Kecamatan;
import com.swg.coconuts.backend.domain.Kelurahan;
import com.swg.coconuts.backend.repo.KecamatanRepository;
import com.swg.coconuts.backend.repo.KelurahanRepository;
import com.swg.coconuts.initiator.report.ColumnConfigurer;
import com.swg.coconuts.initiator.report.VoteMap;
import com.swg.coconuts.report.service.KecamatanReportService;
import com.swg.coconuts.web.VisibilityAware;
import com.swg.coconuts.web.converter.KecamatanConverter;
import com.swg.coconuts.web.vote.chart.ChartGenerator.KecamatanChartGenerator;
import com.swg.coconuts.web.vote.chart.ChartTrigger.KecamatanChartTrigger;

@ManagedBean
@RequestScoped
public class KecamatanVoteBean implements Serializable,VisibilityAware,KecamatanChartTrigger{
	private static final long serialVersionUID = 9953947205207322L;
	
	@ManagedProperty("#{kecamatanChartGeneratorImpl}")
	private KecamatanChartGenerator chartGenerator;
	@ManagedProperty("#{kecamatanRepository}")
	private KecamatanRepository repository;
	@ManagedProperty("#{voteMapColumnsConfigurer}")
	private ColumnConfigurer configurer;
	@ManagedProperty("#{kecamatanReportServiceImpl}")
	private KecamatanReportService reportService;
	@ManagedProperty("#{kelurahanRepository}")
	private KelurahanRepository kelurahanRepository;

	private List<String> columns;
	private Collection<VoteMap> voteMaps;
	private AutoComplete autoComplete;
	private CartesianChartModel model;
	private Kecamatan kecamatan;
	private VoteMap map;
	private List<String> voteDescription;

	private boolean dataVisible;
	private boolean downloadEnable;
	
	public AutoComplete populate(){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		Application application = facesContext.getApplication();
		ExpressionFactory expressionFactory = application.getExpressionFactory();
		ELContext elContext = facesContext.getELContext();
		
		KecamatanConverter converter=new KecamatanConverter();
		converter.setRepository(repository);
		
		AutoComplete complete=(AutoComplete) application.createComponent(AutoComplete.COMPONENT_TYPE);
		complete.setId("kecamatanInput");
		complete.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{kecamatanVoteBean.kecamatan}", Kecamatan.class));
		complete.setCompleteMethod(expressionFactory.createMethodExpression(elContext, "#{kecamatanVoteBean.completeList}", List.class, new Class[] { String.class }));
		complete.setValueExpression("var", expressionFactory.createValueExpression(elContext, "kecamatan", String.class));
		complete.setValueExpression("itemLabel", expressionFactory.createValueExpression(elContext, "#{kecamatan.name}", String.class));
	    complete.setValueExpression("itemValue", expressionFactory.createValueExpression(elContext, "#{kecamatan}", Kecamatan.class));
	    complete.setDropdown(true);
	    complete.setConverter(converter);
		return complete;
	}
	
	public String displayForm(){
		downloadEnable=false;
		return "kecamatanChart?faces-redirect=true";
	}
	
	public List<Kecamatan> completeList(String query) {
		List<Kecamatan> kecamatans=new ArrayList<Kecamatan>();
		for(Kecamatan kecamatan:repository.findAll()){
			String kelStr=String.valueOf(kecamatan.getName());
			if(kelStr.toLowerCase().startsWith(query)){
				kecamatans.add(kecamatan);
			}
		}
		return kecamatans;
	}
	
	public KecamatanChartGenerator getChartGenerator() {
		return chartGenerator;
	}

	public void setChartGenerator(KecamatanChartGenerator chartGenerator) {
		this.chartGenerator = chartGenerator;
	}

	public KecamatanRepository getRepository() {
		return repository;
	}

	public void setRepository(KecamatanRepository repository) {
		this.repository = repository;
	}

	public ColumnConfigurer getConfigurer() {
		return configurer;
	}

	public void setConfigurer(ColumnConfigurer configurer) {
		this.configurer = configurer;
	}

	public KecamatanReportService getReportService() {
		return reportService;
	}

	public void setReportService(KecamatanReportService reportService) {
		this.reportService = reportService;
	}

	public KelurahanRepository getKelurahanRepository() {
		return kelurahanRepository;
	}

	public void setKelurahanRepository(KelurahanRepository kelurahanRepository) {
		this.kelurahanRepository = kelurahanRepository;
	}

	public List<String> getColumns() {
		return columns;
	}

	public void setColumns(List<String> columns) {
		this.columns = columns;
	}

	public Collection<VoteMap> getVoteMaps() {
		return voteMaps;
	}

	public void setVoteMaps(Collection<VoteMap> voteMaps) {
		this.voteMaps = voteMaps;
	}

	public CartesianChartModel getModel() {
		return model;
	}

	public void setModel(CartesianChartModel model) {
		this.model = model;
	}

	public Kecamatan getKecamatan() {
		return kecamatan;
	}

	public void setKecamatan(Kecamatan kecamatan) {
		this.kecamatan = kecamatan;
	}

	public VoteMap getMap() {
		return map;
	}

	public void setMap(VoteMap map) {
		this.map = map;
	}

	public List<String> getVoteDescription() {
		return voteDescription;
	}

	public void setVoteDescription(List<String> voteDescription) {
		this.voteDescription = voteDescription;
	}

	public boolean isDataVisible() {
		return dataVisible;
	}

	public void setDataVisible(boolean dataVisible) {
		this.dataVisible = dataVisible;
	}

	public boolean isDownloadEnable() {
		return downloadEnable;
	}

	public void setDownloadEnable(boolean downloadEnable) {
		this.downloadEnable = downloadEnable;
	}

	public void setAutoComplete(AutoComplete autoComplete) {
		this.autoComplete = autoComplete;
	}

	public AutoComplete getAutoComplete() {
		if(autoComplete==null)
			autoComplete=populate();
		return autoComplete;
	}
	
	public void handle(SelectEvent event){
		Kecamatan selected=(Kecamatan) event.getObject();
		
		kecamatan=selected;
		FacesMessage facesMessage=new FacesMessage("kelurahan: "+selected.getName());
		FacesContext.getCurrentInstance().addMessage(null, facesMessage);
		model=chartGenerator.createChartModel(ensure(kecamatan));
		List<Kelurahan> kelurahans=kelurahanRepository.findByKecamatan(kecamatan);
		map=reportService.getVoteFor(kecamatan);
		dataVisible=!(map.getVoteResult().isEmpty());
		voteDescription=new ArrayList<String>();
		voteDescription.add("Nama Kecamatan: "+map.getAreaId());
		Map<String, Integer> result=map.getVoteResult();
		for(String s:result.keySet()){
			voteDescription.add(s+": "+result.get(s)+" suara");
		}
		voteDescription.add("total: "+map.getTotal());
		voteMaps=reportService.getChildVoteMap(kelurahans);
		columns=configurer.getColumnsTitle();
			
	}
	
	protected Kecamatan ensure(Kecamatan kecamatan){
		return repository.findOne(kecamatan.getId());
	}


}
