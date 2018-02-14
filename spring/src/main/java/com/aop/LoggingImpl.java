package com.aop;

public class LoggingImpl {
	
	public void beforeRead(){
		System.out.println("LoggingImpl: logging injected via autowiring");
	}
}
