package com.ericsson.listener;

import com.ericsson.RegisterListener;
import com.ericsson.message.Message;
import com.ericsson.subject.SubjectImpl_1;
import com.ericsson.subject.SubjectImpl_2;


@RegisterListener(subject={SubjectImpl_1.class,SubjectImpl_2.class})
public class ListenerImpl_7 extends AbstractListenerImpl {

	public void processMsg(Message msg) {
		super.processMsg(msg);		
	}
}
