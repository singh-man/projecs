package com.component;

import java.util.Date;

public class Event_1 {
	
	private long id;
	private String name;
	private Date startDate;
	private long duration;
	private Location_1 location;
	
	public Event_1() {}
	
	public Event_1(String name, Date startDate, long duration) {
		this.name = name; this.startDate = startDate; this.duration = duration;
	}
	
	public Event_1(String name, Date startDate, long duration, Location_1 location) {
		this.name = name; this.startDate = startDate; this.duration = duration;
		this.location = location;
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
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public long getDuration() {
		return duration;
	}
	public void setDuration(long duration) {
		this.duration = duration;
	}

	public Location_1 getLocation() {
		return location;
	}
	public void setLocation(Location_1 location) {
		this.location = location;
	}

}
