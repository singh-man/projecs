package com.spring.event;

import java.io.IOException;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Testing {

	
	public static void main(String[] args) throws IOException {
		BeanFactory app = new ClassPathXmlApplicationContext("com/spring/event/appContext.xml");
		MsgEventHandler myListener = (MsgEventHandler)app.getBean("msgEventHandler");
		myListener.handleMsg();
		System.in.read();
	}
}
