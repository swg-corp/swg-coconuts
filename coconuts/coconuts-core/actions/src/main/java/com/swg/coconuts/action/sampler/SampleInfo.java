package com.swg.coconuts.action.sampler;

/**
 * 
 * @author satriaprayoga
 *
 */
public final class SampleInfo {
	private final String value;
	private final String name;
	private Integer position;
	
	public SampleInfo(String name,String value,Integer position){
		this.value=value;
		this.name=name;
		this.position=position;
	}
	
	public String getName() {
		return name;
	}
	
	public Integer getPosition() {
		return position;
	}
	
	public String getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		return "Sample Info[name= "+name+", value= "+value+", position= "+position+"]";
	}
}
