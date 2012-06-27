package com.swg.coconuts.gateway.processor;

import java.io.Serializable;

import com.swg.coconuts.gateway.IIncomingMessage;
import com.swg.coconuts.gateway.processor.MessageProcessingException.FailedStep;

/**
 * Informasi kegagalan pemrosesan pesan masuk.
 * 
 * @author zakyalvan
 */
public final class ProcessFailureInfo implements Serializable {
	private static final long serialVersionUID = 8449848118458958772L;
	
	private final IIncomingMessage processedMessage;
	private final int code;
	private final FailedStep failedStep;
	private final Throwable failureCause;
	
	public ProcessFailureInfo(IIncomingMessage processedMessage, int code, FailedStep failedStep, Throwable failureCause) {
		this.processedMessage = processedMessage;
		this.code = code;
		this.failedStep = failedStep;
		this.failureCause = failureCause;
	}
	
	public IIncomingMessage getProcessedMessage() {
		return processedMessage;
	}
	public int getCode() {
		return code;
	}
	public FailedStep getFailedStep() {
		return failedStep;
	}
	public Throwable getFailureCause() {
		return failureCause;
	}

	@Override
	public String toString() {
		return "ProcessFailureInfo [code=" + code + ", failedStep="
				+ failedStep + ", failureCause=" + failureCause 
				+"(with message : " + failureCause != null ? failureCause.getMessage() : "" + ")" + "]";
	}
}
