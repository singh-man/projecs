package com.ericsson.subject;

import java.util.ArrayList;
import java.util.List;

import com.ericsson.listener.Listener;
import com.ericsson.message.Message;
import com.ericsson.message.MessageGeneric;

public abstract class AbstractSubjectImpl implements Subject {
	
	private List<Listener> listeners = new ArrayList<Listener>();

	@Override
	public void addListner(Listener lsr) {
		listeners.add(lsr);
	}

	@Override
	public void removeListener(Listener lsr) {
		listeners.remove(lsr);
	}

	protected void update(Message msg) {
		for(Listener list : listeners) {
			list.processMsg(msg);
		}
	}
	
	protected <T> void update(MessageGeneric<T> msgT) {
		for(Listener list : listeners) {
			list.processMsg(msgT);
		}
	}
	
	public <T> void newMsg(MessageGeneric<T> msg) {
		throw new RuntimeException("This should be overriden in the SUb classes");
	}

}
