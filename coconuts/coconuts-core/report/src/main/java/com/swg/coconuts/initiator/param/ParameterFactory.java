package com.swg.coconuts.initiator.param;

import java.io.Serializable;

/**
 * Factory sederhana untuk membuat instance dari {@link com.swg.coconuts.initiator.param.CellParam}
 * @author satriaprayoga
 *
 */
public interface ParameterFactory {

	<T extends Serializable> CellParam<?> getCellParam(Class<?> clazz,Object value);
}
