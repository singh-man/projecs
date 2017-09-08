package com.ericsson.listener;

import com.ericsson.RegisterListener;
import com.ericsson.message.Message;
import com.ericsson.subject.SubjectImpl_1;

@RegisterListener(subject=SubjectImpl_1.class)
public class ListenerImpl_5 extends AbstractListenerImpl {

	public void processMsg(Message msg) {
		super.processMsg(msg);		
	}
}
