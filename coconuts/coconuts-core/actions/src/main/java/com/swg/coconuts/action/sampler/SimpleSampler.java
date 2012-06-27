/**
 * 
 */
package com.swg.coconuts.action.sampler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author satriaprayoga
 *
 */
public class SimpleSampler extends AbstractSampler{

	@Override
	protected String getRawFormat(String payload) {
		String pattern="([a-z]+){5,8}";
		payload=payload.toLowerCase();
		Pattern checkPattern=Pattern.compile(pattern,Pattern.CASE_INSENSITIVE);
		Matcher matcher=checkPattern.matcher(payload);
		boolean keywordFound=false;
		int paramCount=0;
		StringBuilder builder=new StringBuilder();
		while(!keywordFound){
			if(matcher.find()){
				keywordPart=matcher.group();
				logger.info("Keyword: "+keywordPart);
				keywordFound=true;
			}
		}
		builder.append(START_TAG+"keyword"+END_TAG);
		String param=payload.replaceFirst(keywordPart,"");
		param=param.trim();
		logger.info("param residual: "+param);
		//matcher.reset();
		//checkPattern=Pattern.compile("[A-Z][a-zA-Z]*[a-zA-z]+([ '-][a-zA-Z]+)|([a-zA-z]+)?\\d+|([a-zA-z]+)\\s*",Pattern.CASE_INSENSITIVE);
		//matcher=checkPattern.matcher(param);
		String paramName="value"+(paramCount+1);
		SampleInfo info=new SampleInfo(paramName, param,(paramCount+1));
		sampleInfoMap.put(info.getName(), info);
		logger.info("param built: "+START_TAG+"parameter:string:value"+(paramCount+1)+END_TAG+" value= "+param);
		builder.append(START_TAG+"parameter:string:value"+(paramCount+1)+END_TAG);
		paramCount=paramCount+1;
		logger.info("map parameter count from raw string: "+paramCount);
		return builder.toString();
	}

}
