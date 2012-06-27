package com.swg.coconuts.initiator.report;

import java.util.Collection;


public interface VoteMapsAware<T> {

	Collection<VoteMap> getData(T source);
}
