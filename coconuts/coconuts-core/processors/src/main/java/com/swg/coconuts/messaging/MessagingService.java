package com.swg.coconuts.messaging;

import com.swg.coconuts.gateway.IIncomingMessage;
import com.swg.coconuts.gateway.IOutgoingMessage;

public interface MessagingService {

	void process(IIncomingMessage incomingMessage);
	void reprocess(IIncomingMessage incomingMessage);
	void process(IOutgoingMessage outgoingMessage);
}
