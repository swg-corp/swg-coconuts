package com.swg.coconuts.gateway.provider;

import java.io.Serializable;

/**
 * Interface dasar untuk message gateway
 * @author satriaprayoga
 *
 */
public interface IGateway extends Serializable{

	public String getGatewayId();
	
	public void setGatewayId(String gatewayId);
	
	public void setStatus(String status);
	
	public String getStatus();
	
	
}
