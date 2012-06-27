package com.swg.coconuts.messaging.processor;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.swg.coconuts.action.AbstractAction;
import com.swg.coconuts.action.ActionException;
import com.swg.coconuts.action.Keyword;
import com.swg.coconuts.action.PayloadType;
import com.swg.coconuts.action.SimpleKeyword;
import com.swg.coconuts.action.format.Format;
import com.swg.coconuts.action.format.SimpleFormat;
import com.swg.coconuts.action.param.Parameter;
import com.swg.coconuts.action.param.StringParameter;

@Component
public class ReportAction extends AbstractAction{
	
	private static final Keyword KEYWORD=new SimpleKeyword("lapor");
	private static final Format FORMAT=new SimpleFormat("{keyword}{parameter:string:value1}",PayloadType.SIMPLE_STRING);

	public ReportAction() {
		super(KEYWORD,FORMAT);
	}
	
	@Override
	protected void configureParameterTypeMap(Map<String, Class<? extends Parameter<?>>> parametersTypeMap2) {
		parametersTypeMap2.put("value1", StringParameter.class);
	
	}

	@Override
	public void execute() throws ActionException {
		logger.info("######## execution of SimpleAction #######");
		if(getParameter("value1")!=null){
			logger.info("sent by user: "+getParameter("value1"));
		}
		
	
	}


}
