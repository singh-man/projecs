package com.ericsson.cm.manager.datamanagement.dao.hibernate;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import junit.framework.Assert;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.ericsson.cm.common.constant.ConfigType;
import com.ericsson.cm.manager.datamanagement.dao.ConfigCollectionDAO;
import com.ericsson.cm.manager.datamanagement.dto.ConfigCollectionDTO;
import com.ericsson.cm.manager.datamanagement.dto.ConfigurationDTO;
import com.sybase.jdbc3.a.b.ac;

public class ConfigCollectionDAOImplTest_1 {

	Logger log = Logger.getLogger(ConfigCollectionDAOImplTest_1.class.getName());

	static ApplicationContext ctx;
	static HibernateTemplate hibernateTemplate;
	final static Long primaryKey = (long) Math.abs((Math.random()*1000));

	private ConfigCollectionDAO configCollectionDAO;

	@BeforeClass
	public static void oneTimeSetUp() {
		ctx = DaoTestHelperUtils.getContext();
		hibernateTemplate = ctx.getBean(HibernateTemplate.class);
		hibernateTemplate.save(new ConfigCollectionDTO(
				primaryKey, "name", "desc", "ow",false, null));
	}

	@Before
	public void setUp() {
		configCollectionDAO = ctx.getBean(ConfigCollectionDAO.class);
	}

	@Test
	public void testCreateConfigCollectionSuccess() throws Exception {
		ConfigCollectionDTO configCollectionDTO = new ConfigCollectionDTO(
				(long) Math.abs((Math.random()*1000)), "name", "desc", "ow",false, null);
		configCollectionDAO.createConfigCollection(configCollectionDTO);

		ConfigCollectionDTO expectedConfigCollectionDTO = hibernateTemplate.get(
				ConfigCollectionDTO.class, configCollectionDTO.getCollectionId());

		Assert.assertEquals(configCollectionDTO.getCollectionId(),
				expectedConfigCollectionDTO.getCollectionId());
	}

	/*
	 * Child primary key is native; 
	 * While doing cascade save parent; don't set the child primary key
	 * or else hibernate will try to execute the update stmt on child rathen than insert 
	 */
	@Test
	public void testCreateConfigCollectionCascadeSuccess() throws Exception  {
		ConfigurationDTO configurationDTO = new ConfigurationDTO(
				null, null, "name", ConfigType.ONLINE, false, false, null);

		ConfigCollectionDTO configCollectionDTO = new ConfigCollectionDTO(
				(long) Math.abs((Math.random()*1000)), "name", "desc", "ow", false, new HashSet<ConfigurationDTO>());
		configCollectionDTO.getConfigurationDTOs().add(configurationDTO);

		configurationDTO.setConfigCollectDTO(configCollectionDTO);

		configCollectionDAO.createConfigCollection(configCollectionDTO);

		ConfigCollectionDTO expectedConfigCollectionDTO = hibernateTemplate.get(
				ConfigCollectionDTO.class, configCollectionDTO.getCollectionId());

		Assert.assertEquals(configCollectionDTO.getCollectionId(),
				expectedConfigCollectionDTO.getCollectionId());

		Set<ConfigurationDTO> configurationDTOs = expectedConfigCollectionDTO.getConfigurationDTOs();
		for(ConfigurationDTO cd : configurationDTOs) {
			Assert.assertEquals(configurationDTO.getName(),
					cd.getName());
		}

	}

	@Test(expected = DataIntegrityViolationException.class)
	public void testCreateConfigCollectionFailurePrimarykeyViolation() throws Exception {
		List<ConfigCollectionDTO> configCollectionDTOs = hibernateTemplate
				.loadAll(ConfigCollectionDTO.class);

		configCollectionDAO.createConfigCollection(configCollectionDTOs.get(0));
	}

	@Test
	public void testUpdateConfigCollectionSuccess() throws Exception {
		ConfigCollectionDTO actualConfigColl = new ConfigCollectionDTO(
				primaryKey, "name1", "desc1", "ow1",false, null);
		configCollectionDAO.updateConfigCollection(actualConfigColl);

		ConfigCollectionDTO expectedConfigColl = hibernateTemplate.load(ConfigCollectionDTO.class, primaryKey);

		Assert.assertEquals(actualConfigColl.getDescription(), expectedConfigColl.getDescription());
	}

	@Test(expected=HibernateOptimisticLockingFailureException.class)
	public void testUpdateConfigCollectionFailure() throws Exception {
		ConfigCollectionDTO actualConfigColl = new ConfigCollectionDTO(
				primaryKey + 1, "name1", "desc1", "ow1",false, null);
		configCollectionDAO.updateConfigCollection(actualConfigColl);
	}

	@Test
	public void testDeleteConfigCollectionTemporarilySuccess() throws Exception {
		configCollectionDAO.deleteConfigCollection(primaryKey);
		ConfigCollectionDTO actualConfigCollectionDTO = hibernateTemplate.load(ConfigCollectionDTO.class, primaryKey);
		Assert.assertEquals(true, actualConfigCollectionDTO.isDeleted().booleanValue());
	}

	@Test(expected=HibernateObjectRetrievalFailureException.class)
	public void testDeleteConfigCollectionPermanentlySuccess() throws Exception {
		ConfigCollectionDTO configCollectionDTO = new ConfigCollectionDTO(
				primaryKey + 1, "name", "desc", "ow",false, null);
		configCollectionDTO.setDeleted(true);
		hibernateTemplate.save(configCollectionDTO);

		configCollectionDAO.deleteConfigCollection(primaryKey + 1);

		hibernateTemplate.load(ConfigCollectionDTO.class, primaryKey + 1);
	}

	@Test
	public void testGetAllConfigCollection() throws Exception {
		List<ConfigCollectionDTO> actualConfigCollectionDTOs = configCollectionDAO.getAllConfigCollections();
		List<ConfigCollectionDTO> expectedConfigCollectionDTOs = hibernateTemplate.loadAll(ConfigCollectionDTO.class);
		log.info("Number of rows in table : " + actualConfigCollectionDTOs.size());

		Assert.assertEquals(actualConfigCollectionDTOs.size(), expectedConfigCollectionDTOs.size());
		Assert.assertEquals(actualConfigCollectionDTOs.get(0).getCollectionId(), expectedConfigCollectionDTOs.get(0).getCollectionId());
		//Assert.assertNotSame(actualConfigCollectionDTOs.get(1).getCollectionId(), expectedConfigCollectionDTOs.get(0).getCollectionId());
	}

	@After
	public void tearDown() throws Exception {

	}

	@AfterClass
	public static void expungeTable() throws Exception {
		System.out.println();
		DaoTestHelperUtils_1.deleteAll(hibernateTemplate, ConfigCollectionDTO.class, ConfigurationDTO.class);
	}

}
