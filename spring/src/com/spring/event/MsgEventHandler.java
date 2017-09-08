package com.spring.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

public class MsgEventHandler implements ApplicationEventPublisherAware {

	private ApplicationEventPublisher applicationEventPublisher;
	
	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;		
	}

	public void handleMsg(){
		ApplicationEvent event = new SmsMsgEvent("SMS");
		applicationEventPublisher.publishEvent(event);		
		BaseEvent evt = new MmsMsgEvent();
	}
}
