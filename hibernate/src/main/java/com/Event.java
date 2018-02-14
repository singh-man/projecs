package com;

import java.sql.Blob;
import java.util.Date;
import java.util.Set;

public abstract class Event {
	
	private long id;
	private String name;
	private Date startDate;
	private long duration;
	private Set<Speaker> speaker;
	private Location location;
	private Integer version;
	
	public Event() {}
	
	public Event(String name, Date startDate, long duration, Set<Speaker> speaker) {
		this.name = name; this.startDate = startDate; this.duration = duration;
		this.speaker = speaker;
	}
	
	public Event(String name, Date startDate, long duration, Set<Speaker> speaker, Location location) {
		this.name = name; this.startDate = startDate; this.duration = duration;
		this.speaker = speaker; this.location = location;
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

	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public void setSpeaker(Set<Speaker> speaker) {
		this.speaker = speaker;
	}
	public Set<Speaker> getSpeaker() {
		return speaker;
	}

	protected Integer getVersion() {
		return version;
	}

	protected void setVersion(Integer version) {
		this.version = version;
	}
	

	private Blob buf;

	public Blob getBuf() {
		return buf;
	}

	public void setBuf(Blob buf) {
		this.buf = buf;
	}
	
}
