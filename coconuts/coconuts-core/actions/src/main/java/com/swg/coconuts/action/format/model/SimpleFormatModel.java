/**
 * 
 */
package com.swg.coconuts.action.format.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.swg.coconuts.action.param.Parameter;
import com.swg.coconuts.action.param.ParameterInfo;

/**
 * @author satriaprayoga
 *
 */
public class SimpleFormatModel implements FormatModel,Serializable{
	private static final long serialVersionUID = 1287108971878501487L;

	protected final Logger logger=Logger.getLogger(getClass());

	private String formatString;
	private Map<String, ParameterInfo> parameterInfoMap = new HashMap<String, ParameterInfo>();

	public SimpleFormatModel() {

		this.parameterInfoMap=new HashMap<String, ParameterInfo>();
	}

	@Override
	public Integer getKeywordPosition() {
		return 1;
	}

	@Override
	public Integer countParameters() {
		return parameterInfoMap.keySet().size();
	}

	@Override
	public String getFormatString() {
		return formatString;
	}

	@Override
	public Set<String> getParametersName() {
		return parameterInfoMap.keySet();
	}

	@Override
	public String getParameterNameAt(Integer position) {
		logger.debug("Retrieve parameter name at position : " + position);

		// Tambah satu, yaitu posisi keyword.
		if(parameterInfoMap.keySet().size()+1 < position) {
			throw new IndexOutOfBoundsException("Posisi yang diminta lebih besar dari jumlah parameter yang ada.");
		}

		String parameterName = null;
		for(ParameterInfo parameterInfo : parameterInfoMap.values()) {
			logger.debug("Parameter in loop : " + parameterInfo);
			if(parameterInfo.getPosition() == position) {
				parameterName = parameterInfo.getName();
			}
		}
		return parameterName;
	}

	@Override
	public Integer getParameterPosition(String name) {
		Integer position = 0;
		for(String parameterName : parameterInfoMap.keySet()) {
			if(parameterName.equalsIgnoreCase(name)) {
				position = parameterInfoMap.get(parameterName).getPosition();
			}
		}
		if(position == 0) {
			throw new IllegalArgumentException("Parameter with name '"+name+"' not found.");
		}
		return position;
	}

	@Override
	public Class<? extends Parameter<?>> getParameterTypeAt(Integer position) {
		// Tambah satu, yaitu posisi keyword.
		if(parameterInfoMap.keySet().size()+1 < position) {
			throw new IndexOutOfBoundsException("Posisi yang diminta lebih besar dari jumlah parameter yang ada.");
		}

		logger.debug("Retrieve parameter type at position : " + position);
		Class<? extends Parameter<?>> parameterType = null;
		for(ParameterInfo parameterInfo : parameterInfoMap.values()) {
			logger.debug("Item in loop : " + parameterInfo);
			if(parameterInfo.getPosition() == position) {
				parameterType = parameterInfo.getType();
			}
		}
		return parameterType;
	}

	@Override
	public Class<? extends Parameter<?>> getParameterTypeFor(String name) {
		Class<? extends Parameter<?>> type = null;
		for(String parameterName : parameterInfoMap.keySet()) {
			if(parameterName.equalsIgnoreCase(name)) {
				type = parameterInfoMap.get(parameterName).getType();
			}
		}
		if(type == null) {
			throw new IllegalArgumentException("Parameter with name '"+name+"' not found.");
		}
		return type;
	}

	@Override
	public void addParameterInfo(ParameterInfo parameterInfo){
		this.parameterInfoMap.put(parameterInfo.getName(), parameterInfo);
	}

	@Override
	public Object getParameterValueAt(Integer position) {
		// Tambah satu, yaitu posisi keyword.
		if(parameterInfoMap.keySet().size()+1 < position) {
			throw new IndexOutOfBoundsException("Posisi yang diminta lebih besar dari jumlah parameter yang ada.");
		}

		logger.debug("Retrieve parameter value at position : " + position);
		Object value=null;
		for(ParameterInfo parameterInfo : parameterInfoMap.values()) {
			logger.debug("Parameter in loop : " + parameterInfo);
			if(parameterInfo.getPosition() == position) {
				value = parameterInfo.getValue();
			}
		}
		return value;
	}
	
	@Override
	public Object getParameterValueFor(String name) {
		Object value = null;
		for(String parameterName : parameterInfoMap.keySet()) {
			if(parameterName.equalsIgnoreCase(name)) {
				value = parameterInfoMap.get(parameterName).getValue();
			}
		}
		if(value == null) {
			throw new IllegalArgumentException("Valu with name '"+name+"' not found.");
		}
		return value;
	}

}
