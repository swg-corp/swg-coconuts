package com.swg.coconuts.action;

public interface Validator<T> {
	void validate(T object);
}
