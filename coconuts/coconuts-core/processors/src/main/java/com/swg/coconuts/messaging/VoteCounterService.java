package com.swg.coconuts.messaging;

import java.util.Map;

public interface VoteCounterService {

	public void update(Map<String, Integer> rawVotes,Integer kode,String kelurahan);
}
