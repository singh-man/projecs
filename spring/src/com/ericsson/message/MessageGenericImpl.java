package com.ericsson.message;

public class MessageGenericImpl<T> implements MessageGeneric<T>{

	private T t;
	private String subject;
	
	@Override
	public void setMsg(T t) {
		this.t = t;
	}

	@Override
	public T getMsg() {
		return t;
	}

	@Override
	public String getSubject() {
		return subject;
	}

	@Override
	public void setSubject(String subject) {
		this.subject = subject;
	}

}