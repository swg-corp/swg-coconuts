package com.swg.coconuts.messaging.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.smslib.AGateway.GatewayStatuses;

import com.swg.coconuts.gateway.provider.IModemGateway;

@Entity
public class SerialModem implements IModemGateway{
	private static final long serialVersionUID = 590557971106227794L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	private String gatewayId;
	private String status=GatewayStatuses.STOPPED.name();
	private String manufacturer;
	private String model;
	private String port;
	private Integer baudRate;
	private String simPin;
	private String smscNumber;
	
	public SerialModem() {
	}
	
	public SerialModem(String gatewayId, String manufacturer, String model,String port, Integer baudRate) {
		this.gatewayId = gatewayId;
		this.manufacturer = manufacturer;
		this.model = model;
		this.port = port;
		this.baudRate = baudRate;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getId() {
		return id;
	}

	

	public String getGatewayId() {
		return gatewayId;
	}
	public void setGatewayId(String gatewayId) {
		this.gatewayId = gatewayId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public Integer getBaudRate() {
		return baudRate;
	}
	public void setBaudRate(Integer baudRate) {
		this.baudRate = baudRate;
	}
	public String getSimPin() {
		return simPin;
	}
	public void setSimPin(String simPin) {
		this.simPin = simPin;
	}
	public String getSmscNumber() {
		return smscNumber;
	}
	public void setSmscNumber(String smscNumber) {
		this.smscNumber = smscNumber;
	}

}
