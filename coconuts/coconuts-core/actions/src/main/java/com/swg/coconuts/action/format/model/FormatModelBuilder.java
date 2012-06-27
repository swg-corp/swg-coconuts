/**
 * 
 */
package com.swg.coconuts.action.format.model;

/**
 * @author satriaprayoga
 *
 */
public interface FormatModelBuilder {

	FormatModel buildFormatModel(String value);
	
	FormatModel buildFromSample(String payload);
}
