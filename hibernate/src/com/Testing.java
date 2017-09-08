package com;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.hibernate.Session;

/**
 * pending task
 * 
 * 1. many-to-many mapping
 * 2. inheritance mapping : table per class
 * @author Acer
 *
 */
public class Testing {

	public void saveExistingObject(Session session) {
		ConferenceEvent confEve = getConferenceEvent();

		// event with id 1 exists in the tables
		// Using generator hence this set is ignored and new row will be inserted
		confEve.setId(1);
		confEve.setName("New name to existing one");
		session.saveOrUpdate(confEve);
	}
	
	public void saveUpdate(Session session) {
		Event event = (Event) session.createQuery("from Event e where e.id = (select max(e.id) from Event e)").list().get(0);
		event.setName("My eventv 3");
		event.getLocation().setName("new location 5");
		//example to show use of unsaved-value in hibernate 
		session.saveOrUpdate(event);
	}
	
	public void saveMassEvent(Session session){
		System.out.println("save objects");
		Location loc = getLocation();
		
		//Check - add - method returns MassEvent Object
		MassEvent massEve = getMassEvent()
				.addNoOfPeople(130)
				.addNoOfPeople(250);
		massEve.setLocation(loc);
		massEve.setSpeaker(getSpeakerSet());
		//session.save(loc); //disable this if using cascade="save-update" in event.hbm.xml at <may-to-one> tag
		session.save(getSpeaker());
		session.save(massEve);
		/*Set<Event> add = new HashSet<Event>();add.add(massEve);
		loc.setEvent(add);// use this if u want to store everything while saving only location
		session.save(loc);*/
	}
	
	public void saveConferenceEvent(Session session){
		System.out.println("save objects");
		Location loc = getLocation();
		ConferenceEvent confEve = getConferenceEvent();
		confEve.setNoOfSeats(20);
		confEve.setSpeaker(getSpeakerSet());
		confEve.setLocation(loc);
		//session.save(loc); //disable this if using cascade="save-update" in event.hbm.xml at <may-to-one> tag
		//session.save(speaker); //disable this if using cascade="save-update" in event.hbm.xml at <set> tag
		session.save(confEve);
	}

	private ConferenceEvent getConferenceEvent() {
		return new ConferenceEvent("My event", new Date(), 15L, null);
	}

	public void print(Session session){
		Event event = (Event) session.createQuery("from Event").list().get(0);
		System.out.println("retrieving objects");
		System.out.println("for may-to-one");
		System.out.println(event.getName() + " : " + event.getLocation().getName());

		System.out.println("demo one-to-many");
		Location location = (Location) session.createQuery("from Location").list().get(2);
		System.out.println(location.getName() + " : " + location.getEvent().size());
		Iterator<Event> it = location.getEvent().iterator();
		while(it.hasNext()) {
			System.out.println(it.next().getName());
		}

		Event event1 = (Event) session.createQuery("from Event e where e.name = 'call'").list().get(0);
		System.out.println(event1.getName() + " : " + event1.getStartDate());
	}

	public static void main(String[] args) {
		
	}

	private Set<Speaker> getSpeakerSet() {
		Set<Speaker> setSpeaker = new HashSet<Speaker>();
		setSpeaker.add(getSpeaker());
		return setSpeaker;
	}
	private Speaker getSpeaker() {
		return new Speaker("manish", "prof");
	}

	private Location getLocation() {
		return new Location("my loc");
	}

	private MassEvent getMassEvent() {
		return new MassEvent("My event", new Date(), 15L, null);
	}
	
}