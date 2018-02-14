package com.ericsson.subject;

import com.ericsson.listener.Listener;
import com.ericsson.message.MessageGeneric;

public interface Subject {

	void addListner(Listener lsr);
	void removeListener(Listener lsr);
	//void update(Message msg);
	void newMsg();
	<T> void newMsg(MessageGeneric<T> msg);
}
