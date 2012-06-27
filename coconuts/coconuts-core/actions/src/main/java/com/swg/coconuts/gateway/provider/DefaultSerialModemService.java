package com.swg.coconuts.gateway.provider;

import java.io.IOException;

import org.smslib.AGateway;
import org.smslib.GatewayException;
import org.smslib.ICallNotification;
import org.smslib.IGatewayStatusNotification;
import org.smslib.IInboundMessageNotification;
import org.smslib.IOrphanedMessageNotification;
import org.smslib.IOutboundMessageNotification;
import org.smslib.OutboundMessage;
import org.smslib.SMSLibException;
import org.smslib.Service;
import org.smslib.TimeoutException;


public class DefaultSerialModemService implements ISerialModemGatewayService{

	private boolean active=false;

	@Override
	public void start() {
		active=true;
		try {
			Service.getInstance().startService();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GatewayException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SMSLibException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void stop() {
		try {
			Service.getInstance().stopService();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GatewayException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SMSLibException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		active=false;

	}
	
	
	@Override
	public void addGateway(AGateway modemGateway) {
		try {
			Service.getInstance().addGateway(modemGateway);
		} catch (GatewayException e) {
			e.printStackTrace();
		}
	}

	@Override
	public AGateway getGateway(String gatewayId) {
		return Service.getInstance().findGateway(gatewayId);
	}

	@Override
	public void addInboundNotification(IInboundMessageNotification notification) {
		Service.getInstance().setInboundMessageNotification(notification);
	}

	@Override
	public void addOutboundNotification(
			IOutboundMessageNotification notification) {
		Service.getInstance().setOutboundMessageNotification(notification);
	}

	@Override
	public void addCallNotification(ICallNotification callNotification) {
		Service.getInstance().setCallNotification(callNotification);
	}

	@Override
	public void addGatewayStatusNotification(
			IGatewayStatusNotification notification) {
		Service.getInstance().setGatewayStatusNotification(notification);
	}

	@Override
	public void addOrphanNotification(IOrphanedMessageNotification notification) {
		Service.getInstance().setOrphanedMessageNotification(notification);
	}

	
	public boolean isActive() {
		return active;
	}

	@Override
	public void removeGateway(AGateway aGateway) {
		try {
			Service.getInstance().removeGateway(aGateway);
		} catch (GatewayException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void sendMessage(OutboundMessage message) {
		try {
			Service.getInstance().sendMessage(message);
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GatewayException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void queueMessage(OutboundMessage message) {
		Service.getInstance().queueMessage(message);
	}

}
