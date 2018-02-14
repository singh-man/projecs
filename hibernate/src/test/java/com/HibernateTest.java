package com;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HibernateTest {

	private Configuration cfg;
	private SessionFactory sessionFactory;
	private Session session;
	private Transaction transaction;
	private Testing testing;

	@Before
	public void setUp() throws Exception {
		cfg = new Configuration().configure();
		testing = new Testing();
		startTransaction();
	}

	@Test
	public void testSchemaScript() {
		SchemaExport se = new SchemaExport(cfg);
		se.create(true, false);
	}

	@Test
	public void testSaveMassEvent() {
		testing.saveMassEvent(session);
	}
	
	@Test
	public void testSaveConferenceEvent() {
		testing.saveConferenceEvent(session);
	}

	@Test
	public void testPrint() {
		testing.print(session);
	}

	@Test
	public void testSaveUpdate() {
		testing.saveUpdate(session);
	}
	
	@Test
	public void testSaveExistingObject() {
		testing.saveExistingObject(session);
	}

	private void buildSessionFactory() {
		sessionFactory = cfg.buildSessionFactory();
	}

	@After
	public void tearDown() {
		endTransaction();
	}
	
	private void startTransaction() {
		buildSessionFactory();
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
	}

	private void endTransaction() {
		transaction.commit();
		session.close();
	}
}
