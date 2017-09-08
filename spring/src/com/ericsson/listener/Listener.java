package com.ericsson.listener;

import com.ericsson.message.Message;
import com.ericsson.message.MessageGeneric;

public interface Listener {
	
	void processMsg(Message msg);
	<T> void processMsg(MessageGeneric<T> msgT);
}
