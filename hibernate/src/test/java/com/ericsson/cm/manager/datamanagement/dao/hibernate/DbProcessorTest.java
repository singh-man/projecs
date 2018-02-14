package com.ericsson.cm.manager.datamanagement.dao.hibernate;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.orm.hibernate3.HibernateSystemException;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;

import com.ericsson.cm.manager.datamanagement.DbProcessor;

public class DbProcessorTest {

	@Mock private LocalSessionFactoryBean lsfb;
	
	private DbProcessor processor;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		processor = new DbProcessor();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSchemaValidationSuccess() {
		processor.postProcessAfterInitialization(lsfb, null);
		Mockito.verify(lsfb).validateDatabaseSchema();
	}
	
	@Test
	public void testSchemaValidationFailedAndCreationSuccess() {
		Mockito.doThrow(new HibernateSystemException(null)).when(lsfb).validateDatabaseSchema();
		processor.postProcessAfterInitialization(lsfb, null);
		Mockito.verify(lsfb).createDatabaseSchema();
	}
	
	@Test
	public void testSchemaValidationFailedAndCreationFailed() {
		Mockito.doThrow(new HibernateSystemException(null)).when(lsfb).validateDatabaseSchema();
		Mockito.doThrow(new HibernateSystemException(null)).when(lsfb).createDatabaseSchema();
		Assert.assertEquals(lsfb, processor.postProcessAfterInitialization(lsfb, null));
	}

}
