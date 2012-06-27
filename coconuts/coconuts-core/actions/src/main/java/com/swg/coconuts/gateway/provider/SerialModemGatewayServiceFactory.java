package com.swg.coconuts.gateway.provider;


/**
 * Factory class untuk bikin {@link com.swg.coconuts.gateway.provider.ISerialModemGatewayService}
 * @author satriaprayoga
 *
 */
public class SerialModemGatewayServiceFactory implements IGatewayServiceFactory {
	
	private static final ISerialModemGatewayService iSerialModemGatewayService=new DefaultSerialModemService();
	
	@Override
	public IGatewayService getGatewayService(){
		return iSerialModemGatewayService;
	}
	
	
}
