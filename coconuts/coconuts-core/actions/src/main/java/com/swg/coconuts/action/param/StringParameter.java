package com.swg.coconuts.action.param;

/**
 * Simple dto yang nyimpan nilai parameter hasil parsing sms.
 * Parameter ini digunakan dalam eksekusi action.
 * 
 * @author zakyalvan
 */
public class StringParameter implements Parameter<String> {
	private String name;
	private String value;
	
	public StringParameter(String name, String value) {
		this.name = name;
		this.value = value;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public Class<String> getType() {
		return String.class;
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "StringParameter [name=" + name + ", value=" + value + "]";
	}
}
