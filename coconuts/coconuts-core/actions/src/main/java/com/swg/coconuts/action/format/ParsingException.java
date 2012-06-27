package com.swg.coconuts.action.format;


public class ParsingException extends RuntimeException {
	private static final long serialVersionUID = 8748430592598071340L;
	
	private final Parser parser;
	
	public ParsingException(String message, Parser parser) {
		super(message);
		this.parser = parser;
	}

	public Parser getParser() {
		return parser;
	}
}
