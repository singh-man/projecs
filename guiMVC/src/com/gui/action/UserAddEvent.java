package com.gui.action;


public class UserAddEvent extends AbstractEvent<UserAddEventHandler>{

	public static Type<UserAddEventHandler> eventType = new Type<UserAddEventHandler>();

	@Override
	public void execute(UserAddEventHandler eh) {
		eh.addUser(this);
	}

	@Override
	public Type<UserAddEventHandler> getType() {
		return eventType;
	}

}
