/**
 * 
 */
package com.swg.coconuts.backend.domain;

import java.util.concurrent.atomic.AtomicInteger;


/**
 * @author satriaprayoga
 *
 */
public final class DomainContext {

	private static final AtomicInteger atomicInteger=new AtomicInteger(0);
	
	public static Integer generateTpsNumber(){
		return atomicInteger.incrementAndGet();
	}
	
	public static Integer removeTpsNumber(){
		return atomicInteger.decrementAndGet();
	}
}
