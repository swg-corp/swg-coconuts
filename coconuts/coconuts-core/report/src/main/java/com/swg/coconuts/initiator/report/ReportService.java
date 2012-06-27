package com.swg.coconuts.initiator.report;


/**
 * 
 * @author satriaprayoga
 *
 */
public interface ReportService<T> {

	VoteMap getVoteFor(T object);
	
	
}
