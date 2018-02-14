package com.spring.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

public class MsgListener implements ApplicationListener{

	@Override
	public void onApplicationEvent(ApplicationEvent arg0) {
		if(arg0 instanceof SmsMsgEvent) {
			System.out.println("its a SMS event");
			System.out.println(((SmsMsgEvent)arg0).getMsgDescription());
		}
	}
}
