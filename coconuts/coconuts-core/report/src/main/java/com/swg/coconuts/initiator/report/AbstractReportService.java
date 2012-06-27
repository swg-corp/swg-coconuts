package com.swg.coconuts.initiator.report;

public abstract class AbstractReportService<T> implements ReportService<T>{

	private Class<T> clazz;
	
	public AbstractReportService(Class<T> clazz) {
		this.clazz=clazz;
	}
	
	public Class<T> getClazz() {
		return clazz;
	}
	
	public VoteMap getVoteFor(T object) {
		return null;
	};
}
