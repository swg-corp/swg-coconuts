package com.swg.coconuts.gateway.processor;

import com.swg.coconuts.gateway.IIncomingMessage;
import com.swg.coconuts.gateway.processor.MessageProcessingCallback.HasMessageProcessingCallback;

public interface MessageProcessor extends HasMessageProcessingCallback {
	void processMessage(IIncomingMessage inboundMessage) throws MessageProcessingException;
}
