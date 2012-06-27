package com.swg.coconuts.messaging.processor;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.swg.coconuts.action.Action;
import com.swg.coconuts.action.format.Format;
import com.swg.coconuts.action.format.Parser;
import com.swg.coconuts.action.format.ParsingException;
import com.swg.coconuts.action.format.SimpleParser;
import com.swg.coconuts.action.param.Parameter;
import com.swg.coconuts.gateway.IIncomingMessage;
import com.swg.coconuts.gateway.processor.MessageProcessingCallback;
import com.swg.coconuts.gateway.processor.MessageProcessingException;
import com.swg.coconuts.gateway.processor.MessageProcessor;
import com.swg.coconuts.gateway.processor.ProcessFailureInfo;

@Component
public class MessageProcessorImpl implements MessageProcessor {

	protected final Logger logger = Logger.getLogger(getClass());
	
	@Autowired(required=true)
	protected ActionStackFactory actionStackFactory;
	
	ValidationProcessorCallback processorCallback;
	
	private Parser parser;
	
	public MessageProcessorImpl() {
		this.parser=new SimpleParser();
	}
	
	@Autowired
	@Qualifier("validationProcessorCallback")
	public void setProcessorCallback(MessageProcessingCallback callback) {
		this.processorCallback=(ValidationProcessorCallback)callback;
	}

	@Override
	public void processMessage(IIncomingMessage incomingMessage)throws MessageProcessingException {
		try {
			logger.info("Eksekusi pre process callback.");
			processorCallback.onPreProcess(incomingMessage);
			
			doProcess(incomingMessage);
			
			logger.info("Eksekusi post processor callback.");
			processorCallback.onPostProcess(incomingMessage);
		}
		catch(MessageProcessingException e) {
			logger.error("Exception (" + e + ") dilempar pada saat pemrosesan pesan. Panggil failure callback.");
			processorCallback.onProcessFailure(
				new ProcessFailureInfo(incomingMessage, e.getCode(), e.getFailedStep(), e.getCause()));
			
			logger.info("Lempar kembali processing exception, biar invoker tau ada exception.");
			throw e;
		}

	}
	
	@SuppressWarnings("unchecked")
	protected void doProcess(IIncomingMessage incomingMessage){
		logger.info("Process pesan masuk.");
		
		ActionStack actionStack = actionStackFactory.create();
		boolean executed = false;
		while(!actionStack.empty()) {
			Action action = actionStack.pop();
			Format format = action.getFormatReceived();
			
			try {
				logger.info("Try to parse message.");
				Parser.Result result = parser.parse(incomingMessage.getContent(), format);
				
				action.setParameters((Collection<Parameter<?>>) result.getParameters());
				action.execute();
				executed = true;
			}
			catch(ParsingException parsingException) {
				// Disini message-processing-exception tidak dilempar, biar action berikutnya bisa dieksekusi.
				logger.error("Action ("+action+") tidak dapat memparsing content sms. Coba dengan action yang lain.", parsingException);
			}
		}
		if(!executed) {
			logger.error("Format pesan masuk invalid, tidak ada action yang sesuai untuk dieksekusi.");
			throw new MessageProcessingException("Format pesan masuk invalid, tidak ada action yang sesuai untuk dieksekusi.", 1);
		}
	}

}
