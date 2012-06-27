package com.swg.coconuts.gateway.provider;

import org.smslib.AGateway;
import org.smslib.modem.SerialModemGateway;


/**
 * Interface dasar untuk bikin Object Gateway sebenarnya, yaitu class AGateway dan turunannya dari SMSlib
 * @author satriaprayoga
 *
 * @param <T>
 * @param <E>
 */
public interface IGatewayProvider<T extends AGateway,E extends IGateway> {

	/**
	 * Method untuk bikin gatewaynya, parameter gateway diset dari parameter {@link com.swg.coconuts.gateway.provider.IGateway}
	 * @param gateway
	 * @return
	 */
	public T getSmslibGateway(E gateway);
	
	/**
	 * Interface untuk bikin Object Gateway tipe {@link org.smslib.modem.SerialModemGateway}
	 * @author satriaprayoga
	 *
	 */
	public interface SerialModemGatewayProvider extends IGatewayProvider<SerialModemGateway,IModemGateway> {

	}
	
}
