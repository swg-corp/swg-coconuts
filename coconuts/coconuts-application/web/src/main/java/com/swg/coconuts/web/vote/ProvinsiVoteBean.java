package com.swg.coconuts.web.vote;

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

import com.swg.coconuts.backend.domain.Kabupaten;
import com.swg.coconuts.backend.domain.Provinsi;
import com.swg.coconuts.backend.repo.KabupatenRepository;
import com.swg.coconuts.backend.repo.ProvinsiRepository;
import com.swg.coconuts.initiator.report.ColumnConfigurer;
import com.swg.coconuts.initiator.report.VoteMap;
import com.swg.coconuts.report.service.ProvinsiReportService;
import com.swg.coconuts.web.converter.ProvinsiConverter;
import com.swg.coconuts.web.vote.chart.ChartGenerator.ProvinsiChartGenerator;

@ManagedBean
@RequestScoped
public class ProvinsiVoteBean {
	
	@ManagedProperty("#{provinsiChartGeneratorImpl}")
	private ProvinsiChartGenerator chartGenerator;
	@ManagedProperty("#{provinsiRepository}")
	private ProvinsiRepository repository;
	@ManagedProperty("#{provinsiReportServiceImpl}")
	private ProvinsiReportService reportService;
	@ManagedProperty("#{kabupatenRepository}")
	private KabupatenRepository kabupatenRepository;
	@ManagedProperty("#{voteMapColumnsConfigurer}")
	private ColumnConfigurer configurer;
	
	private List<String> columns;
	private Collection<VoteMap> voteMaps;
	private AutoComplete autoComplete;
	private CartesianChartModel model;
	private Provinsi provinsi;
	private VoteMap map;
	private List<String> voteDescription;

	private boolean dataVisible;
	private boolean downloadEnable;
	
	public AutoComplete populate(){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		Application application = facesContext.getApplication();
		ExpressionFactory expressionFactory = application.getExpressionFactory();
		ELContext elContext = facesContext.getELContext();
		
		ProvinsiConverter converter=new ProvinsiConverter();
		converter.setRepository(repository);
		
		AutoComplete complete=(AutoComplete) application.createComponent(AutoComplete.COMPONENT_TYPE);
		complete.setId("provinsiInput");
		complete.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{provinsiVoteBean.provinsi}", Provinsi.class));
		complete.setCompleteMethod(expressionFactory.createMethodExpression(elContext, "#{provinsiVoteBean.completeList}", List.class, new Class[] { String.class }));
		complete.setValueExpression("var", expressionFactory.createValueExpression(elContext, "provinsi", String.class));
		complete.setValueExpression("itemLabel", expressionFactory.createValueExpression(elContext, "#{provinsi.name}", String.class));
	    complete.setValueExpression("itemValue", expressionFactory.createValueExpression(elContext, "#{provinsi}", Provinsi.class));
	    complete.setDropdown(true);
	    complete.setConverter(converter);
		return complete;
	}
	
	public String displayForm(){
		downloadEnable=false;
		return "provinsiChart?faces-redirect=true";
	}
	
	public List<Provinsi> completeList(String query) {
		List<Provinsi>provinsis=new ArrayList<Provinsi>();
		for(Provinsi provinsi:repository.findAll()){
			String kelStr=String.valueOf(provinsi.getName());
			if(kelStr.toLowerCase().startsWith(query)){
				provinsis.add(provinsi);
			}
		}
		return provinsis;
	}
	
	public ProvinsiChartGenerator getChartGenerator() {
		return chartGenerator;
	}
	
	public void setChartGenerator(ProvinsiChartGenerator chartGenerator) {
		this.chartGenerator = chartGenerator;
	}
	
	public ProvinsiRepository getRepository() {
		return repository;
	}
	
	public void setRepository(ProvinsiRepository repository) {
		this.repository = repository;
	}
	
	public AutoComplete getAutoComplete() {
		if(autoComplete==null)
			autoComplete=populate();
		return autoComplete;
	}
	
	public ProvinsiReportService getReportService() {
		return reportService;
	}

	public void setReportService(ProvinsiReportService reportService) {
		this.reportService = reportService;
	}

	public KabupatenRepository getKabupatenRepository() {
		return kabupatenRepository;
	}

	public void setKabupatenRepository(KabupatenRepository kabupatenRepository) {
		this.kabupatenRepository = kabupatenRepository;
	}

	public ColumnConfigurer getConfigurer() {
		return configurer;
	}

	public void setConfigurer(ColumnConfigurer configurer) {
		this.configurer = configurer;
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

	public Provinsi getProvinsi() {
		return provinsi;
	}

	public void setProvinsi(Provinsi provinsi) {
		this.provinsi = provinsi;
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

	public void handle(SelectEvent event){
		Provinsi selected=(Provinsi) event.getObject();
		FacesMessage facesMessage=new FacesMessage("kelurahan: "+selected.getName());
		FacesContext.getCurrentInstance().addMessage(null, facesMessage);
		provinsi=selected;
		model=chartGenerator.createChartModel(ensure(provinsi));
		List<Kabupaten> kabupatens=kabupatenRepository.findByProvinsi(ensure(provinsi));
		map=reportService.getVoteFor(provinsi);
		dataVisible=!(map.getVoteResult().isEmpty());
		voteDescription=new ArrayList<String>();
		voteDescription.add("Nama Provinsi: "+map.getAreaId());
		Map<String, Integer> result=map.getVoteResult();
		for(String s:result.keySet()){
			voteDescription.add(s+": "+result.get(s)+" suara");
		}
		voteDescription.add("total: "+map.getTotal());
		voteMaps=reportService.getChildVoteMap(kabupatens);
		columns=configurer.getColumnsTitle();
		
	}
	
	protected Provinsi ensure(Provinsi provinsi){
		return repository.findOne(provinsi.getId());
	}

}
