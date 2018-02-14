package com;

import java.util.Set;

public class Speaker {

	private long id;
	private String name;
	private String title;
	private Set<Event> event;
	
	public Speaker(){	}
	
	public Speaker(String name, String title) {
		this.name = name; this.title = title;
	}
	public Speaker(String name, String title, Set<Event> event) {
		this.name = name; this.title = title; this.event = event;
	}
	public Set<Event> getEvent() {
		return event;
	}
	public void setEvent(Set<Event> event) {
		this.event = event;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

}
