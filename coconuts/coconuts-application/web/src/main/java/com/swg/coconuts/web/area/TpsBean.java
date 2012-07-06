package com.swg.coconuts.web.area;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.context.FacesContext;
import javax.faces.convert.IntegerConverter;

import org.primefaces.component.autocomplete.AutoComplete;
import org.primefaces.component.message.Message;
import org.primefaces.component.spinner.Spinner;

import com.swg.coconuts.backend.domain.Kelurahan;
import com.swg.coconuts.backend.domain.Tps;
import com.swg.coconuts.backend.repo.KelurahanRepository;
import com.swg.coconuts.backend.repo.TpsRepository;
import com.swg.coconuts.web.Manager;
import com.swg.coconuts.web.converter.KelurahanConverter;
import com.swg.coconuts.web.validator.TpsNumberValidator;

@ManagedBean
@SessionScoped
public class TpsBean implements Manager,Serializable{
	private static final long serialVersionUID = -2207316271000702406L;
	
	//private Kelurahan kelurahan;
	
	private List<Tps> tps;
	
	private boolean dataVisible;
	
	private boolean dialogVisible;
	
	private Tps tpsBean;
	
	@ManagedProperty("#{tpsRepository}")
	private TpsRepository repository;
	@ManagedProperty("#{kelurahanRepository}")
	private KelurahanRepository kelurahanRepository;
	
	private TpsNumberValidator numberValidator;
	private HtmlPanelGrid createPanelGrid;
	
	
	public String findAll(){
		tps=repository.findAll();
		dataVisible=!tps.isEmpty();
		return null;
	}

	@Override
	public String displayList() {
		findAll();
		return "listTps?faces-redirect=true";
	}

	@Override
	public String displayNew() {
		tpsBean=new Tps();
		dialogVisible=true;
		return null;
	}

	@Override
	public String displayEdit() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String save() {
		if(tpsBean.getId()==null){
			repository.save(tpsBean);
		}else{
			repository.saveAndFlush(tpsBean);
		}
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Tps Baru dibuat untuk Kelurahan: "+tpsBean.getKelurahan().getName()));
		clear();
		return displayList();
	}

	@Override
	public String delete() {
		repository.delete(tpsBean);
		FacesMessage facesMessage = new FacesMessage("Successfully deleted");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        clear();
		return displayList();
	
	}

	@Override
	public void clear() {
		tpsBean=null;
		dialogVisible=false;
	}
	
	public HtmlPanelGrid populateGrid(){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		Application application = facesContext.getApplication();
		ExpressionFactory expressionFactory = application.getExpressionFactory();
		ELContext elContext = facesContext.getELContext();

		HtmlPanelGrid htmlPanelGrid = (HtmlPanelGrid) application.createComponent(HtmlPanelGrid.COMPONENT_TYPE);
		
		HtmlOutputText nameCreateOutput = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
		nameCreateOutput.setId("noCreateOutput");
		nameCreateOutput.setValue("No Tps: * ");
		htmlPanelGrid.getChildren().add(nameCreateOutput);
		
		numberValidator=new TpsNumberValidator();
		numberValidator.setTpsRepository(repository);
		Spinner noCreateInput = (Spinner) application.createComponent(Spinner.COMPONENT_TYPE);
        noCreateInput.setId("noCreateInput");
        noCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{tpsBean.tpsBean.nomor}", String.class));
        noCreateInput.setRequired(true);
        noCreateInput.setConverter(new IntegerConverter());
        noCreateInput.addValidator(numberValidator);
        htmlPanelGrid.getChildren().add(noCreateInput);
        
        Message noInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        noInputMessage.setId("noInputMessage");
        noInputMessage.setFor("noCreateInput");
        noInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(noInputMessage);
        
        HtmlOutputText kelurahanCreateOutput = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
		kelurahanCreateOutput.setId("kelurahanCreateOutput");
		kelurahanCreateOutput.setValue("Kelurahan: * ");
		htmlPanelGrid.getChildren().add(kelurahanCreateOutput);
        
        KelurahanConverter converter=new KelurahanConverter();
		converter.setRepository(kelurahanRepository);
		AutoComplete kelurahanInput = (AutoComplete) application.createComponent(AutoComplete.COMPONENT_TYPE);
        kelurahanInput.setId("kelurahanInput");
        kelurahanInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{tpsBean.tpsBean.kelurahan}", Kelurahan.class));
        kelurahanInput.setCompleteMethod(expressionFactory.createMethodExpression(elContext, "#{tpsBean.completeKelurahan}", List.class, new Class[] { String.class }));
        kelurahanInput.setDropdown(true);
        kelurahanInput.setValueExpression("var", expressionFactory.createValueExpression(elContext, "kelurahan", String.class));
        kelurahanInput.setValueExpression("itemLabel", expressionFactory.createValueExpression(elContext, "#{kelurahan.name}", String.class));
        kelurahanInput.setValueExpression("itemValue", expressionFactory.createValueExpression(elContext, "#{kelurahan}", Kelurahan.class));
        kelurahanInput.setConverter(converter);
        kelurahanInput.setRequired(true);
        htmlPanelGrid.getChildren().add(kelurahanInput);
        
        Message kelurahanInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        kelurahanInputMessage.setId("kelurahanInputMessage");
        kelurahanInputMessage.setFor("kelurahanInput");
        kelurahanInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(kelurahanInputMessage);
		
		return htmlPanelGrid;
	}
	
	public List<Kelurahan> completeKelurahan(String query){
		List<Kelurahan> suggestions=new ArrayList<Kelurahan>();
		for(Kelurahan kelurahan:kelurahanRepository.findAll()){
			String kecStr=String.valueOf(kelurahan.getName());
			if(kecStr.toLowerCase().startsWith(query)){
				suggestions.add(kelurahan);
			}
		}
		return suggestions;
	}

	@Override
	public boolean isDataVisible() {
		return dataVisible;
	}
	
	public void setDataVisible(boolean dataVisible) {
		this.dataVisible = dataVisible;
	}
	
	public void setTps(List<Tps> tps) {
		this.tps = tps;
	}
	
	public List<Tps> getTps() {
		return tps;
	}



	public boolean isDialogVisible() {
		return dialogVisible;
	}



	public void setDialogVisible(boolean dialogVisible) {
		this.dialogVisible = dialogVisible;
	}
	
	public void setTpsBean(Tps tpsBean) {
		this.tpsBean = tpsBean;
	}
	
	public Tps getTpsBean() {
		return tpsBean;
	}



	public TpsRepository getRepository() {
		return repository;
	}



	public void setRepository(TpsRepository repository) {
		this.repository = repository;
	}
		
	public void setCreatePanelGrid(HtmlPanelGrid createPanelGrid) {
		this.createPanelGrid = createPanelGrid;
	}
	
	public HtmlPanelGrid getCreatePanelGrid() {
		if(createPanelGrid==null)
			createPanelGrid=populateGrid();
		return createPanelGrid;
	}
	
	public void setKelurahanRepository(KelurahanRepository kelurahanRepository) {
		this.kelurahanRepository = kelurahanRepository;
	}
	
	public KelurahanRepository getKelurahanRepository() {
		return kelurahanRepository;
	}
	
}