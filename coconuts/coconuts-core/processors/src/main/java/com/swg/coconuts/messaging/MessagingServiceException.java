package com.swg.coconuts.messaging;

public class MessagingServiceException extends RuntimeException {
	private static final long serialVersionUID = 8356013006915154244L;

	public MessagingServiceException() {
		super();
	}

	public MessagingServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public MessagingServiceException(String message) {
		super(message);
	}

	public MessagingServiceException(Throwable cause) {
		super(cause);
	}	
}
