package com.swg.coconuts.gateway.provider;

import org.apache.log4j.Logger;
import org.smslib.AGateway.Protocols;
import org.smslib.modem.SerialModemGateway;


/**
 * Impelentasi sederhana untuk provider {@link org.smslib.modem.SerialModemGateway}
 * @author satriaprayoga
 *
 */
public class DefaultGatewayProvider implements IGatewayProvider.SerialModemGatewayProvider {

	private static final Logger LOGGER=Logger.getLogger(DefaultGatewayProvider.class);
	
	@Override
	public SerialModemGateway getSmslibGateway(IModemGateway gateway) {
		SerialModemGateway modemGateway=new SerialModemGateway(gateway.getGatewayId(), gateway.getPort(), 
				gateway.getBaudRate(), gateway.getManufacturer(), gateway.getModel());
		modemGateway.setInbound(true);
		modemGateway.setOutbound(true);
		modemGateway.setSimPin(gateway.getSimPin());
		modemGateway.setSmscNumber(gateway.getSmscNumber());
		modemGateway.setProtocol(Protocols.PDU);
		LOGGER.debug("creating serialModem: "+modemGateway.getSmscNumber());
		return modemGateway;
	}

}
