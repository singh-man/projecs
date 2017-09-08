package com.ericsson.message;

/**
 * New instance is created by the "new" operator hence not registered in Spring context
 * @author emmhssh
 *
 */
public class MessageImpl implements Message {

	private int count;
	private String msg;
	
	private MessageImpl() {
		System.out.println("MessageImpl_1 being prototype <-> check the count is never increased " + count++);
	}
	
	@Override
	public String msgDesc() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
