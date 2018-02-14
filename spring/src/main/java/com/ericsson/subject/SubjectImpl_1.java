package com.ericsson.subject;

import com.ericsson.message.Message;

public class SubjectImpl_1 extends AbstractSubjectImpl {

	private Message msg;
	
	public void newMsg() {
		msg.setMsg("SubjectImpl_1");
		update(msg);
	}

	public void setMsg(Message msg) {
		this.msg = msg;
	}
	
}
