package com.swg.coconuts.gateway.provider;

import com.swg.coconuts.gateway.IOutgoingMessage;

/**
 * Interface utama yang menyediakan akses service yang berhubungan dengan gateway pesan
 * @author satriaprayoga
 *
 */
public interface IGatewayServiceProvider extends IModemGatewayRegistrar{

	public enum ServiceStatus{
		Start,
		Stop,
		Error
		
	}
	public void startService();
	public void stopService();
	public String getCommand();
	public String getStatus();
	public void sendMessage(IOutgoingMessage message);
	public void queue(IOutgoingMessage message);
	public ServiceStatus getServiceStatus();
	public IGatewayService getGatewayService();
}
