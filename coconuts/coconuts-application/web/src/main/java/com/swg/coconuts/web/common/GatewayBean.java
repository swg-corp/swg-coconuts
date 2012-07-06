package com.swg.coconuts.web.common;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.swg.coconuts.gateway.provider.IGatewayServiceProvider;
import com.swg.coconuts.gateway.provider.IModemGateway;
import com.swg.coconuts.gateway.provider.IGatewayServiceProvider.ServiceStatus;
import com.swg.coconuts.messaging.domain.OutgoingMessage;
import com.swg.coconuts.messaging.domain.SerialModem;
import com.swg.coconuts.messaging.repo.SerialModemRepository;
import com.swg.coconuts.web.Manager;

@ManagedBean
@SessionScoped
public class GatewayBean implements Manager,Serializable{
	private static final long serialVersionUID = -5149149990848619593L;
	
	private SerialModem gateway;
	
	private List<SerialModem> gateways;
	
	@ManagedProperty("#{gatewayServiceProvider}")
	private IGatewayServiceProvider serviceProvider;
	
	@ManagedProperty("#{serialModemRepository}")
	private SerialModemRepository repository;
	
	private OutgoingMessage message;
	
	private boolean dataVisible;
	
	private String gatewayStatus;
	
	private boolean started;
	
	private boolean dialogVisible;

	public String findAll(){
		gateways=repository.findAll();
		dataVisible=!gateways.isEmpty();
		gatewayStatus=serviceProvider.getServiceStatus().name();
		started=serviceProvider.getServiceStatus()==ServiceStatus.Start;
		return null;
	}
	
	@Override
	public String displayList() {
		findAll();
		return "gateway?faces-redirect=true";
	}

	@Override
	public String displayNew() {
		gateway=new SerialModem();
		dialogVisible=true;
		return null;
	}

	@Override
	public String displayEdit() {
		dialogVisible=true;
		return null;
	}

	@Override
	public String save() {
		if(gateway.getId()==null){
			repository.save(gateway);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Gateway baru berhasil dibuat"));
		}else{
			repository.saveAndFlush(gateway);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Gateway "+gateway.getGatewayId()+" diubah"));
		}
		clear();
		return findAll();

	}
	
	public String command(){
		FacesMessage facesMessage=null;
		if(serviceProvider.getServiceStatus()==ServiceStatus.Stop){
			serviceProvider.registerGateway(gateway);
			serviceProvider.startService();
			dataVisible=false;
			facesMessage=new FacesMessage(FacesMessage.SEVERITY_INFO, "Gateway Info", "gateway started");
		}else{
			serviceProvider.stopService();
			dataVisible=true;
			facesMessage=new FacesMessage(FacesMessage.SEVERITY_INFO, "Gateway Info", "gateway stop");
		}
		FacesContext.getCurrentInstance().addMessage(null, facesMessage);
		return displayList();
	}
	
	public String start(){
		FacesMessage facesMessage=null;
		if(serviceProvider.getServiceStatus()==ServiceStatus.Stop){
			serviceProvider.registerGateway(gateway);
			serviceProvider.startService();
			dataVisible=false;
			facesMessage=new FacesMessage(FacesMessage.SEVERITY_INFO, "Gateway Info", "gateway started");
		}
		FacesContext.getCurrentInstance().addMessage(null, facesMessage);
		return findAll();
	}
	
	public String stop(){
		FacesMessage facesMessage=null;
		serviceProvider.stopService();
		dataVisible=true;
		for(IModemGateway im:gateways){
			if(serviceProvider.isRegistered(im))
				serviceProvider.unregisterGateway(im);
		}
		facesMessage=new FacesMessage(FacesMessage.SEVERITY_INFO, "Gateway Info", "gateway stop");
		FacesContext.getCurrentInstance().addMessage(null, facesMessage);
		return findAll();
	}
	
	public String displaySendForm(){
		message=new OutgoingMessage();
		return "sendForm";
	}
	
	public String send(){
		FacesMessage facesMessage=null;
		serviceProvider.sendMessage(message);
		facesMessage=new FacesMessage("Outgoing Message Processed");
		FacesContext.getCurrentInstance().addMessage(null, facesMessage);
		message=null;
		return displaySendForm();
	}

	@Override
	public String delete() {
		repository.delete(gateway);
		if(serviceProvider.isRegistered(gateway)){
			serviceProvider.unregisterGateway(gateway);
		}
		FacesMessage facesMessage = new FacesMessage("Successfully deleted");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        clear();
		return findAll();
	}

	@Override
	public void clear() {
		gateway=null;
		
	}
	
	public void setDataVisible(boolean dataVisible) {
		this.dataVisible = dataVisible;
	}

	@Override
	public boolean isDataVisible() {
		return dataVisible;
	}
	
	public void setStarted(boolean started) {
		this.started = started;
	}
	
	public boolean isStarted() {
		return started;
	}
	
	public void setGatewayStatus(String gatewayStatus) {
		this.gatewayStatus = gatewayStatus;
	}
	
	public String getGatewayStatus() {
		return gatewayStatus;
	}
	
	public void setServiceProvider(IGatewayServiceProvider serviceProvider) {
		this.serviceProvider = serviceProvider;
	}
	
	public IGatewayServiceProvider getServiceProvider() {
		return serviceProvider;
	}

	public boolean isDialogVisible() {
		return dialogVisible;
	}

	public void setDialogVisible(boolean dialogVisible) {
		this.dialogVisible = dialogVisible;
	}

	public SerialModem getGateway() {
		return gateway;
	}

	public List<SerialModem> getGateways() {
		return gateways;
	}

	public void setGateways(List<SerialModem> gateways) {
		this.gateways = gateways;
	}

	public SerialModemRepository getRepository() {
		return repository;
	}

	public void setRepository(SerialModemRepository repository) {
		this.repository = repository;
	}

	public void setGateway(SerialModem gateway) {
		this.gateway = gateway;
	}

	public void setMessage(OutgoingMessage message) {
		this.message = message;
	}
	
	public OutgoingMessage getMessage() {
		return message;
	}


}
