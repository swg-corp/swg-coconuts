package com.swg.coconuts.action.sampler;

import java.util.Map;
import java.util.Set;

import com.swg.coconuts.action.format.Format;

/**
 * 
 * @author satriaprayoga
 *
 */
public interface Sampler {

	Format createFromSample(String payload);
	String getKeywordPart();
	Map<String, SampleInfo> getSampleInfo();
	Set<String> getParametersValue();
	String getParameterValueAt(int position);
	String getParameterStringFor(String paramname);
}
