package com.swg.coconuts.gateway.provider;


/**
 * Interface dasar untuk registrasi Gateway pada system 
 * @author satriaprayoga
 *
 */
public interface IGatewayRegistrar<T extends IGateway> {

	/**
	 * Register gateway pada system
	 * @param gateway
	 */
	public void registerGateway(T gateway);
	
	/**
	 * Unregister gateway dari system
	 * @param gateway
	 */
	public void unregisterGateway(T gateway);
	
	/**
	 * method pengecekan apakah gateway telah diregister pada system
	 * @param gateway
	 * @return (true:gateway sudah teregister, false sebaliknya..)
	 */
	public boolean isRegistered(T gateway);
}
