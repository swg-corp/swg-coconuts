package com.swg.coconuts.action;

public class ActionValidationException extends ActionException {
	private static final long serialVersionUID = 4861114457052334488L;

	public ActionValidationException(String message, int code, Action action) {
		super(message, code, action);
	}
}
