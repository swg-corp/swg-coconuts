package com.swg.coconuts.messaging.gateway;

import org.apache.log4j.Logger;
import org.smslib.AGateway;
import org.smslib.AGateway.GatewayStatuses;
import org.smslib.IGatewayStatusNotification;
import org.springframework.stereotype.Component;

@Component
public class GatewayStatusNotification implements IGatewayStatusNotification {

	private static final Logger logger=Logger.getLogger(GatewayStatusNotification.class);
	
	@Override
	public void process(AGateway gateway, GatewayStatuses oldStatus,GatewayStatuses newStatus) {
		logger.info("gateway status change: "+oldStatus.name()+" to "+newStatus.name());
	}

}
