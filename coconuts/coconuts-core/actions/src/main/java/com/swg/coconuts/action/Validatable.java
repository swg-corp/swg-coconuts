package com.swg.coconuts.action;

/**
 * Simple validation kontrak untuk object action yang dapat divalidate.
 * 
 * @author zakyalvan
 *
 * @param <T>
 */
public interface Validatable {
	void validate() throws ActionValidationException;
}
