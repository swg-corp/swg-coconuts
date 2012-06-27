package com.swg.coconuts.action.format;

import com.swg.coconuts.action.PayloadType;
import com.swg.coconuts.action.format.model.DefaultFormatModelBuilder;
import com.swg.coconuts.action.format.model.FormatModel;
import com.swg.coconuts.action.format.model.FormatModelBuilder;

public class SimpleFormat extends Format {

	private static final long serialVersionUID = 2779901014335680973L;
	
	public SimpleFormat(String value) {
		super(value,PayloadType.SIMPLE_STRING);
	}
	
	public SimpleFormat(String value, PayloadType payloadType) {
		super(value, payloadType);
	}



	@Override
	protected FormatModel createModel(String value) {
		FormatModelBuilder builder=DefaultFormatModelBuilder.builderInstance();
		return builder.buildFormatModel(value);
	}

}
