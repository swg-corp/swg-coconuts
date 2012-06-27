package com.swg.coconuts.messaging.processor;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/*
 **
 * Factory untuk object {@link ActionStack} yang mencreate fresh action stack
 * setiap kali {@link #create()} dipanggil.
 * 
 * @author zakyalvan
 */
@Component
public class ActionStackFactory implements ApplicationContextAware{
	private Logger logger = Logger.getLogger(getClass());
	private ApplicationContext applicationContext;

	public ActionStack create(boolean allowZeroAction) {
		logger.info("Create action stack. Stack with zero action allowed? " + allowZeroAction);
		ActionStack actionStack = new ActionStack();
		actionStack.refresh(applicationContext, allowZeroAction);
		return actionStack;
	}
	public ActionStack create() {
		return create(true);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

}
