package com.swg.coconuts.messaging;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.smslib.OutboundMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.swg.coconuts.gateway.IIncomingMessage;
import com.swg.coconuts.gateway.IIncomingMessage.ProcessStatus;
import com.swg.coconuts.gateway.IOutgoingMessage;
import com.swg.coconuts.gateway.IOutgoingMessage.SendStatus;
import com.swg.coconuts.gateway.processor.MessageProcessingException;
import com.swg.coconuts.gateway.processor.MessageProcessor;
import com.swg.coconuts.gateway.provider.IGatewayServiceProvider;
import com.swg.coconuts.gateway.provider.ISerialModemGatewayService;
import com.swg.coconuts.messaging.domain.IncomingMessage;
import com.swg.coconuts.messaging.repo.IncomingMessageRepository;
import com.swg.coconuts.messaging.repo.OutgoingMessageRepository;

@Component
public class ScheduledMessagingService implements MessagingService,ApplicationEventPublisherAware{

	private Logger logger=Logger.getLogger(getClass());
	
	@Autowired(required=true)
	private MessageProcessor messageProcessor;
	
	@Autowired(required=true)
	private IncomingMessageRepository repository;
	@Autowired(required=true)
	private OutgoingMessageRepository outgoingMessageRepository;
	@Autowired(required=true)
	private IGatewayServiceProvider gatewayServiceProvider;
	
	private ApplicationEventPublisher applicationEventPublisher;
	
	@Override
	public void process(IIncomingMessage inboundMessage) {
		logger.info("Process inbound message.");
		
		if(inboundMessage.getProcessStatus() != ProcessStatus.NEW_MESSAGE && inboundMessage.getProcessStatus() != ProcessStatus.REPROCESS_MESSAGE) {
			throw new MessagingServiceException("Tidak dapat memproses pesan masuk yang sudah diproses.");
		}
		
		inboundMessage = ensurePersistableMessage(inboundMessage);
		inboundMessage.setProcessStatus(ProcessStatus.PROCESSING_MESSAGE);
		//repository.save((IncomingMessage) inboundMessage);
		IIncomingMessage msg=repository.save((IncomingMessage) inboundMessage);
	//	int status=msg.getStatus();
		try {
			messageProcessor.processMessage(inboundMessage);
			msg.setProcessStatus(ProcessStatus.PROCESSED_MESSAGE);
			repository.save((IncomingMessage) msg);
		}
		catch(MessageProcessingException e) {
			msg.setProcessStatus(ProcessStatus.FAILED_MESSAGE);
			repository.save((IncomingMessage) msg);
		}
		logger.info(msg.getProcessStatus());
	
		
	}
	@Override
	public void process(IOutgoingMessage outgoingMessage){
		OutboundMessage message=new OutboundMessage();
		message.setId(String.valueOf(outgoingMessage.getId()));
		message.setText(outgoingMessage.getContent());
		message.setRecipient(outgoingMessage.getRecipient());
		ISerialModemGatewayService gatewayService=(ISerialModemGatewayService) gatewayServiceProvider.getGatewayService();
		gatewayService.queueMessage(message);
	}

	@Override
	public void reprocess(IIncomingMessage inboundMessage) {
		logger.info("Reproses message.");
		inboundMessage = ensurePersistableMessage(inboundMessage);
		inboundMessage.setProcessStatus(ProcessStatus.REPROCESS_MESSAGE);
		repository.save((IncomingMessage) inboundMessage);
		
	}
	
	private IncomingMessage ensurePersistableMessage(IIncomingMessage inboundMessage) {
		logger.debug("Ensure persistable message. If not, convert to persistable message.");
		if(!IncomingMessage.class.isAssignableFrom(inboundMessage.getClass())) {
			
		}
		return (IncomingMessage)inboundMessage;
	}
	
	@Scheduled(fixedDelay=50000)
	synchronized void checkAndProcessInboundMessage() {
		logger.info("Check and process inbound message.");
		List<IIncomingMessage> msgs = repository.findByStatusIn(
				Arrays.<ProcessStatus> asList(ProcessStatus.NEW_MESSAGE, ProcessStatus.REPROCESS_MESSAGE));
		
		for(IIncomingMessage msg : msgs) {
			process(msg);
		}
	}
	
	@Scheduled(fixedDelay=60000)
	synchronized void checkAndProcessOutboundMessage(){
		logger.info("Check and process outbound message.");
		List<IOutgoingMessage> messages=outgoingMessageRepository.findBySendStatus(SendStatus.NEW_MESSAGE);
		if(!messages.isEmpty()){
			for(IOutgoingMessage message:messages){
				process(message);
			}
		}
	}

	@Override
	public void setApplicationEventPublisher(
			ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher=applicationEventPublisher;
		
	}
	
	public ApplicationEventPublisher getApplicationEventPublisher() {
		return applicationEventPublisher;
	}

}
