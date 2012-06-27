/**
 * 
 */
package com.swg.coconuts.action.sampler;

/**
 * @author satriaprayoga
 *
 */
public final class SamplerFactory {
	
	public enum SamplerType{
		SIMPLE,
		MULTI,
		MAPPED
	}

	public static Sampler getSamplerForType(SamplerType type){
		if(type==null){
			throw new IllegalArgumentException("type cannot be null!");
		}
		Sampler sampler=null;
		switch (type) {
		case SIMPLE:
			sampler=new SimpleSampler();
			break;
		case MULTI:
			sampler=new MultiSampler();
			break;
		case MAPPED:
			sampler=new MapSampler();
			break;
		default:
			break;
		}
		return sampler;
	}
}
