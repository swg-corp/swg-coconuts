/**
 * 
 */
package com.swg.coconuts.action.sampler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * @author satriaprayoga
 *
 */
public class MultiSampler extends AbstractSampler{

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
				logger.debug("Keyword: "+keywordPart);
				keywordFound=true;
			}
		}
		builder.append(START_TAG+"keyword"+END_TAG);
		String param=payload.replaceFirst(keywordPart,"");
		param=param.trim();
		logger.info("param residual: "+param);
		matcher.reset();
		checkPattern=Pattern.compile("[A-Z][a-zA-Z]*[a-zA-z]+([ '-][a-zA-Z]+)|([a-zA-z]+)?\\d+|([a-zA-z]+)\\s*",Pattern.CASE_INSENSITIVE);
		matcher=checkPattern.matcher(param);
		String valid=null;
		while(matcher.find()){
			valid=matcher.group();
			String paramName=null;
			if(StringUtils.isNumeric(valid)){
				int num=Integer.valueOf(valid);
				logger.info("numeric found: "+num);
				paramName="value"+paramCount;
				builder.append(START_TAG+"parameter:number:value"+(paramCount+1)+END_TAG);
				logger.info("param built: "+START_TAG+"parameter:number:value"+(paramCount+1)+END_TAG+" value= "+valid);
				
			}else if(StringUtils.isAlphanumericSpace(valid)){
				valid=valid.trim();
				builder.append(START_TAG+"parameter:string:value"+(paramCount+1)+END_TAG);
				logger.info("param built: "+START_TAG+"parameter:string:value"+(paramCount+1)+END_TAG+" value= "+valid);
				
			}else{
				valid=valid.trim();
				builder.append(START_TAG+"parameter:string:value"+(paramCount+1)+END_TAG);
				logger.info("param built: "+START_TAG+"parameter:string:value"+(paramCount+1)+END_TAG+" value= "+valid);
			}
		
			paramName="value"+(paramCount+1);
			SampleInfo info=new SampleInfo(paramName, valid,(paramCount+1));
			sampleInfoMap.put(info.getName(), info);
			paramCount++;
		}
		logger.info("parameter count from raw string: "+paramCount);
		logger.info(builder.toString());
		return builder.toString();
	}

}
