package com.swg.coconuts.action.param;

import java.util.Map;

public class MapParameter implements Parameter<Map<String, Parameter<?>>>{

	
	private String name;
	private Map<String, Parameter<?>> value;
	
	public MapParameter(String name, Map<String, Parameter<?>> value) {
		this.name = name;
		this.value = value;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Map<String, Parameter<?>> getValue() {
		return value;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Class<Map<String, Parameter<?>>> getType() {
		return (Class<Map<String, Parameter<?>>>) value.getClass();
	}

	@Override
	public String toString() {
		return "MapParameter [name=" + name + ", value=" + value + "]";
	}

	
}
