package com.swg.coconuts.initiator;

import com.swg.coconuts.initiator.xls.Content;

public interface TemplateMapper<T> {

	public T getTarget(Content content);
	
	public Class<T> getTargetType();
}
