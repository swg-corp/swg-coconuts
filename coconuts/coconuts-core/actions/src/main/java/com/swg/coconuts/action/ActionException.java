package com.swg.coconuts.action;

/**
 * Exception yang dilempar pada saat eksekusi action gagal.
 * 
 * @author zakyalvan
 */
public class ActionException extends RuntimeException {
	private static final long serialVersionUID = -6963324075113801446L;
	
	protected int code;
	protected Action action;
	
	public ActionException(String message, int code, Action action) {
		super(message);
		assert(action != null) : "Parameter action tidak boleh null.";
		
		this.code = code;
		this.action = action;
	}

	public int getCode() {
		return code;
	}
	public Action getAction() {
		return action;
	}
}
