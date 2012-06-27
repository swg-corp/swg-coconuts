package com.swg.coconuts.action.param;

public class NumberParameter implements Parameter<Number>{

	private String name;
	private Number value;
	
	public NumberParameter(String name, Number value) {
		this.name = name;
		this.value = value;
	}
	
	@Override
	public String getName() {
		return name;
	}
	@Override
	public Class<Number> getType() {
		return Number.class;
	}
	@Override
	public Number getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "NumberParameter [name=" + name + ", value=" + value + "]";
	}
	
	
}
