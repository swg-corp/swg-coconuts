package com.swg.coconuts.messaging.processor;

import java.util.Map;
import java.util.Stack;

import org.springframework.context.ApplicationContext;

import com.swg.coconuts.action.Action;

/**
 * Object yang nampung actions.
 * 
 * @author zakyalvan
 */
public class ActionStack extends Stack<Action> {
	private static final long serialVersionUID = -8563395153633624949L;
	
	public void refresh(ApplicationContext applicationContext) {
		refresh(applicationContext, true);
	}
	public void refresh(ApplicationContext applicationContext, boolean allowZeroAction) {
		clear();
		Map<String, Action> actionsMap = applicationContext.getBeansOfType(Action.class);
		if(actionsMap.values().size() == 0 && !allowZeroAction) {
			throw new IllegalStateException("Object action tidak ditemukan dalam application context. Stack action harus berisi minimal satu action.");
		}
		addAll(actionsMap.values());
	}
}

