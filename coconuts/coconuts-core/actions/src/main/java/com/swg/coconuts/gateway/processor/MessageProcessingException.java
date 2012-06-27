package com.swg.coconuts.gateway.processor;

public class MessageProcessingException extends RuntimeException {
	private static final long serialVersionUID = -9050888156201410117L;

	public static enum FailedStep {
		PRE_PROCESSING,
		ON_PROCESSING,
		POST_PROCESSING
	}
	
	private FailedStep failedStep = FailedStep.ON_PROCESSING;
	private int code = 0;
	
	public MessageProcessingException(String message, int code) {
		super(message);
		this.code = code;
	}
	public MessageProcessingException(String message, int code, Throwable cause) {
		super(message, cause);
		this.code = code;
	}
	
	public final FailedStep getFailedStep() {
		return failedStep;
	}
	protected final void setFailedStep(FailedStep failedStep) {
		this.failedStep = failedStep;
	}
	
	public final int getCode() {
		return code;
	}
	
	/**
	 * Untuk debug aja ini.
	 */
	@Override
	public String toString() {
		return "MessageProcessingException [failedStep=" + failedStep
				+ ", code=" + code + ", getMessage()=" + getMessage() + "]";
	}
	
	
	public static class MessagePreProcessingException extends MessageProcessingException {
		private static final long serialVersionUID = -5976618223854892531L;

		public MessagePreProcessingException(String message, int code, Throwable cause) {
			super(message, code, cause);
			setFailedStep(FailedStep.PRE_PROCESSING);
		}

		public MessagePreProcessingException(String message, int code) {
			super(message, code);
			setFailedStep(FailedStep.PRE_PROCESSING);
		}
	}
	public static class MessagePostProcessingException extends MessageProcessingException {
		private static final long serialVersionUID = 3163133640512880515L;

		public MessagePostProcessingException(String message, int code, Throwable cause) {
			super(message, code, cause);
			setFailedStep(FailedStep.POST_PROCESSING);
		}
		public MessagePostProcessingException(String message, int code) {
			super(message, code);
			setFailedStep(FailedStep.POST_PROCESSING);
		}
	}
}
