package com.gui.action;
/**
 * An Event Bus 
 * exactly
 * a map of <event types, (list) of handlers>
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.gui.action.AbstractEvent.Type;

public class EventBus {

	private Map<AbstractEvent.Type<? extends EventHandler>, ArrayList<? extends EventHandler>> handlerMap;

	private EventBus() {
		handlerMap = new HashMap<AbstractEvent.Type<? extends EventHandler>, ArrayList<? extends EventHandler>>();
	}

	public <EH extends EventHandler> void addHandler(Type<EH> type, EH handler) {
		ArrayList<EH> list = getHandlersList(type);
		if(list == null) {
			list = new ArrayList<EH>();
			handlerMap.put(type, list);
		}
		list.add(handler);
	}
	
	public <EH extends EventHandler> void removeHandler(Type<EH> type, EH handler) {
		ArrayList<EH> list = getHandlersList(type);
		boolean result = (list == null) ? false : list.remove(handler);
		if (result && list.size() == 0) {
			handlerMap.remove(type);
		}
	}

	private <EH> ArrayList<EH> getHandlersList(Type<EH> type) {
		return (ArrayList<EH>) handlerMap.get(type);
	}

	public <EH extends EventHandler> void fireEvent(Event<EH> event) {
		ArrayList<EH> handlerList = getHandlersList(event.getType());
		for(EH handler : handlerList) {
			event.execute(handler);
		}
	}

	/**
	 * SingletonEventCenter is loaded on the first execution of EventBus.getInstance() 
	 * or the first access to SingletonEventCenter.INSTANCE, not before.
	 */
	private static class SingletonEventCenter { 
		public static final EventBus INSTANCE = new EventBus();
	}

	public static EventBus getInstance() {
		return SingletonEventCenter.INSTANCE;
	}
}
