package com.gui.action;

import com.gui.action.EventHandler;
import com.gui.action.AbstractEvent.Type;


public interface Event<EH extends EventHandler> {

	void execute(EH t);
	
	/*
	 * Act as key in HashMap
	 */
	public Type<EH> getType();
	
}