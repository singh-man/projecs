package com.ericsson.message;

public interface MessageGeneric<T> {

	String getSubject();
	void setSubject(String subject);
	
	void setMsg(T t);
	T getMsg();
}
