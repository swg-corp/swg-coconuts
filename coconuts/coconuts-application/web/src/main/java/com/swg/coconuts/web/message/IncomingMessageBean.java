package com.swg.coconuts.web.message;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import com.swg.coconuts.gateway.IIncomingMessage.ProcessStatus;
import com.swg.coconuts.messaging.domain.IncomingMessage;
import com.swg.coconuts.messaging.repo.IncomingMessageRepository;
import com.swg.coconuts.web.ListableAware;
import com.swg.coconuts.web.Updatable;

@ManagedBean
@SessionScoped
public class IncomingMessageBean implements ListableAware,Updatable,Serializable {

	private static final long serialVersionUID = 9002174701429359262L;

	@ManagedProperty("#{incomingMessageRepository}")
	private IncomingMessageRepository repository;
	
	private List<IncomingMessage> incomingSms;
	
	private List<SelectItem> processStatus;
	
	private IncomingMessage sms;
	
	private boolean dataVisible;

	public String findAll(){
		incomingSms=repository.findAll();
		dataVisible=!incomingSms.isEmpty();
		return null;
	}
	
	@Override
	public String displayList() {
		findAll();
		return "listPesanMasuk?faces-redirect=true";
	}

	

	@Override
	public String save() {
		return null;
	}

	@Override
	public String delete() {
		repository.delete(sms);
		FacesMessage facesMessage = new FacesMessage("Successfully deleted");
		FacesContext.getCurrentInstance().addMessage(null, facesMessage);
		clear();
		return findAll();
	}

	@Override
	public void clear() {
		sms=null;
	}

	public List<IncomingMessage> getIncomingSms() {
		return incomingSms;
	}

	public void setIncomingSms(List<IncomingMessage> incomingSms) {
		this.incomingSms = incomingSms;
	}

	public IncomingMessage getSms() {
		return sms;
	}

	public void setSms(IncomingMessage sms) {
		this.sms = sms;
	}

	public boolean isDataVisible() {
		return dataVisible;
	}

	public void setDataVisible(boolean dataVisible) {
		this.dataVisible = dataVisible;
	}

	public IncomingMessageRepository getRepository() {
		return repository;
	}

	public void setRepository(IncomingMessageRepository repository) {
		this.repository = repository;
	}
	
	public List<SelectItem> getProcessStatus() {
		if(processStatus==null){
			processStatus=new ArrayList<SelectItem>();
			processStatus.add(new SelectItem(ProcessStatus.NEW_MESSAGE));
			processStatus.add(new SelectItem(ProcessStatus.PROCESSING_MESSAGE));
			processStatus.add(new SelectItem(ProcessStatus.PROCESSED_MESSAGE));
			processStatus.add(new SelectItem(ProcessStatus.FAILED_MESSAGE));
		}
		return processStatus;
	}
	
	public void setProcessStatus(List<SelectItem> processStatus) {
		this.processStatus = processStatus;
	}
	
	
}
