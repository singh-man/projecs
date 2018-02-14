package com;

import java.util.Date;
import java.util.Set;

public class MassEvent extends Event {

	private int noOfPeople;

	private MassEvent() {}
	
	public MassEvent(int noOfPeople) {
		super();
		this.noOfPeople = noOfPeople;
	}

	public MassEvent(String string, Date date, long l, Set<Speaker> object) {
		super(string, date, l, object);
	}

	public int getNoOfPeople() {
		return noOfPeople;
	}

	public MassEvent setNoOfPeople(int noOfPeople) {
		this.noOfPeople = noOfPeople;
		return this;
	}
	
	public MassEvent addNoOfPeople(int noOfPeople) {
		this.noOfPeople += noOfPeople;
		return this;
	}
	
	
}
