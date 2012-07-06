package com.swg.coconuts.web.common;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.swg.coconuts.backend.domain.VoteSender;
import com.swg.coconuts.backend.repo.VoteSenderRepository;
import com.swg.coconuts.web.Manager;

@ManagedBean
@SessionScoped
public class VoteSenderBean implements Serializable,Manager {
	private static final long serialVersionUID = 1174189693676562508L;

	@ManagedProperty("#{voteSenderRepository}")
	VoteSenderRepository repository;
	
	private VoteSender voteSender;
	
	private List<VoteSender> voteSenders;
	
	private boolean dataVisible;
	
	
	public String findAll(){
		voteSenders=repository.findAll();
		dataVisible=!voteSenders.isEmpty();
		return null;
	}
	
	@Override
	public String displayList(){
		findAll();
		return "listPemantau?faces-redirect=true";
	}
	
	@Override
	public String displayNew(){
		voteSender=new VoteSender();
		return "editPemantau";
	}
	
	@Override
	public String displayEdit(){
		return "editPemantau?faces-redirect=true";
	}
	
	@Override
	public String save(){
		if(voteSender.getId()==null)
			repository.save(voteSender);
		else
			repository.saveAndFlush(voteSender);
		clear();
		return displayList();
	}
	
	@Override
	public String delete(){
		repository.delete(voteSender);
		FacesMessage facesMessage = new FacesMessage("Successfully deleted");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        clear();
		return findAll();
	}

	public List<VoteSender> getVoteSenders() {
		return voteSenders;
	}

	public void setVoteSenders(List<VoteSender> voteSenders) {
		this.voteSenders = voteSenders;
	}

	public VoteSender getVoteSender() {
		return voteSender;
	}

	public void setVoteSender(VoteSender voteSender) {
		this.voteSender = voteSender;
	}
	
	@Override
	public void clear(){
		voteSender=null;
	}

	public boolean isDataVisible() {
		return dataVisible;
	}

	public void setDataVisible(boolean dataVisible) {
		this.dataVisible = dataVisible;
	}

	public VoteSenderRepository getRepository() {
		return repository;
	}

	public void setRepository(VoteSenderRepository repository) {
		this.repository = repository;
	}
	

}
