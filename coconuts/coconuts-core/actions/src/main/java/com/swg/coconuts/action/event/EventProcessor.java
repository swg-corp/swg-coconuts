package com.swg.coconuts.action.event;

import java.util.Calendar;

import org.apache.log4j.Logger;

public abstract class EventProcessor{

	private final static Logger logger=Logger.getLogger(EventProcessor.class);
	
	protected abstract IActionEvent createActionEvent();
	
	private IActionEventListener eventListener;
	
	public void setEventListener(IActionEventListener eventListener) {
		this.eventListener = eventListener;
	}
	
	public IActionEventListener getEventListener() {
		return eventListener;
	}
	
	protected static class DefaultEventProcessor extends EventProcessor{
		
		@Override
		protected IActionEvent createActionEvent() {
			IActionEvent actionEvent=new ActionEvent("Aksi dieksekusi");
			actionEvent.setSource(this);
			return actionEvent;
		}
		
	
		@Override
		public IActionEventListener getEventListener() {
			IActionEventListener eventListener=new IActionEventListener() {
				
				@Override
				public void onActionExecuted() {
					logger.info("executed on "+Calendar.getInstance().getTime());
				}
			};
			return eventListener;
		}
	}
}
