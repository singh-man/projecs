package com;

import java.util.Set;

public class Location {

	private long id;
	private String name;
	private Set<Event> event;
	private Integer version;
	
	public Location(){}
	
	public Location(String name) {
		this.name = name;
	}
	
	public Location(String name, Set<Event> event) {
		this.name = name; this.event = event;
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
	public Set<Event> getEvent() {
		return event;
	}
	public void setEvent(Set<Event> event) {
		this.event = event;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
	

}
