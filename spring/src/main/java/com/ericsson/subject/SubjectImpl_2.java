package com.ericsson.subject;

import com.ericsson.message.Message;

public class SubjectImpl_2 extends AbstractSubjectImpl {

	private Message msg;
	
	public void newMsg() {
		msg.setMsg("SubjectImpl_2");
		update(msg);
	}

	public void setMsg(Message msg) {
		this.msg = msg;
	}
}
