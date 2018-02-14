package com.spring.event;

public class MmsMsgEvent implements BaseEvent{

	@Override
	public String getMsgDescription() {
		return "MMS event";
	}

}
