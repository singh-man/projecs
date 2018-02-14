package com.ericsson.subject;

import com.ericsson.message.Message;
import com.ericsson.message.MessageGeneric;

public class SubjectImpl_3 extends AbstractSubjectImpl {

	private Message msg;
	//private MessageGeneric<T> msgT;
	
	public void newMsg() {
		msg.setMsg("SubjectImpl_2");
		update(msg);
	}

	public void setMsg(Message msg) {
		this.msg = msg;
	}
	
	public <T> void newMsg(MessageGeneric<T> msgT) {
		msgT.setSubject("SubjectImpl_3");
		update(msgT);
	}
}
