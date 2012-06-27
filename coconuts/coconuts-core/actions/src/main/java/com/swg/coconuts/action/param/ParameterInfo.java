package com.swg.coconuts.action.param;

public final class ParameterInfo {
	
	private String name;
	private Class<? extends Parameter<?>> type;
	private Integer position;
	private Object value;
	
	public ParameterInfo(String name, Class<? extends Parameter<?>> type, Integer position) {
		this.name = name;
		this.type = type;
		this.position = position;
	}
	
	public ParameterInfo(String name, Class<? extends Parameter<?>> type, Integer position,Object value) {
		this(name, type, position);
		this.value=value;
	}
	
	public String getName() {
		return name;
	}
	public Class<? extends Parameter<?>> getType() {
		return type;
	}
	public Integer getPosition() {
		return position;
	}
	
	public Object getValue() {
		return value;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.toLowerCase().hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ParameterInfo other = (ParameterInfo) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.toLowerCase().equalsIgnoreCase(other.name.toLowerCase()))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "ParameterInfo [name=" + name + ", type=" + type
				+ ", position=" + position + "]";
	}

}
