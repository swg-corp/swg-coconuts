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

import org.primefaces.component.autocomplete.AutoComplete;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.message.Message;

import com.swg.coconuts.backend.domain.Kecamatan;
import com.swg.coconuts.backend.domain.Kelurahan;
import com.swg.coconuts.backend.repo.KecamatanRepository;
import com.swg.coconuts.backend.repo.KelurahanRepository;
import com.swg.coconuts.web.Manager;
import com.swg.coconuts.web.converter.KecamatanConverter;

@ManagedBean
@SessionScoped
public class KelurahanBean implements Serializable,Manager{
	private static final long serialVersionUID = 2506687350144831075L;
	
	@ManagedProperty("#{kelurahanRepository}")
	private KelurahanRepository kelurahanRepository;
	@ManagedProperty("#{kecamatanRepository}")
	private KecamatanRepository kecamatanRepository;
	
	private Kelurahan kelurahan;
	private List<Kelurahan> kelurahans;
	
	private boolean dataVisible;
	private boolean dialogVisible=false;

	private HtmlPanelGrid createPanelGrid;
	
	public String findAll(){
		kelurahans=kelurahanRepository.findAll();
		dataVisible=!kelurahans.isEmpty();
		return null;
	}
	
	@Override
	public String save() {
		if(kelurahan.getId()==null){
			kelurahanRepository.save(kelurahan);
		}else{
			kelurahanRepository.save(kelurahan);
		}
		clear();
		return findAll();
	}

	@Override
	public String delete() {
		kelurahanRepository.delete(kelurahan);
		FacesMessage facesMessage = new FacesMessage("Successfully deleted");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        clear();
		return findAll();	
	}
	@Override
	public void clear() {
		kelurahan=null;
		dialogVisible=false;
	}

	@Override
	public String displayList() {
		dialogVisible=false;
		findAll();
		return "listKelurahan?faces-redirect=true";
	}
	@Override
	public String displayNew() {
		kelurahan=new Kelurahan();
		dialogVisible=true;
		return null;
	}

	@Override
	public String displayEdit() {
		dialogVisible=true;
		return null;
	}

	public HtmlPanelGrid populateGrid(){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		Application application = facesContext.getApplication();
		ExpressionFactory expressionFactory = application.getExpressionFactory();
		ELContext elContext = facesContext.getELContext();

		HtmlPanelGrid htmlPanelGrid = (HtmlPanelGrid) application.createComponent(HtmlPanelGrid.COMPONENT_TYPE);
		
		HtmlOutputText nameCreateOutput = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
		nameCreateOutput.setId("nameCreateOutput");
		nameCreateOutput.setValue("Nama Kelurahan: * ");
		htmlPanelGrid.getChildren().add(nameCreateOutput);
		
		InputText nameCreateInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        nameCreateInput.setId("nameCreateInput");
        nameCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{kelurahanBean.kelurahan.name}", String.class));
        nameCreateInput.setRequired(true);
        htmlPanelGrid.getChildren().add(nameCreateInput);
        
        Message nameCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        nameCreateInputMessage.setId("nameCreateInputMessage");
        nameCreateInputMessage.setFor("nameCreateInput");
        nameCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(nameCreateInputMessage);
        
        HtmlOutputText codeCreateOutput = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
		codeCreateOutput.setId("codeCreateOutput");
		codeCreateOutput.setValue("Kode:* ");
		htmlPanelGrid.getChildren().add(codeCreateOutput);
		
		InputText codeCreateInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        codeCreateInput.setId("codeCreateInput");
        codeCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{kelurahanBean.kelurahan.code}", String.class));
        codeCreateInput.setRequired(true);
        htmlPanelGrid.getChildren().add(codeCreateInput);
        
        Message codeCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        codeCreateInputMessage.setId("codeCreateInputMessage");
        codeCreateInputMessage.setFor("codeCreateInput");
        codeCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(codeCreateInputMessage);
        
        HtmlOutputText kecamatanCreateOutput = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
		kecamatanCreateOutput.setId("kecamatanCreateOutput");
		kecamatanCreateOutput.setValue("Kecamatan: ");
		htmlPanelGrid.getChildren().add(kecamatanCreateOutput);
		
		KecamatanConverter converter=new KecamatanConverter();
		converter.setRepository(kecamatanRepository);
		
		AutoComplete kecamatanInput = (AutoComplete) application.createComponent(AutoComplete.COMPONENT_TYPE);
        kecamatanInput.setId("kecamatanInput");
        kecamatanInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{kelurahanBean.kelurahan.kecamatan}", Kecamatan.class));
        kecamatanInput.setCompleteMethod(expressionFactory.createMethodExpression(elContext, "#{kelurahanBean.completeKecamatan}", List.class, new Class[] { String.class }));
        kecamatanInput.setDropdown(true);
        kecamatanInput.setValueExpression("var", expressionFactory.createValueExpression(elContext, "kecamatan", String.class));
        kecamatanInput.setValueExpression("itemLabel", expressionFactory.createValueExpression(elContext, "#{kecamatan.name}", String.class));
        kecamatanInput.setValueExpression("itemValue", expressionFactory.createValueExpression(elContext, "#{kecamatan}", Kecamatan.class));
        kecamatanInput.setConverter(converter);
        kecamatanInput.setRequired(true);
        htmlPanelGrid.getChildren().add(kecamatanInput);
        
        Message kecamatanInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        kecamatanInputMessage.setId("kecamatanInputMessage");
        kecamatanInputMessage.setFor("kecamatanInput");
        kecamatanInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(kecamatanInputMessage);
		
		
		return htmlPanelGrid;
	}
	
	public List<Kecamatan> completeKecamatan(String query){
		List<Kecamatan> suggestions=new ArrayList<Kecamatan>();
		for(Kecamatan kecamatan:kecamatanRepository.findAll()){
			String kecStr=String.valueOf(kecamatan.getName());
			if(kecStr.toLowerCase().startsWith(query)){
				suggestions.add(kecamatan);
			}
		}
		return suggestions;
	}

	public KelurahanRepository getKelurahanRepository() {
		return kelurahanRepository;
	}

	public void setKelurahanRepository(KelurahanRepository kelurahanRepository) {
		this.kelurahanRepository = kelurahanRepository;
	}

	public KecamatanRepository getKecamatanRepository() {
		return kecamatanRepository;
	}

	public void setKecamatanRepository(KecamatanRepository kecamatanRepository) {
		this.kecamatanRepository = kecamatanRepository;
	}

	public Kelurahan getKelurahan() {
		return kelurahan;
	}

	public void setKelurahan(Kelurahan kelurahan) {
		this.kelurahan = kelurahan;
	}

	public List<Kelurahan> getKelurahans() {
		return kelurahans;
	}

	public void setKelurahans(List<Kelurahan> kelurahans) {
		this.kelurahans = kelurahans;
	}

	public boolean isDataVisible() {
		return dataVisible;
	}

	public void setDataVisible(boolean dataVisible) {
		this.dataVisible = dataVisible;
	}

	public boolean isDialogVisible() {
		return dialogVisible;
	}

	public void setDialogVisible(boolean dialogVisible) {
		this.dialogVisible = dialogVisible;
	}

	public HtmlPanelGrid getCreatePanelGrid() {
		if(createPanelGrid==null)
			createPanelGrid=populateGrid();
		return createPanelGrid;
	}

	public void setCreatePanelGrid(HtmlPanelGrid createPanelGrid) {
		this.createPanelGrid = createPanelGrid;
	}

	

}
