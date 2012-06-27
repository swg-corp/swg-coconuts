package com.swg.coconuts.action.mock;

import java.util.Map;

import com.swg.coconuts.action.AbstractAction;
import com.swg.coconuts.action.ActionException;
import com.swg.coconuts.action.Keyword;
import com.swg.coconuts.action.PayloadType;
import com.swg.coconuts.action.SimpleKeyword;
import com.swg.coconuts.action.format.Format;
import com.swg.coconuts.action.format.SimpleFormat;
import com.swg.coconuts.action.param.MapParameter;
import com.swg.coconuts.action.param.Parameter;

public class MapAction extends AbstractAction{

	private static final Keyword KEYWORD=new SimpleKeyword("suara");
	private static final Format FORMAT=new SimpleFormat("{keyword}{parameter:map:suaramap}", PayloadType.MAPPED);
	
	public MapAction() {
		super(KEYWORD, FORMAT);
	}
	
	@Override
	protected void configureParameterTypeMap(
			Map<String, Class<? extends Parameter<?>>> parametersTypeMap2) {
		parametersTypeMap2.put("suaramap", MapParameter.class);
	}

	@Override
	public void execute() throws ActionException {
		logger.info("############# execution of MapAction ############");
		MapParameter parameter=(MapParameter) getParameter("suaramap");
		logger.info("yang dikirim user:");
		Map<String, Parameter<?>> map=parameter.getValue();
		for(Parameter<?> parameter2:map.values()){
			logger.info("yang dikirim: "+parameter2.getName()+", "+parameter2.getValue());
		}
		logger.info("############# end execution of MapAction ############");
		
	}

}
