package com.gui.action;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		EventBus eventBus = EventBus.getInstance();

		addUser(eventBus);

		addConfiguration(eventBus);

		eventBus.fireEvent(new UserAddEvent());
		eventBus.fireEvent(new ConfigurationAddEvent());
		
	}

	private static void addUser(EventBus eventBus) {
		eventBus.addHandler(UserAddEvent.eventType, new UserAddEventHandler() {

			@Override
			public void addUser(UserAddEvent command) {
				System.out.println("Adding user 1");

			}

		});
		eventBus.addHandler(UserAddEvent.eventType, new UserAddEventHandler() {

			@Override
			public void addUser(UserAddEvent command) {
				System.out.println("Adding user 2");

			}

		});
	}

	private static void addConfiguration(EventBus eventBus) {
		eventBus.addHandler(ConfigurationAddEvent.eventType, new ConfigurationAddEventHandler() {

			@Override
			public boolean addConfiguration(ConfigurationAddEvent command) {
				System.out.println("Adding conf 1");
				return true;
			}
		});

		eventBus.addHandler(ConfigurationAddEvent.eventType, new ConfigurationAddEventHandler() {

			@Override
			public boolean addConfiguration(ConfigurationAddEvent evt) {
				System.out.println("Adding conf 2");
				return false;
			}
		});
	}

}
