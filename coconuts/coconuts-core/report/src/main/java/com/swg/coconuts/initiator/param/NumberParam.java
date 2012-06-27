package com.swg.coconuts.initiator.param;

/**
 * Class representasi dari sebuah cell yang berisi tipe data {@link Number}
 * @author satriaprayoga
 *
 */
public class NumberParam implements CellParam<Number>{

	private Number value;
	private boolean proceed;
	
	public void setValue(Number value) {
		this.value = value;
	}
	
	public Number getValue() {
		return value;
	}
	
	public boolean isProceed() {
		return proceed;
	}
	
	public void setProceed(boolean proceed) {
		this.proceed = proceed;
	}
}
