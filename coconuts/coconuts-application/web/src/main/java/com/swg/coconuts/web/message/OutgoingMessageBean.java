package com.swg.coconuts.web.message;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.swg.coconuts.messaging.domain.OutgoingMessage;
import com.swg.coconuts.messaging.repo.OutgoingMessageRepository;
import com.swg.coconuts.web.DeleteEnable;
import com.swg.coconuts.web.ListableAware;

@ManagedBean
@SessionScoped
public class OutgoingMessageBean implements ListableAware,DeleteEnable,Serializable{

	private static final long serialVersionUID = 4325872970681104099L;

	@ManagedProperty("#{outgoingMessageRepository}")
	private OutgoingMessageRepository repository;
	private List<OutgoingMessage> outgoingSms;
	private OutgoingMessage sms;
	
	private boolean dataVisible;

	public String findAll(){
		outgoingSms=repository.findAll();
		dataVisible=!outgoingSms.isEmpty();
		return null;
	}
	
	@Override
	public boolean isDataVisible() {
		return dataVisible;
	}
	
	public void setDataVisible(boolean dataVisible) {
		this.dataVisible = dataVisible;
	}

	@Override
	public String displayList() {
		findAll();
		return "listPesanKeluar?faces-redirect=true";
	}

	@Override
	public String delete() {
		repository.delete(sms);
		FacesMessage facesMessage = new FacesMessage("Successfully deleted");
		FacesContext.getCurrentInstance().addMessage(null, facesMessage);
		clear();
		return findAll();
	}

	public void clear() {
		sms=null;
	}
	
	public void setRepository(OutgoingMessageRepository repository) {
		this.repository = repository;
	}
	
	public OutgoingMessageRepository getRepository() {
		return repository;
	}
	
	public OutgoingMessage getSms() {
		return sms;
	}
	public void setSms(OutgoingMessage sms) {
		this.sms = sms;
	}
	
	public void setOutgoingSms(List<OutgoingMessage> outgoingSms) {
		this.outgoingSms = outgoingSms;
	}
	
	public List<OutgoingMessage> getOutgoingSms() {
		return outgoingSms;
	}

}
