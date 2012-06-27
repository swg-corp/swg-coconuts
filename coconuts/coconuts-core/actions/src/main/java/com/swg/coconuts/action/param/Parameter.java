package com.swg.coconuts.action.param;

/**
 * 
 * @author zakyalvan
 *
 * @param <T>
 */
public interface Parameter<T> {

	String getName();
	T getValue();
	Class<T> getType();
}
