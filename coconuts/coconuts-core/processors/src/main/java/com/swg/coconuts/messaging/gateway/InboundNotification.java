package com.swg.coconuts.messaging.gateway;

import java.util.Calendar;

import org.apache.log4j.Logger;
import org.smslib.AGateway;
import org.smslib.IInboundMessageNotification;
import org.smslib.InboundMessage;
import org.smslib.Message.MessageTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.swg.coconuts.messaging.domain.IncomingMessage;
import com.swg.coconuts.messaging.repo.IncomingMessageRepository;

@Component
public class InboundNotification implements IInboundMessageNotification {

	private final Logger logger=Logger.getLogger(getClass());
	
	@Autowired
	private IncomingMessageRepository repository;
	
	@Override
	public void process(AGateway gateway, MessageTypes msgType,InboundMessage msg) {
		if (msgType == MessageTypes.INBOUND){
			logger.info("New Inbound message detected from Gateway: " + gateway.getGatewayId());
			try{
				IncomingMessage incomingSms=new IncomingMessage();
				incomingSms.setOrigin(msg.getOriginator());
				incomingSms.setContent(msg.getText());
				incomingSms.setReceivedDate(Calendar.getInstance().getTime());
				repository.save(incomingSms);
				gateway.deleteMessage(msg);
			}catch(Throwable e){
				
			}
		}
		else if (msgType == MessageTypes.STATUSREPORT) 
			logger.info("New Inbound Status Report message detected from Gateway: " + gateway.getGatewayId());
	}

}
