/**
 * 
 */
package com.swg.coconuts.action.format.model;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.swg.coconuts.action.param.MapParameter;
import com.swg.coconuts.action.param.NumberParameter;
import com.swg.coconuts.action.param.Parameter;
import com.swg.coconuts.action.param.ParameterInfo;
import com.swg.coconuts.action.param.StringParameter;

/**
 * @author satriaprayoga
 *
 */
public class DefaultFormatModelBuilder extends AbstractFormatModelBuilder {

	protected final Logger logger=Logger.getLogger(getClass());

	public static final Pattern VALID_FORMAT_PATTERN = 
			Pattern.compile(
					"(\\G\\s*?\\{\\s*?(([keyword]{6,8}?)|([parmet]{8,10}\\s*?:\\s*?(string|number|map){1}\\s*?:\\s*?(\\w*?){1}))\\s*?\\}\\s*?)\\1*?",
					Pattern.CASE_INSENSITIVE);
	
	private static FormatModelBuilder formatModelBuilder;
	
	DefaultFormatModelBuilder() {
		
	}
	
	public final static FormatModelBuilder builderInstance(){
		if(formatModelBuilder==null)
			formatModelBuilder=new DefaultFormatModelBuilder();
		return  formatModelBuilder;
	}
	
	/* (non-Javadoc)
	 * @see com.swg.coconuts.actions.format.model.FormatModelBuilder#decodeFromString(java.lang.String)
	 */
	@Override
	public FormatModel buildFormatModel(String format) {
		if(format == null || format.length() == 0) {
			logger.error("Format parameter should not be null or zero length string.");
			throw new IllegalArgumentException("Format parameter should not be null or zero length string.");
		}
		// Trim dahulu fomatnya.
		format = format.trim();
		int formatLength = format.length();
		
		Matcher validPatternMatcher = VALID_FORMAT_PATTERN.matcher(format);
		
		int lastEnd = 0;
		int parameterCount = 0;
		boolean keywordPartFound = false;
		Set<ParameterInfo> parameterInfos = new HashSet<ParameterInfo>();
		while(validPatternMatcher.find()) {
			lastEnd = validPatternMatcher.end();
			
			if(validPatternMatcher.group(3) != null) {
				logger.debug("Keyword part ("+validPatternMatcher.group(3)+") found start at index " + validPatternMatcher.start(3) + " and end at index " + validPatternMatcher.end(3));
				if(parameterCount > 0) {
					logger.error("Invalid format. Keyword part found after "+parameterCount+" of parameter(s) definition. Keyword part should defined in first part");
					throw new IllegalArgumentException(
							"Invalid format. Keyword part found after "+parameterCount+" of parameter(s) definition. Keyword part should defined in first part");
				}
				
				if(!keywordPartFound) {
					keywordPartFound = true;
				}
				else {
					logger.error("Invalid format. Keyword part defined more than one. Only one keyword part allowed in valid format.");
					throw new IllegalArgumentException(
							"Invalid format. Keyword part already found before. Only one keypword part allowed in valid format.");
				}
			}
			else if(validPatternMatcher.group(4) != null) {
				logger.debug("Parameter part #"+(parameterCount+1)+" ("+validPatternMatcher.group(4)+") found.");				
				String parameterType = validPatternMatcher.group(5);
				String parameterName = validPatternMatcher.group(6);
				
				Class<? extends Parameter<?>> type = null;
				if(parameterType.equalsIgnoreCase("string")) {
					type = StringParameter.class;
				}
				else if(parameterType.equalsIgnoreCase("number")) {
					type = NumberParameter.class;
				}
				else if(parameterType.equalsIgnoreCase("map")) {
					type =  MapParameter.class;
				}
				
				parameterCount++;
				
				// Parameter count ditambah satu karena pada posisi 1 ada keyword.
				ParameterInfo parameterInfo = new ParameterInfo(parameterName, type, parameterCount+1);
				if(!parameterInfos.add(parameterInfo)) {
					logger.error("Invalid format. Parameter with name " + parameterName + " found more than once.");
					throw new IllegalArgumentException("Invalid format. Parameter with name \"" + parameterName + "\" found more than once.");
				}
			}
		}

		if(lastEnd != formatLength) {
			logger.error("Invalid format. Terjadi kesalahan pada format yang didefinisikan " +
					"(Dimulai sekitar part \""+format.substring(lastEnd)+"\")");
			throw new IllegalArgumentException("Invalid format. Terjadi kesalahan pada format yang didefinisikan " +
					"(Dimulai sekitar part \""+format.substring(lastEnd)+"\")");
		}
		
		logger.debug(parameterInfos);
		
		SimpleFormatModel formatModel = new SimpleFormatModel();
		for(ParameterInfo parameterInfo : parameterInfos) {
			formatModel.addParameterInfo(parameterInfo);
		}
		return formatModel;
	}

}
