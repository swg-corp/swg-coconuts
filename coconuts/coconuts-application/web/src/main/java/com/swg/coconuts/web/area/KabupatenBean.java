package com.swg.coconuts.web.area;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.swg.coconuts.backend.domain.Kabupaten;
import com.swg.coconuts.backend.repo.KabupatenRepository;
import com.swg.coconuts.web.Manager;

@ManagedBean
@SessionScoped
public class KabupatenBean implements Serializable,Manager{

	private static final long serialVersionUID = -4187409171102398919L;
	
	private List<Kabupaten> kabupatens;
	private Kabupaten kabupaten;
	@ManagedProperty("#{kabupatenRepository}")
	private KabupatenRepository repository;
	private boolean dataVisible;
	private boolean dialogVisible;
	
	public String findAll(){
		kabupatens=repository.findAll();
		dataVisible=!kabupatens.isEmpty();
		return null;
	}

	@Override
	public String displayList() {
		findAll();
		return "listKabupaten?faces-redirect=true";
	}

	@Override
	public boolean isDataVisible() {
		return dataVisible;
	}
	
	public boolean isDialogVisible() {
		return dialogVisible;
	}

	@Override
	public String save() {
		if(kabupaten.getId()==null){
			repository.save(kabupaten);
		}else{
			repository.saveAndFlush(kabupaten);
		}
		clear();
		return displayList();
	}

	@Override
	public String delete() {
		repository.delete(kabupaten);
		FacesMessage facesMessage = new FacesMessage("Successfully deleted");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        clear();
		return findAll();
	}

	@Override
	public void clear() {
		kabupaten=null;
		dialogVisible=false;
	}

	@Override
	public String displayNew() {
		kabupaten=new Kabupaten();
		dialogVisible=true;
		return null;
	}

	@Override
	public String displayEdit() {
		dialogVisible=true;
		return null;
	}

	public List<Kabupaten> getKabupatens() {
		return kabupatens;
	}

	public void setKabupatens(List<Kabupaten> kabupatens) {
		this.kabupatens = kabupatens;
	}

	public Kabupaten getKabupaten() {
		return kabupaten;
	}

	public void setKabupaten(Kabupaten kabupaten) {
		this.kabupaten = kabupaten;
	}

	public KabupatenRepository getRepository() {
		return repository;
	}

	public void setRepository(KabupatenRepository repository) {
		this.repository = repository;
	}

	public void setDialogVisible(boolean dialogVisible) {
		this.dialogVisible = dialogVisible;
	}
	
	public void setDataVisible(boolean dataVisible) {
		this.dataVisible = dataVisible;
	}

}
