package com.swg.coconuts.web.common;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.swg.coconuts.backend.domain.Candidate;
import com.swg.coconuts.backend.domain.CandidateCouple;
import com.swg.coconuts.backend.repo.CandidateCoupleRepository;
import com.swg.coconuts.web.Manager;
import com.swg.coconuts.web.validator.CandidateNameValidator;
import com.swg.coconuts.web.validator.CandidateNumberValidator;

@ManagedBean
@SessionScoped
public class CandidateBean implements Manager,Serializable{

	private static final long serialVersionUID = 6246646482221734440L;
	
	private CandidateCouple couple;

	private Candidate head;

	private Candidate vice;

	private List<CandidateCouple> couples;

	private boolean dataVisible;

	@ManagedProperty("#{candidateCoupleRepository}")
	CandidateCoupleRepository repository;
	
	CandidateNumberValidator numberValidator;
	CandidateNameValidator nameValidator;

	public String displayNew(){
		couple=new CandidateCouple();
		head=new Candidate();
		vice=new Candidate();
		couple.setHead(head);
		couple.setVice(vice);
		return "editPasangan?faces-redirect=true";
	}

	public String findAll(){
		couples=repository.findAll();
		dataVisible=!couples.isEmpty();
		return null;
	}

	@Override
	public String displayList() {
		findAll();
		return "listPasangan?faces-redirect=true";
	}

	@Override
	public String displayEdit() {
		return "editPasangan?faces-redirect=true";
	}

	@Override
	public String save() {
		if(couple.getId()==null)
			repository.save(couple);
		else
			repository.saveAndFlush(couple);
		clear();
		return displayList();
	}
	@Override
	public String delete() {
		repository.delete(couple);
		FacesMessage facesMessage = new FacesMessage("Successfully deleted");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        clear();
		return findAll();
	}
	
	@Override
	public void clear() {
		couple=null;
		vice=null;
		head=null;
	}



	@Override
	public boolean isDataVisible() {
		return dataVisible;
	}

	public void setDataVisible(boolean dataVisible) {
		this.dataVisible = dataVisible;
	}


	public void setCouple(CandidateCouple couple) {
		this.couple = couple;
	}

	public CandidateCouple getCouple() {
		return couple;
	}

	public Candidate getHead() {
		return head;
	}

	public void setHead(Candidate head) {
		this.head = head;
	}

	public Candidate getVice() {
		return vice;
	}

	public void setVice(Candidate vice) {
		this.vice = vice;
	}

	public List<CandidateCouple> getCouples() {
		return couples;
	}

	public void setCouples(List<CandidateCouple> couples) {
		this.couples = couples;
	}

	public CandidateCoupleRepository getRepository() {
		return repository;
	}

	public void setRepository(CandidateCoupleRepository repository) {
		this.repository = repository;
	}
	
	public void setNameValidator(CandidateNameValidator nameValidator) {
		this.nameValidator = nameValidator;
	}
	
	public CandidateNameValidator getNameValidator() {
		if(nameValidator==null){
			nameValidator=new CandidateNameValidator();
			nameValidator.setCoupleRepository(repository);
		}
		return nameValidator;
	}
	
	public void setNumberValidator(CandidateNumberValidator numberValidator) {
		this.numberValidator = numberValidator;
	}
	
	public CandidateNumberValidator getNumberValidator() {
		if(numberValidator==null){
			numberValidator=new CandidateNumberValidator();
			numberValidator.setCoupleRepository(repository);
		}
		return numberValidator;
	}
	

}
