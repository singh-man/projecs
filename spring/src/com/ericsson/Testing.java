package com.ericsson;

/**
 * Listeners registered with Subjects are
 * 
 * ListenerImpl_1		SubjectImpl_2
 * ListenerImpl_1_1		SubjectImpl_1 (will add the parent subjects too)
 * ListenerImpl_2		SubjectImpl_1
 * ListenerImpl_3
 * ListenerImpl_4		SubjectImpl_1
 * ListenerImpl_5		SubjectImpl_1
 * ListenerImpl_6		ALL							Spring injected via @Component
 * ListenerImpl_7		SubjectImpl_1,SubjectImpl_2
 * 
 */
import java.io.IOException;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ericsson.message.MessageGeneric;
import com.ericsson.message.MessageGenericImpl;
import com.ericsson.subject.Subject;
import com.ericsson.subject.SubjectImpl_1;
import com.ericsson.subject.SubjectImpl_2;
import com.ericsson.subject.SubjectImpl_3;

public class Testing {
	
	public static void main(String[] args) throws IOException {
		BeanFactory app = new ClassPathXmlApplicationContext("com/ericsson/appContext.xml");
		
		Subject sub_1 = app.getBean(SubjectImpl_1.class);
		sub_1.newMsg();
		
		System.out.println("****************************");
		Subject sub_2 = app.getBean(SubjectImpl_2.class);
		sub_2.newMsg();
		
		System.out.println("**********************************************");
		System.out.println("***** For Generic Msg ******");
		MessageGeneric<Integer> msg_1 = new MessageGenericImpl<Integer>();
		Subject sub_3 = app.getBean(SubjectImpl_3.class);
		msg_1.setMsg(1);
		sub_3.newMsg(msg_1);
		MessageGeneric<String> msg_2 = new MessageGenericImpl<String>();
		msg_2.setMsg("a generic call");
		sub_3.newMsg(msg_2);
		
	}
}




/* EXPECTED OUTPUT
MessageImpl_1 being prototype <-> check the count is never increased 0
MessageImpl_1 being prototype <-> check the count is never increased 0
SubjectImpl_1 <-> updates listener <-> com.ericsson.listener.ListenerImpl_6
SubjectImpl_1 <-> updates listener <-> com.ericsson.listener.ListenerImpl_1_1
SubjectImpl_1 <-> updates listener <-> com.ericsson.listener.ListenerImpl_2
SubjectImpl_1 <-> updates listener <-> com.ericsson.listener.ListenerImpl_4
SubjectImpl_1 <-> updates listener <-> com.ericsson.listener.ListenerImpl_5
SubjectImpl_1 <-> updates listener <-> com.ericsson.listener.ListenerImpl_7
****************************
SubjectImpl_2 <-> updates listener <-> com.ericsson.listener.ListenerImpl_6
SubjectImpl_2 <-> updates listener <-> com.ericsson.listener.ListenerImpl_1
SubjectImpl_2 <-> updates listener <-> com.ericsson.listener.ListenerImpl_1_1
SubjectImpl_2 <-> updates listener <-> com.ericsson.listener.ListenerImpl_7
**********************************************
***** For Generic Msg ******
SubjectImpl_3 <-> updates listener <-> com.ericsson.listener.ListenerImpl_6 and the msg is: 1
SubjectImpl_3 <-> updates listener <-> com.ericsson.listener.ListenerImpl_8 and the msg is: 1
SubjectImpl_3 <-> updates listener <-> com.ericsson.listener.ListenerImpl_6 and the msg is: a generic call
SubjectImpl_3 <-> updates listener <-> com.ericsson.listener.ListenerImpl_8 and the msg is: a generic call*/