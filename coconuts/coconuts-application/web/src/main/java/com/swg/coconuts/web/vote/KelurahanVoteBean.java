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

import com.swg.coconuts.backend.domain.Kelurahan;
import com.swg.coconuts.backend.domain.Tps;
import com.swg.coconuts.backend.repo.KelurahanRepository;
import com.swg.coconuts.backend.repo.TpsRepository;
import com.swg.coconuts.initiator.report.ColumnConfigurer;
import com.swg.coconuts.initiator.report.VoteMap;
import com.swg.coconuts.report.service.KelurahanReportService;
import com.swg.coconuts.web.VisibilityAware;
import com.swg.coconuts.web.converter.KelurahanConverter;
import com.swg.coconuts.web.vote.chart.ChartGenerator.KelurahanChartGenerator;
import com.swg.coconuts.web.vote.chart.ChartTrigger.KelurahanChartTrigger;

@ManagedBean
@RequestScoped
public class KelurahanVoteBean implements Serializable,VisibilityAware,KelurahanChartTrigger{
	private static final long serialVersionUID = -2708353498669411572L;
	
	@ManagedProperty("#{kelurahanChartGeneratorImpl}")
	private KelurahanChartGenerator chartGenerator;
	@ManagedProperty("#{kelurahanRepository}")
	private KelurahanRepository repository;
	@ManagedProperty("#{voteMapColumnsConfigurer}")
	private ColumnConfigurer configurer;
	@ManagedProperty("#{kelurahanReportServiceImpl}")
	private KelurahanReportService reportService;
	@ManagedProperty("#{tpsRepository}")
	private TpsRepository tpsRepository;

	private List<String> columns;
	private Collection<VoteMap> voteMaps;
	private AutoComplete autoComplete;
	private CartesianChartModel model;
	private Kelurahan kelurahan;
	private VoteMap map;
	private List<String> voteDescription;

	private boolean dataVisible;
	private boolean downloadEnable;
	
	public AutoComplete populate(){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		Application application = facesContext.getApplication();
		ExpressionFactory expressionFactory = application.getExpressionFactory();
		ELContext elContext = facesContext.getELContext();
		
		KelurahanConverter converter=new KelurahanConverter();
		converter.setRepository(repository);
		
		AutoComplete complete=(AutoComplete) application.createComponent(AutoComplete.COMPONENT_TYPE);
		complete.setId("kelurahanInput");
		complete.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{kelurahanVoteBean.kelurahan}", Kelurahan.class));
		complete.setCompleteMethod(expressionFactory.createMethodExpression(elContext, "#{kelurahanVoteBean.completeList}", List.class, new Class[] { String.class }));
		complete.setValueExpression("var", expressionFactory.createValueExpression(elContext, "kelurahan", String.class));
		complete.setValueExpression("itemLabel", expressionFactory.createValueExpression(elContext, "#{kelurahan.name}", String.class));
	    complete.setValueExpression("itemValue", expressionFactory.createValueExpression(elContext, "#{kelurahan}", Kelurahan.class));
	    complete.setDropdown(true);
	    complete.setConverter(converter);
		return complete;
	}
	
	public String displayForm(){
		downloadEnable=false;
		return "kelurahanChart?faces-redirect=true";
	}
	
	@Override
	public List<Kelurahan> completeList(String query) {
		List<Kelurahan> kelurahans=new ArrayList<Kelurahan>();
		for(Kelurahan kelurahan:repository.findAll()){
			String kelStr=String.valueOf(kelurahan.getName());
			if(kelStr.toLowerCase().startsWith(query)){
				kelurahans.add(kelurahan);
			}
		}
		return kelurahans;
	}
	
	
	public void setMap(VoteMap map) {
		this.map = map;
	}
	
	public VoteMap getMap() {
		return map;
	}
	
	public ColumnConfigurer getConfigurer() {
		return configurer;
	}

	public void setConfigurer(ColumnConfigurer configurer) {
		this.configurer = configurer;
	}

	public KelurahanReportService getReportService() {
		return reportService;
	}

	public void setReportService(KelurahanReportService reportService) {
		this.reportService = reportService;
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

	public boolean isDataVisible() {
		return dataVisible;
	}
	
	public void setChartGenerator(KelurahanChartGenerator chartGenerator) {
		this.chartGenerator = chartGenerator;
	}
	
	public KelurahanChartGenerator getChartGenerator() {
		return chartGenerator;
	}
	
	public void setKelurahan(Kelurahan kelurahan) {
		this.kelurahan = kelurahan;
	}
	
	public Kelurahan getKelurahan() {
		return kelurahan;
	}
	public void setAutoComplete(AutoComplete autoComplete) {
		this.autoComplete = autoComplete;
	}
	public void setVoteDescription(List<String> voteDescription) {
		this.voteDescription = voteDescription;
	}
	public List<String> getVoteDescription() {
		return voteDescription;
	}
	public AutoComplete getAutoComplete() {
		if(autoComplete==null)
			autoComplete=populate();
		return autoComplete;
	}
	public void setModel(CartesianChartModel model) {
		this.model = model;
	}
	
	public CartesianChartModel getModel() {
		return model;
	}
	public void setRepository(KelurahanRepository repository) {
		this.repository = repository;
	}
	
	public KelurahanRepository getRepository() {
		return repository;
	}
	
	public void setTpsRepository(TpsRepository tpsRepository) {
		this.tpsRepository = tpsRepository;
	}
	public TpsRepository getTpsRepository() {
		return tpsRepository;
	}
	
	public boolean isDownloadEnable() {
		return downloadEnable;
	}
	
	public void handle(SelectEvent event){
		Kelurahan selected=(Kelurahan) event.getObject();
		FacesMessage facesMessage=new FacesMessage("kelurahan: "+selected.getName());
		FacesContext.getCurrentInstance().addMessage(null, facesMessage);
		kelurahan=selected;
		model=chartGenerator.createChartModel(ensure(kelurahan));
		List<Tps> tps=tpsRepository.findByKelurahan(kelurahan);
		map=reportService.getVoteFor(kelurahan);
		dataVisible=!(map.getVoteResult().isEmpty());
		voteDescription=new ArrayList<String>();
		voteDescription.add("Nama Kelurahan: "+map.getAreaId());
		Map<String, Integer> result=map.getVoteResult();
		for(String s:result.keySet()){
			voteDescription.add(s+": "+result.get(s)+" suara");
		}
		voteDescription.add("total: "+map.getTotal());
		voteMaps=reportService.getChildVoteMap(tps);
		columns=configurer.getColumnsTitle();
	}
	
	protected Kelurahan ensure(Kelurahan kelurahan){
		return repository.findOne(kelurahan.getId());
	}

}
