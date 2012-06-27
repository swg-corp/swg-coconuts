package com.swg.coconuts.action.mock;

import java.util.Map;

import com.swg.coconuts.action.AbstractAction;
import com.swg.coconuts.action.ActionException;
import com.swg.coconuts.action.Keyword;
import com.swg.coconuts.action.PayloadType;
import com.swg.coconuts.action.SimpleKeyword;
import com.swg.coconuts.action.format.Format;
import com.swg.coconuts.action.format.SimpleFormat;
import com.swg.coconuts.action.param.NumberParameter;
import com.swg.coconuts.action.param.Parameter;
import com.swg.coconuts.action.param.StringParameter;

public class SimpleAction extends AbstractAction {
	
	//private static String sample="lapor tps 2 banyak yang curang kaka";
	
	private static final Keyword KEYWORD=new SimpleKeyword("lapor");
	private static final Format FORMAT=new SimpleFormat("{keyword}{parameter:string:value1}{parameter:number:value2}",PayloadType.MIX_STRING);
	

	public SimpleAction() {
		super(KEYWORD,FORMAT);
	}

	@Override
	protected void configureParameterTypeMap(Map<String, Class<? extends Parameter<?>>> parametersTypeMap2) {
		parametersTypeMap2.put("value1", StringParameter.class);
		parametersTypeMap2.put("value2", NumberParameter.class);
	}

	@Override
	public void execute() throws ActionException {
		logger.info("######## execution of SimpleAction #######");
		logger.info("sent by user: "+getParameter("value1"));
		logger.info("sent by user: "+getParameter("value2").getValue());
	}

}
