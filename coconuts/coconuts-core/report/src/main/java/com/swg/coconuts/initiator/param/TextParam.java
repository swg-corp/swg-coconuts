package com.swg.coconuts.initiator.param;

/**
 * Class representasi dari sebuah cell yang berisi tipe data {@link String}
 * @author satriaprayoga
 *
 */
public class TextParam implements CellParam<String>{

	private String value;
	private boolean proceed=false;
	
	public TextParam() {
	}
	
	
	
	public TextParam(String value) {
		super();
		this.value = value;
	}

	@Override
	public void setValue(String value) {
		this.value=value;
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	public void setProceed(boolean proceed) {
		this.proceed=proceed;
	}

	@Override
	public boolean isProceed() {
		return proceed;
	}

}
