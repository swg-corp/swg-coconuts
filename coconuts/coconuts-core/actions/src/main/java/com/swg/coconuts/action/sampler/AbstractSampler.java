/**
 * 
 */
package com.swg.coconuts.action.sampler;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.swg.coconuts.action.format.Format;
import com.swg.coconuts.action.format.SimpleFormat;

/**
 * @author satriaprayoga
 *
 */
public abstract class AbstractSampler implements Sampler{

	protected final Logger logger=Logger.getLogger(getClass());
	
	protected static final String START_TAG="{";
	protected static final String END_TAG="}";
	
	protected String keywordPart;
	protected final Map<String, SampleInfo> sampleInfoMap;;
	
	public AbstractSampler() {
		this.sampleInfoMap=new HashMap<String, SampleInfo>();
	}
	
	@Override
	public Format createFromSample(String rawValue) {
		Format format=new SimpleFormat(getRawFormat(rawValue));
		return format;
	}
	
	protected abstract String getRawFormat(String payload);
	
	@Override
	public String getKeywordPart() {
		return keywordPart;
	}
	
	@Override
	public Map<String, SampleInfo> getSampleInfo() {
		return sampleInfoMap;
	}
	
	@Override
	public Set<String> getParametersValue(){
		return new HashSet<String>(sampleInfoMap.keySet());
	}
	
	@Override
	public String getParameterValueAt(int position){
		logger.debug("retrieve parameter value at: "+position);
		String param=null;
		for(SampleInfo info:sampleInfoMap.values()){
			if(info.getPosition()==position){
				param=info.getValue();
				logger.debug("result looping:"+param);
			}
		}
		return param;
	}
	
	@Override
	public String getParameterStringFor(String paramname) {
		String value=null;
		for(String s:sampleInfoMap.keySet()){
			if(s.equalsIgnoreCase(paramname)){
				value=sampleInfoMap.get(s).getValue();
			}
		}
		if(value == null) {
			throw new IllegalArgumentException("Valu with name '"+paramname+"' not found.");
		}
		return value;
	}
	
	
}
