package com.swg.coconuts.initiator.param;

import java.util.List;

/**
 * Interface untuk Area geografis yang lebih besar
 * @author satriaprayoga
 *
 */
public interface BigArea extends Area{

	public List<? extends Area> getChilds();
}
