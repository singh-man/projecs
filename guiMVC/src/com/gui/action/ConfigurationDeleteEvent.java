package com.gui.action;


@EventType(eventType=ConfigurationDeleteEvent.getT)
public class ConfigurationDeleteEvent extends AbstractEvent<ConfigurationAddEventHandler>{

	public static Type<ConfigurationAddEventHandler> eventType = new Type<ConfigurationAddEventHandler>();
	
	@Override
	public void execute(ConfigurationAddEventHandler t) {
		// TODO Auto-generated method stub
		
	}

	final static String getT = "";
	@Override
	public Type<ConfigurationAddEventHandler> getType() {
		// TODO Auto-generated method stub
		return null;
	}

}
