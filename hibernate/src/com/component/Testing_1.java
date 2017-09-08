package com.component;

import java.util.Date;
import java.util.Iterator;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class Testing_1 {
	
	public static void save(Session session){
		System.out.println("save objects");
		Address addr = new Address(22, "delhi");
		Location_1 loc = new Location_1("my loc");
		
		/* check the component here rAddr and oAddr are mapped differently in hbm file
		 * also check both the address type i.e. rAddr and oAddr refer to different columns
		 * rAddr to hno and city
		 * oAddr to o_hno and o_city 
		 */
		//loc.setResAddress(addr);
		loc.setOffAddress(addr);
		Event_1 eve = new Event_1("My event", new Date(), 15, null);
		eve.setLocation(loc);
		//session.save(loc); //disable this if using cascade="save-update" in event.hbm.xml at <may-to-one> tag
		session.save(eve);
		session.flush();
	}
	
	public static void print(Session session){
		System.out.println("retrieving objects");
		Event_1 event = (Event_1) session.createQuery("from Event_1").list().get(0);
		System.out.println("for may-to-one");
		System.out.println(event.getName() + " : " + event.getLocation().getName());

		System.out.println("demo one-to-many");
		Location_1 location = (Location_1) session.createQuery("from Location_1").list().get(2);
		System.out.println(location.getId() + " : " + location.getName() + " : " + location.getEvent().size());
		Iterator<Event_1> it = location.getEvent().iterator();
		while(it.hasNext()) {
			Event_1 eve = it.next();
			System.out.println(eve.getName());
		}

		Event_1 event1 = (Event_1) session.createQuery("from Event_1 e where e.name = 'call'").list().get(0);
		System.out.println(event1.getName() + " : " + event1.getStartDate());
		

		System.out.println("fetching by named query");
		Query q = session.getNamedQuery("findEventByDuration");
		q.setString(0, "15");
		Iterator<Event_1> durationIt = q.list().iterator();
		while(it.hasNext()){
			System.out.println(" by duration : " + durationIt.next().getName() + " : " + durationIt.next().getDuration());
		}
	}

	public static void main(String[] args) {
		Configuration config = new Configuration().configure();
		SessionFactory sessionFactory = config.buildSessionFactory();
		Session session = sessionFactory.openSession();
		try {
			Transaction tx = session.beginTransaction();

			//save(session);
			print(session);
						
			tx.commit();
		}catch(Exception e){
			System.out.println(e);
		}
	}

}