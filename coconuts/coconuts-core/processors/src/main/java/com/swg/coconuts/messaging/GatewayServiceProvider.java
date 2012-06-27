package com.swg.coconuts.messaging;



import java.util.Calendar;

import org.apache.log4j.Logger;
import org.smslib.AGateway;
import org.smslib.modem.SerialModemGateway;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.swg.coconuts.gateway.IOutgoingMessage;
import com.swg.coconuts.gateway.IOutgoingMessage.SendStatus;
import com.swg.coconuts.gateway.provider.IGatewayProvider.SerialModemGatewayProvider;
import com.swg.coconuts.gateway.provider.IGatewayService;
import com.swg.coconuts.gateway.provider.IGatewayServiceProvider;
import com.swg.coconuts.gateway.provider.IModemGateway;
import com.swg.coconuts.gateway.provider.ISerialModemGatewayService;
import com.swg.coconuts.gateway.provider.SerialModemGatewayServiceFactory;
import com.swg.coconuts.messaging.domain.OutgoingMessage;
import com.swg.coconuts.messaging.gateway.GatewayStatusNotification;
import com.swg.coconuts.messaging.gateway.InboundNotification;
import com.swg.coconuts.messaging.gateway.OutboundNotification;
import com.swg.coconuts.messaging.repo.OutgoingMessageRepository;

@Component
public class GatewayServiceProvider implements IGatewayServiceProvider,InitializingBean {

	private final Logger logger=Logger.getLogger(getClass());
	@Autowired
	private OutgoingMessageRepository repository;
	@Autowired
	private SerialModemGatewayServiceFactory gatewayServiceFactory;
	@Autowired
	private SerialModemGatewayProvider gatewayProvider;
	@Autowired
	private GatewayStatusNotification statusNotification;
	@Autowired
	private InboundNotification inboundNotification;
	@Autowired
	private OutboundNotification outboundNotification;
	
	private ISerialModemGatewayService gatewayService;
	
	private String command;
	
	private ServiceStatus serviceStatus=ServiceStatus.Stop;
	
	@Override
	public void registerGateway(IModemGateway gateway) {
		SerialModemGateway modemGateway=this.gatewayProvider.getSmslibGateway(gateway);
		logger.info("adding gateway: "+modemGateway.getGatewayId());
		gatewayService.addGateway(modemGateway);

	}

	@Override
	public void unregisterGateway(IModemGateway gateway) {
		AGateway modemGateway=gatewayService.getGateway(gateway.getGatewayId());
		gatewayService.removeGateway(modemGateway);

	}

	@Override
	public boolean isRegistered(IModemGateway gateway) {
		AGateway modemGateway=gatewayService.getGateway(gateway.getGatewayId());
		if(modemGateway==null)
			return false;
		return true;
	}

	@Override
	public void startService() {
		this.gatewayService.addInboundNotification(inboundNotification);
		this.gatewayService.addGatewayStatusNotification(statusNotification);
		this.gatewayService.addOutboundNotification(outboundNotification);
		gatewayService.start();
		this.serviceStatus=ServiceStatus.Start;
		command="Stop";

	}

	@Override
	public void stopService() {
		gatewayService.stop();
		this.serviceStatus=ServiceStatus.Stop;
		command="Start";

	}

	@Override
	public String getCommand() {
		return command;
	}

	@Override
	public String getStatus() {
		return serviceStatus.name();
	}
	
	public void setServiceStatus(ServiceStatus serviceStatus) {
		this.serviceStatus = serviceStatus;
	}
	
	public ServiceStatus getServiceStatus() {
		return serviceStatus;
	}
	

	@Override
	public void afterPropertiesSet() throws Exception {
		if(gatewayService==null)
			this.gatewayService=(ISerialModemGatewayService)gatewayServiceFactory.getGatewayService();
		
	}

	@Override
	public void sendMessage(IOutgoingMessage message) {
		queue(message);
	}

	@Override
	public void queue(IOutgoingMessage message) {
		if(!OutgoingMessage.class.isAssignableFrom(message.getClass())){
			message=new OutgoingMessage();
			message.setContent(message.getContent());
			message.setRecipient(message.getRecipient());
			message.setCreateDate(Calendar.getInstance().getTime());
		}
		message.setSendStatus(SendStatus.NEW_MESSAGE);
		repository.save((OutgoingMessage)message);
	}
	
	@Override
	public IGatewayService getGatewayService() {
		return gatewayService;
	}

}
