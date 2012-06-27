package com.swg.coconuts.action.event;

import java.io.Serializable;

public class ActionEvent implements IActionEvent,Serializable{

	private static final long serialVersionUID = 7607724830101219663L;
	private Object source;
	private final String eventMessage;
	
	public ActionEvent(String eventMessage) {
		this.eventMessage=eventMessage;
	}
	
	@Override
	public void setSource(Object source) {
		this.source=source;
	}

	@Override
	public Object getSource() {
		return source;
	}

	@Override
	public String getEventMessage() {
		return eventMessage;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof IActionEvent){
			IActionEvent other=(IActionEvent)obj;
			if(other.getSource()!=this.getSource()){
				return false;
			}
			return true;
		}else{
			return false;
		}
		
	}
	
	@Override
	public String toString() {
		return "[IAction Event= eventMessage: "+eventMessage+", event source: "+source+"]";
	}

}
