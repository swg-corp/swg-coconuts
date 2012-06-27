package com.swg.coconuts.action.format.model;

import java.util.Set;

import com.swg.coconuts.action.param.Parameter;
import com.swg.coconuts.action.param.ParameterInfo;

public interface FormatModel {

	String getFormatString();
	
	/**
	 * Retrieve position dari keyword.
	 * Index ini ditentukan setelah format di split menjadi beberapa bagian
	 * (token format) dan displit berdasarkan delimiter.
	 * 
	 * @return Integer
	 */
	Integer getKeywordPosition();
	
	/**
	 * Count seluruh parameter yang didefinisikan dalam format.
	 * 
	 * @return
	 */
	Integer countParameters();
	/**
	 * Retrieve parameter name yang terdefinisi dalam format.
	 * 
	 * @return
	 */
	Set<String> getParametersName();
	String getParameterNameAt(Integer position);
	Integer getParameterPosition(String name);
	void addParameterInfo(ParameterInfo parameterInfo);
	Class<? extends Parameter<?>> getParameterTypeAt(Integer position);
	Class<? extends Parameter<?>> getParameterTypeFor(String name);
	Object getParameterValueAt(Integer position);
	Object getParameterValueFor(String name);
}
