package com.swg.coconuts.action.event;

public interface IActionEvent {

	void setSource(Object source);
	Object getSource();
	String getEventMessage();
}
