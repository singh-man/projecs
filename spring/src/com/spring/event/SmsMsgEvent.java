package com.spring.event;

import org.springframework.context.ApplicationEvent;

public class SmsMsgEvent extends ApplicationEvent implements BaseEvent {

	private String name;
	
	public SmsMsgEvent(Object source) {
		super(source);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String getMsgDescription() {
		return "SMS event";
	}
}
