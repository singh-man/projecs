package com.ericsson.listener;

import com.ericsson.message.Message;
import com.ericsson.message.MessageGeneric;

public class AbstractListenerImpl implements Listener {

	@Override
	public void processMsg(Message msg) {
		System.out.println(msg.msgDesc() + " <-> updates listener <-> " + this.getClass().getName());		
	}

	@Override
	public <T> void processMsg(MessageGeneric<T> msgT) {
		System.out.println(msgT.getSubject() + " <-> updates listener <-> " + this.getClass().getName()+ " and the msg is: " + msgT.getMsg());
	}

}
