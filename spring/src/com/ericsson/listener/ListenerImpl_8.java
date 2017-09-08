package com.ericsson.listener;

import org.springframework.stereotype.Component;

import com.ericsson.RegisterListener;
import com.ericsson.message.MessageGeneric;
import com.ericsson.subject.SubjectImpl_3;

@Component
@RegisterListener(subject=SubjectImpl_3.class)
public class ListenerImpl_8 extends AbstractListenerImpl {

	public <T> void processMsg(MessageGeneric<T> msgT) {
		super.processMsg(msgT);
	}
}
