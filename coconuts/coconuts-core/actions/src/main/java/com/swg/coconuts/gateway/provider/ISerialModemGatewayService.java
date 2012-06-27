package com.swg.coconuts.gateway.provider;

import org.smslib.AGateway;
import org.smslib.ICallNotification;
import org.smslib.IGatewayStatusNotification;
import org.smslib.IInboundMessageNotification;
import org.smslib.IOrphanedMessageNotification;
import org.smslib.IOutboundMessageNotification;
import org.smslib.OutboundMessage;

/**
 * Interface untuk aktivasi Gateway tipe SerialModem. 
 * Fungsi utama Interface ini sebagai proxy dari {@link org.smslib.Service}
 * @author satriaprayoga
 *
 */
public interface ISerialModemGatewayService extends IGatewayService{

	public void addGateway(AGateway modemGateway);
	
	public AGateway getGateway(String gatewayId);
	
	public void removeGateway(AGateway aGateway);
	
	public void addInboundNotification(IInboundMessageNotification notification);
	
	public void addOutboundNotification(IOutboundMessageNotification notification);
	
	public void addCallNotification(ICallNotification callNotification);
	
	public void addGatewayStatusNotification(IGatewayStatusNotification notification);
	
	public void addOrphanNotification(IOrphanedMessageNotification notification);
	
	public void sendMessage(OutboundMessage message);
	
	public void queueMessage(OutboundMessage message);
	
}
