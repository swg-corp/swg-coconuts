package com.swg.coconuts.initiator;

import java.util.List;

public interface TemplateMapperAware {

	void setTemplateMappers(List<TemplateMapper<?>> templateMapper);
}
