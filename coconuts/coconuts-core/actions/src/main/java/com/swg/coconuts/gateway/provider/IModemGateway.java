package com.swg.coconuts.gateway.provider;

/**
 * Interface dasar untuk gateway jenis serial modem
 * @author satriaprayoga
 *
 */
public interface IModemGateway extends IGateway{

	/**
	 * property port untuk serial modem yang tersambung
	 * . Pada OS windows biasanya di COM4,COM5, atau COM lainnya
	 * . Pada unix base ada di /dev/tty* atau di /dev/ttyUSB*
	 * @return
	 */
	public String getPort();
	public void setPort(String port);
	
	/**
	 * baudrate device modem
	 * @return
	 */
	public Integer getBaudRate();
	public void setBaudRate(Integer baudRate);
	
	/**
	 * No SIM kartu GSM yang dipasang di modem
	 * @param simPin
	 */
	public void setSimPin(String simPin);
	public String getSimPin();
	
	/**
	 * No SMS Center operator seluler SIM
	 * @param smscNumber
	 */
	public void setSmscNumber(String smscNumber);
	public String getSmscNumber();
	
	public String getManufacturer();
	
	public void setManufacturer(String manufacturer);
	
	public String getModel();
	
	public void setModel(String model);
}
