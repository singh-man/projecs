package com.component;

import java.util.Set;

public class Location_1 {

	private long id;
	private String name;
	private Set<Event_1> event;
	private Address resAddress;
	private Address offAddress;
	
	public Location_1(){}
	
	public Location_1(String name) {
		this.name = name;
	}
	
	public Location_1(String name, Set<Event_1> event) {
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
	public Set<Event_1> getEvent() {
		return event;
	}
	public void setEvent(Set<Event_1> event) {
		this.event = event;
	}

	public Address getResAddress() {
		return resAddress;
	}

	public void setResAddress(Address resAddress) {
		this.resAddress = resAddress;
	}

	public Address getOffAddress() {
		return offAddress;
	}

	public void setOffAddress(Address offAddress) {
		this.offAddress = offAddress;
	}
	
	
}
