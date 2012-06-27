package com.swg.coconuts.initiator.xls;

import java.io.Serializable;

public class Column implements Serializable{
	private static final long serialVersionUID = -7365204409836659767L;
	
	private String name;
	private Integer number;
	
	public Column() {
	}

	public Column(String name, Integer number) {
		super();
		this.name = name;
		this.number = number;
	}



	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	} 
	
	public void setNumber(Integer number) {
		this.number = number;
	}
	
	public Integer getNumber() {
		return number;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Column [name=");
		builder.append(name);
		builder.append(", number=");
		builder.append(number);
		builder.append("]");
		return builder.toString();
	}
	
	

}
