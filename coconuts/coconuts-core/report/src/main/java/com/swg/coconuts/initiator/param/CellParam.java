package com.swg.coconuts.initiator.param;

import java.io.Serializable;

/**
 * Interface ini merepresentasikan isi dari sebuah cell
 * @author satriaprayoga
 *
 * @param <T>
 */
public interface CellParam<T extends Serializable> {

	public void setValue(T value);
	public T getValue();
	
	void setProceed(boolean proceed);
	boolean isProceed();
	
}
