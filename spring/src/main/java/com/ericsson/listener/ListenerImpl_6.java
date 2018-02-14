package com.ericsson.listener;

import org.springframework.stereotype.Component;

import com.ericsson.RegisterListener;
import com.ericsson.message.Message;

@Component
@RegisterListener
public class ListenerImpl_6 extends AbstractListenerImpl {

	public void processMsg(Message msg) {
		super.processMsg(msg);		
	}
}
