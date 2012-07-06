package com.swg.coconuts.web.message;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.swg.coconuts.gateway.IIncomingMessage;
import com.swg.coconuts.gateway.IIncomingMessage.ProcessStatus;
import com.swg.coconuts.messaging.domain.IncomingMessage;
import com.swg.coconuts.messaging.repo.IncomingMessageRepository;
import com.swg.coconuts.web.ListableAware;
import com.swg.coconuts.web.Updatable;

@ManagedBean
@SessionScoped
public class FailedMessageBean implements ListableAware,Updatable,Serializable{

	private static final long serialVersionUID = -6033853201224558438L;

	@ManagedProperty("#{incomingMessageRepository}")
	private IncomingMessageRepository incomingMessageRepository;
	
	private List<IIncomingMessage> incomingSms;
	private boolean dataVisible;
	private IncomingMessage sms;
	
	public void setIncomingMessageRepository(
			IncomingMessageRepository incomingMessageRepository) {
		this.incomingMessageRepository = incomingMessageRepository;
	}
	
	public IncomingMessageRepository getIncomingMessageRepository() {
		return incomingMessageRepository;
	}

	public List<IIncomingMessage> getIncomingSms() {
		return incomingSms;
	}

	public void setIncomingSms(List<IIncomingMessage> incomingSms) {
		this.incomingSms = incomingSms;
	}

	public IncomingMessage getSms() {
		return sms;
	}

	public void setSms(IncomingMessage sms) {
		this.sms = sms;
	}

	@Override
	public boolean isDataVisible() {
		return dataVisible;
	}
	
	public void setDataVisible(boolean dataVisible) {
		this.dataVisible = dataVisible;
	}
	
	public String findAll(){
		incomingSms=incomingMessageRepository.findByProcessStatus(ProcessStatus.FAILED_MESSAGE);
		dataVisible=!incomingSms.isEmpty();
		return null;
	}

	@Override
	public String displayList() {
		findAll();
		return "listPesanFailed";
	}
	
	@Override
	public String delete() {
		incomingMessageRepository.delete(sms);
		FacesMessage facesMessage = new FacesMessage("Successfully deleted");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        sms=null;
		return findAll();
	}

	@Override
	public String save() {
		sms.setProcessStatus(ProcessStatus.REPROCESS_MESSAGE);
		incomingMessageRepository.saveAndFlush(sms);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("isi pesan diubah"));
		return null;
	}

	@Override
	public void clear() {
		sms=null;
	}
}
