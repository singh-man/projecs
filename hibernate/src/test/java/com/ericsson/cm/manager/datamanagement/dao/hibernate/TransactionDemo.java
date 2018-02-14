package com.ericsson.cm.manager.datamanagement.dao.hibernate;

import java.util.logging.Logger;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.ericsson.cm.common.exception.MMException;
import com.ericsson.cm.manager.datamanagement.DemoTrasaction;
import com.ericsson.cm.manager.datamanagement.dao.ConfigCollectionDAO;
import com.ericsson.cm.manager.datamanagement.dao.ServerGroupDAO;
import com.ericsson.cm.manager.datamanagement.dto.ConfigCollectionDTO;
import com.ericsson.cm.manager.datamanagement.dto.ServerGroupDTO;

public class TransactionDemo {

	Logger log = Logger.getLogger(ConfigCollectionDAOImplTest_1.class.getName());

	static ApplicationContext ctx;
	static HibernateTemplate hibernateTemplate;
	final static Long configCollPrimaryKey = (long) Math.abs((Math.random()*1000));

	ConfigCollectionDAO configCollectionDAO;
	ServerGroupDAO serverGroupDAO;

	@BeforeClass
	public static void oneTimeSetUp() {
		ctx = DaoTestHelperUtils.getContext();
		hibernateTemplate = ctx.getBean(HibernateTemplate.class);
	}

	@Before
	public void setUp() {
		configCollectionDAO = ctx.getBean(ConfigCollectionDAO.class);
		serverGroupDAO = ctx.getBean(ServerGroupDAO.class);
	}

	@Test(expected=Exception.class)
	public void daoLevelTrasactionDemo() throws Exception {

		configCollectionDAO.createConfigCollection(new ConfigCollectionDTO(
				configCollPrimaryKey, "name1", "desc1", "ow1",false, null));

		serverGroupDAO.createServerGroup(new ServerGroupDTO("sgr1", null));

		// Throws an Exception
		configCollectionDAO.updateConfigCollection(new ConfigCollectionDTO(
				configCollPrimaryKey + 1, "name1", "desc1", "ow1",false, null));
	}

	@Test
	public void serviceLevelTrasactionDemo() throws MMException {
		DemoTrasaction dt = ctx.getBean(DemoTrasaction.class);
		dt.saveSuccessOperation(configCollPrimaryKey +1, "sgr1");
	}

	@Test(expected = DataIntegrityViolationException.class)
	public void serviceLevelTrasactionFailureAndRollback() throws MMException {
		DemoTrasaction dt = ctx.getBean(DemoTrasaction.class);
		dt.saveSuccessOperation(configCollPrimaryKey + 1, "sgr2");
	}

	@Test
	public void ttt() throws BeansException, MMException{
		DemoTrasaction dt = ctx.getBean(DemoTrasaction.class);
		dt.saveSuccessOperation(configCollPrimaryKey + 1, "sgr2");
	}
	
	@After
	public void tearDown() throws Exception {

	}

	@AfterClass
	public static void expungeTable() throws Exception {
		System.out.println("expunging");
		DaoTestHelperUtils.deleteAll(hibernateTemplate, ConfigCollectionDTO.class, ServerGroupDTO.class);
	}
}
