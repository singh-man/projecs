package com;

import java.util.Date;
import java.util.Set;

public class ConferenceEvent extends Event {
	

	private int noOfSeats;

	public ConferenceEvent(){}
	
	public ConferenceEvent(int noOfSeats) {
		this.noOfSeats = noOfSeats;
	}

	public ConferenceEvent(String string, Date date, long l, Set<Speaker> object) {
		super(string, date, l, object);
	}
	
	public int getNoOfSeats() {
		return noOfSeats;
	}

	public void setNoOfSeats(int noOfSeats) {
		this.noOfSeats = noOfSeats;
	}
}
