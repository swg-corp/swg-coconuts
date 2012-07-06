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
import com.swg.coconuts.backend.domain.Kecamatan;
import com.swg.coconuts.backend.repo.KabupatenRepository;
import com.swg.coconuts.backend.repo.KecamatanRepository;
import com.swg.coconuts.initiator.report.ColumnConfigurer;
import com.swg.coconuts.initiator.report.VoteMap;
import com.swg.coconuts.report.service.KabupatenReportService;
import com.swg.coconuts.web.converter.KabupatenConverter;
import com.swg.coconuts.web.vote.chart.ChartGenerator.KabupatenChartGenerator;

@ManagedBean
@RequestScoped
public class KabupatenVoteBean {

	@ManagedProperty("#{kabupatenChartGeneratorImpl}")
	private KabupatenChartGenerator chartGenerator;
	@ManagedProperty("#{kabupatenRepository}")
	private KabupatenRepository repository;
	@ManagedProperty("#{kabupatenReportServiceImpl}")
	private KabupatenReportService reportService;
	@ManagedProperty("#{kecamatanRepository}")
	private KecamatanRepository kecamatanRepository;
	@ManagedProperty("#{voteMapColumnsConfigurer}")
	private ColumnConfigurer configurer;
	
	private List<String> columns;
	private Collection<VoteMap> voteMaps;
	private AutoComplete autoComplete;
	private CartesianChartModel model;
	private Kabupaten kabupaten;
	private VoteMap map;
	private List<String> voteDescription;

	private boolean dataVisible;
	private boolean downloadEnable;
	
	public AutoComplete populate(){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		Application application = facesContext.getApplication();
		ExpressionFactory expressionFactory = application.getExpressionFactory();
		ELContext elContext = facesContext.getELContext();
		
		KabupatenConverter converter=new KabupatenConverter();
		converter.setRepository(repository);
		
		AutoComplete complete=(AutoComplete) application.createComponent(AutoComplete.COMPONENT_TYPE);
		complete.setId("kabupatenInput");
		complete.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{kabupatenVoteBean.kabupaten}", Kabupaten.class));
		complete.setCompleteMethod(expressionFactory.createMethodExpression(elContext, "#{kabupatenVoteBean.completeList}", List.class, new Class[] { String.class }));
		complete.setValueExpression("var", expressionFactory.createValueExpression(elContext, "kabupaten", String.class));
		complete.setValueExpression("itemLabel", expressionFactory.createValueExpression(elContext, "#{kabupaten.name}", String.class));
	    complete.setValueExpression("itemValue", expressionFactory.createValueExpression(elContext, "#{kabupaten}", Kabupaten.class));
	    complete.setDropdown(true);
	    complete.setConverter(converter);
		return complete;
	}
	
	public String displayForm(){
		downloadEnable=false;
		return "kabupatenChart?faces-redirect=true";
	}
	
	public List<Kabupaten> completeList(String query) {
		List<Kabupaten> kabupatens=new ArrayList<Kabupaten>();
		for(Kabupaten kabupaten:repository.findAll()){
			String kelStr=String.valueOf(kabupaten.getName());
			if(kelStr.toLowerCase().startsWith(query)){
				kabupatens.add(kabupaten);
			}
		}
		return kabupatens;
	}
	
	
	
	public KabupatenChartGenerator getChartGenerator() {
		return chartGenerator;
	}

	public void setChartGenerator(KabupatenChartGenerator chartGenerator) {
		this.chartGenerator = chartGenerator;
	}

	public KabupatenRepository getRepository() {
		return repository;
	}

	public void setRepository(KabupatenRepository repository) {
		this.repository = repository;
	}

	public KabupatenReportService getReportService() {
		return reportService;
	}

	public void setReportService(KabupatenReportService reportService) {
		this.reportService = reportService;
	}

	public KecamatanRepository getKecamatanRepository() {
		return kecamatanRepository;
	}

	public void setKecamatanRepository(KecamatanRepository kecamatanRepository) {
		this.kecamatanRepository = kecamatanRepository;
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

	public Kabupaten getKabupaten() {
		return kabupaten;
	}

	public void setKabupaten(Kabupaten kabupaten) {
		this.kabupaten = kabupaten;
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
		Kabupaten selected=(Kabupaten) event.getObject();
		FacesMessage facesMessage=new FacesMessage("kelurahan: "+selected.getName());
		FacesContext.getCurrentInstance().addMessage(null, facesMessage);
		kabupaten=selected;
		model=chartGenerator.createChartModel(ensure(kabupaten));
		List<Kecamatan> kecamatans=kecamatanRepository.findByKabupaten(ensure(kabupaten));
		map=reportService.getVoteFor(kabupaten);
		dataVisible=!(map.getVoteResult().isEmpty());
		voteDescription=new ArrayList<String>();
		voteDescription.add("Nama Kabupaten: "+map.getAreaId());
		Map<String, Integer> result=map.getVoteResult();
		for(String s:result.keySet()){
			voteDescription.add(s+": "+result.get(s)+" suara");
		}
		voteDescription.add("total: "+map.getTotal());
		voteMaps=reportService.getChildVoteMap(kecamatans);
		columns=configurer.getColumnsTitle();
	}
	
	protected Kabupaten ensure(Kabupaten kabupaten){
		return repository.findOne(kabupaten.getId());
	}
}
