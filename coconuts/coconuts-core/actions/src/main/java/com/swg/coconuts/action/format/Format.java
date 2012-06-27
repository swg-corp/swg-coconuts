package com.swg.coconuts.action.format;

import java.io.Serializable;

import com.swg.coconuts.action.PayloadType;
import com.swg.coconuts.action.format.model.FormatModel;

/**
 * Simple kelas dari object yang nampung format pesan masuk yang bisa dihandle
 * untuk mengexecute action tertentu.
 * 
 * @author zakyalvan
 */
public abstract class Format implements Serializable {
	
	
	private static final long serialVersionUID = -1107375968806033065L;
	
	public static final String DEFAULT_START_TAG = "{";
	public static final String DEFAULT_END_TAG = "}";
	
	protected final String value;
	protected final FormatModel model;
	private PayloadType payloadType;
	
	public Format(String value) {
		if(value == null || value.trim().length() ==0)
			throw new IllegalArgumentException("Parameter format value should not be null or zero length string.");
		
		this.value = value;
		this.model = createModel(value);
	}
	
	public Format(String value,PayloadType payloadType) {
		this(value);
		this.payloadType=payloadType;
	}
	
	protected abstract FormatModel createModel(String value);

	public String getValue() {
		return value;
	}
	public final FormatModel getModel() {
		return model;
	}
	
	public String toString() {
		return value;
	}
	
	public PayloadType getPayloadType() {
		return payloadType;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		Format other = (Format) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equalsIgnoreCase(other.value))
			return false;
		return true;
	}
}
