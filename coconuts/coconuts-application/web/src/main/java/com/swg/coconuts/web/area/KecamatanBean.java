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

import com.swg.coconuts.backend.domain.Kabupaten;
import com.swg.coconuts.backend.domain.Kecamatan;
import com.swg.coconuts.backend.repo.KabupatenRepository;
import com.swg.coconuts.backend.repo.KecamatanRepository;
import com.swg.coconuts.web.Manager;
import com.swg.coconuts.web.converter.KabupatenConverter;

@ManagedBean
@SessionScoped
public class KecamatanBean implements Serializable,Manager{

	private static final long serialVersionUID = -8357893099352335365L;

	@ManagedProperty("#{kecamatanRepository}")
	private KecamatanRepository kecamatanRepository;
	@ManagedProperty("#{kabupatenRepository}")
	private KabupatenRepository kabupatenRepository;

	private Kecamatan kecamatan;
	private List<Kecamatan> kecamatans;
	private boolean dataVisible;
	private boolean dialogVisible=false;

	private HtmlPanelGrid createPanelGrid;

	public String findAll(){
		kecamatans=kecamatanRepository.findAll();
		dataVisible=!kecamatans.isEmpty();
		return null;
	}

	@Override
	public String save() {
		if(kecamatan.getId()==null){
			kecamatanRepository.save(kecamatan);
		}else{
			kecamatanRepository.saveAndFlush(kecamatan);
		}
		clear();
		return findAll();
	}

	@Override
	public String delete() {
		kecamatanRepository.delete(kecamatan);
		FacesMessage facesMessage = new FacesMessage("Successfully deleted");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        clear();
		return findAll();	
	}

	@Override
	public void clear() {
		kecamatan=null;
		dialogVisible=false;
	}



	@Override
	public String displayList() {
		dialogVisible=false;
		findAll();
		return "listKecamatan?faces-redirect=true";
	}



	@Override
	public String displayNew() {
		kecamatan=new Kecamatan();
		dialogVisible=true;
		return null;
	}



	@Override
	public String displayEdit() {
		dialogVisible=true;
		return null;
	}



	public HtmlPanelGrid getCreatePanelGrid() {
		if(createPanelGrid==null)
			createPanelGrid=populateGrid();
		return createPanelGrid;
	}

	public void setCreatePanelGrid(HtmlPanelGrid createPanelGrid) {
		this.createPanelGrid = createPanelGrid;
	}

	public HtmlPanelGrid populateGrid(){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		Application application = facesContext.getApplication();
		ExpressionFactory expressionFactory = application.getExpressionFactory();
		ELContext elContext = facesContext.getELContext();

		HtmlPanelGrid htmlPanelGrid = (HtmlPanelGrid) application.createComponent(HtmlPanelGrid.COMPONENT_TYPE);
		
		HtmlOutputText nameCreateOutput = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
		nameCreateOutput.setId("nameCreateOutput");
		nameCreateOutput.setValue("Nama Kecamatan: * ");
		htmlPanelGrid.getChildren().add(nameCreateOutput);
		
		InputText nameCreateInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        nameCreateInput.setId("nameCreateInput");
        nameCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{kecamatanBean.kecamatan.name}", String.class));
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
        codeCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{kecamatanBean.kecamatan.code}", String.class));
        codeCreateInput.setRequired(true);
        htmlPanelGrid.getChildren().add(codeCreateInput);
        
        Message codeCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        codeCreateInputMessage.setId("codeCreateInputMessage");
        codeCreateInputMessage.setFor("codeCreateInput");
        codeCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(codeCreateInputMessage);
        
        HtmlOutputText kabupatenCreateOutput = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
		kabupatenCreateOutput.setId("kabupatenCreateOutput");
		kabupatenCreateOutput.setValue("Kabupaten: ");
		htmlPanelGrid.getChildren().add(kabupatenCreateOutput);
		
		KabupatenConverter converter=new KabupatenConverter();
		converter.setRepository(kabupatenRepository);
		
		AutoComplete kabupatenInput = (AutoComplete) application.createComponent(AutoComplete.COMPONENT_TYPE);
        kabupatenInput.setId("kabupatenInput");
        kabupatenInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{kecamatanBean.kecamatan.kabupaten}", Kabupaten.class));
        kabupatenInput.setCompleteMethod(expressionFactory.createMethodExpression(elContext, "#{kecamatanBean.completeKabupaten}", List.class, new Class[] { String.class }));
        kabupatenInput.setDropdown(true);
        kabupatenInput.setValueExpression("var", expressionFactory.createValueExpression(elContext, "kabupaten", String.class));
        kabupatenInput.setValueExpression("itemLabel", expressionFactory.createValueExpression(elContext, "#{kabupaten.name}", String.class));
        kabupatenInput.setValueExpression("itemValue", expressionFactory.createValueExpression(elContext, "#{kabupaten}", Kabupaten.class));
        kabupatenInput.setConverter(converter);
        kabupatenInput.setRequired(true);
        htmlPanelGrid.getChildren().add(kabupatenInput);
        
        Message kabupatenInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        kabupatenInputMessage.setId("kabupatenInputMessage");
        kabupatenInputMessage.setFor("kabupatenInput");
        kabupatenInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(kabupatenInputMessage);
		
		
		return htmlPanelGrid;
	}
	
	public List<Kabupaten> completeKabupaten(String query){
		List<Kabupaten> suggestions=new ArrayList<Kabupaten>();
		for(Kabupaten kabupaten:kabupatenRepository.findAll()){
			String kabStr=String.valueOf(kabupaten.getName());
			if(kabStr.toLowerCase().startsWith(query)){
				suggestions.add(kabupaten);
			}
		}
		return suggestions;
	}

	public KecamatanRepository getKecamatanRepository() {
		return kecamatanRepository;
	}

	public void setKecamatanRepository(KecamatanRepository kecamatanRepository) {
		this.kecamatanRepository = kecamatanRepository;
	}

	public KabupatenRepository getKabupatenRepository() {
		return kabupatenRepository;
	}

	public void setKabupatenRepository(KabupatenRepository kabupatenRepository) {
		this.kabupatenRepository = kabupatenRepository;
	}

	public Kecamatan getKecamatan() {
		return kecamatan;
	}

	public void setKecamatan(Kecamatan kecamatan) {
		this.kecamatan = kecamatan;
	}

	public List<Kecamatan> getKecamatans() {
		return kecamatans;
	}

	public void setKecamatans(List<Kecamatan> kecamatans) {
		this.kecamatans = kecamatans;
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
	
	

}
