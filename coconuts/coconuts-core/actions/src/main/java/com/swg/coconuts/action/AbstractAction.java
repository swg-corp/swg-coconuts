package com.swg.coconuts.action;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.swg.coconuts.action.format.Format;
import com.swg.coconuts.action.param.Parameter;

/**
 * Ini base class untuk action.
 * Sebagai catatan untuk base kelas ini, setiap perubahan pada format, harus 
 * dicheck kesesesuaiannya dengan parameter yang diperlukan.
 * 
 * @author zakyalvan
 */
public abstract class AbstractAction implements Action {
	protected Logger logger = Logger.getLogger(getClass());
	
	protected boolean validationEnabled;
	
	protected final Keyword keyword;
	protected final Format format;
	
	protected final Set<Parameter<?>> parameters = new HashSet<Parameter<?>>();
	
	/**
	 * Map antara nama parameter yang diperlukan dengan jenis dari parameter tersebut.
	 */
	private Map<String, Class<? extends Parameter<?>>> parametersTypeMap = 
		new HashMap<String, Class<? extends Parameter<?>>>();
	
	protected boolean responseEnabled = true;
	private boolean executed=false;
	private PayloadType payloadType;

	public AbstractAction(Keyword keyword, Format format) {
		if(keyword == null || format == null)
			throw new IllegalArgumentException("Parameter format dan/atau keyword tidak boleh null.");
		
		logger.debug("Call method to configure parameters type mapping.");
		configureParameterTypeMap(parametersTypeMap);
		
		logger.debug("Call method to validate format supplied.");
		validateFormat(format);
		
		this.keyword = keyword;
		this.format = format;
		this.payloadType=PayloadType.MIX_STRING;
	}
	
	public AbstractAction(Keyword keyword,Format format,PayloadType payloadType) {
		this(keyword,format);
		this.payloadType=payloadType;
	}
	
	/**
	 * Configure parameter type mapping, mapping antara nama parameter dan typenya.
	 * 
	 * @param parametersTypeMap2
	 */
	protected abstract void configureParameterTypeMap(Map<String, Class<? extends Parameter<?>>> parametersTypeMap2);
	
	/**
	 * Validasi format pesan yang dibuat, apakah sesuai dengan parameter yang diperlukan
	 * oleh action ini atau tidak.
	 * 
	 * @param {@link Format} format
	 */
	protected final void validateFormat(Format format) {
		Set<String> defined=parametersTypeMap.keySet();
		if(defined.size()!=format.getModel().countParameters()){
			logger.error("defined format parameters size: "+defined.size()+" not match with the required parameters: "+format.getModel().countParameters());
			throw new IllegalArgumentException("defined format parameters size: "+defined.size()+" not match with the required parameters: "+format.getModel().countParameters());
		}
		Set<String> noMatched=new HashSet<String>();
		for(String s:defined){
			logger.debug("try matching: "+s);
			Class<? extends Parameter<?>> matched=format.getModel().getParameterTypeFor(s);
			if(matched==null){
				logger.error("no parameters matched for: "+s);
				noMatched.add(s);
				throw new IllegalArgumentException("no parameters matched for: "+s);
			}
			logger.debug("parameter with name: "+s+" found");
		}
		if(noMatched.size()>0){
			logger.error("there are "+noMatched.size()+" not matched parameters");
			throw new IllegalArgumentException("there are "+noMatched.size()+" not matched parameters");
		}
	}
	
	public void setValidationEnabled(boolean validationEnabled) {
		this.validationEnabled = validationEnabled;
	}
	public boolean isValidationEnabled() {
		return validationEnabled;
	}

	public void addValidator(Validator<?> validator) {
		
	}
	public void removeValidator(Validator<?> validator) {
		
	}

	public void validate() throws ActionValidationException {
		
	}

	/**
	 * Retrieve copy of parameters name yang diperlukan untuk mengeksekusi
	 * action ini. Di kasi kopian biar aman saja.
	 * 
	 * @return Set<String>
	 */
	public Set<String> getParametersName() {
		return new HashSet<String>(parametersTypeMap.keySet());
	}

	public Class<? extends Parameter<?>> getParameterType(String name) {
		if(parametersTypeMap.containsKey(name)) {
			return parametersTypeMap.get(name);
		}
		return null;
	}

	protected Parameter<?> getParameter(String name) {
		Parameter<?> parameter = null;
		// Di loop biar bisa ignore case.
		for(Parameter<?> p : parameters) {
			if(p.getName().equalsIgnoreCase(name)) {
				parameter = p;
			}
		}
		
		
		return parameter;
	}
	
	public void addParameter(Parameter<?> parameter) {
		this.parameters.add(parameter);
	}
	@Override
	public void setParameters(Collection<Parameter<?>> parameters) {
		this.parameters.clear();
		this.parameters.addAll(parameters);
	}

	public Keyword getKeyword() {
		return keyword;
	}
	public Format getFormatReceived() {
		return format;
	}

	public void setResponseEnabled(boolean responseEnabled) {
		this.responseEnabled = responseEnabled;
	}
	public boolean isResponseEnabled() {
		return responseEnabled;
	}

	public void setResponseEnabledFor(int code) {
		
	}

	public boolean isResponseEnabledFor(int code) {
		return false;
	}

	public abstract void execute() throws ActionException;
	
	public void setExecuted(boolean executed) {
		this.executed = executed;
	}
	
	@Override
	public boolean isExecuted() {
		return executed;
	}
	
	@Override
	public PayloadType getPayloadType() {
		return payloadType;
	}
}