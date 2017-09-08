package com.ericsson.listener;

import com.ericsson.RegisterListener;
import com.ericsson.message.Message;
import com.ericsson.subject.SubjectImpl_1;

/**
 * This is the subclass of ListenerImpl_1
 * 
 * @author emmhssh
 *
 */
@RegisterListener(subject=SubjectImpl_1.class,includeParent=true)
public class ListenerImpl_1_1 extends ListenerImpl_1 {

	public void processMsg(Message msg) {
		
		super.processMsg(msg);		
	}
}
