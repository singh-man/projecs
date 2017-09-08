package com.gui.action;

public class ConfigurationAddEvent extends AbstractEvent<ConfigurationAddEventHandler>{

	public static Type<ConfigurationAddEventHandler> eventType = new Type<ConfigurationAddEventHandler>();
	
	
	@Override
	public void execute(ConfigurationAddEventHandler eh) {
		System.out.println(eh.addConfiguration(this));
	}

	@Override
	public Type<ConfigurationAddEventHandler> getType() {
		return eventType;
	}
}
