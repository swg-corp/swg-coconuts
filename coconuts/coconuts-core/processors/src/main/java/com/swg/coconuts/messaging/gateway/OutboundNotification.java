package com.swg.coconuts.messaging.gateway;

import java.util.Calendar;

import org.apache.log4j.Logger;
import org.smslib.AGateway;
import org.smslib.IOutboundMessageNotification;
import org.smslib.OutboundMessage;
import org.smslib.OutboundMessage.MessageStatuses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.swg.coconuts.gateway.IOutgoingMessage.SendStatus;
import com.swg.coconuts.messaging.domain.OutgoingMessage;
import com.swg.coconuts.messaging.repo.OutgoingMessageRepository;

@Component
public class OutboundNotification implements IOutboundMessageNotification {

	private final Logger logger=Logger.getLogger(getClass());
	
	@Autowired
	private OutgoingMessageRepository repository;
	
	@Override
	public void process(AGateway gateway, OutboundMessage msg) {
		logger.info("Outbound Callback sending message from gateway: "+gateway.getGatewayId());
		OutgoingMessage message=repository.findOne(Integer.valueOf(msg.getId()));
		if(msg.getMessageStatus()==MessageStatuses.SENT){
			message.setSendStatus(SendStatus.SENT);
			message.setSentDate(Calendar.getInstance().getTime());
		}
		else if(msg.getMessageStatus()==MessageStatuses.FAILED)
			message.setSendStatus(SendStatus.FAILED);
		else if(msg.getMessageStatus()==MessageStatuses.UNSENT)
			message.setSendStatus(SendStatus.SEND);
		repository.save(message);
		
	}

}
