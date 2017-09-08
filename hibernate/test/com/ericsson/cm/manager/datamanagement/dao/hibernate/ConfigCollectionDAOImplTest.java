package com.ericsson.cm.manager.datamanagement.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.ericsson.cm.common.exception.MMException;
import com.ericsson.cm.manager.datamanagement.dao.ConfigCollectionDAO;
import com.ericsson.cm.manager.datamanagement.dto.ConfigCollectionDTO;

public class ConfigCollectionDAOImplTest {

	@Mock
	private HibernateTemplate hibernateTemplate;
	@Mock
	private ConfigCollectionDTO mockedConfigCollectionDTO;

	private ConfigCollectionDAO configCollectionDAO;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		configCollectionDAO = new ConfigCollectionDAOImpl();

		((ConfigCollectionDAOImpl) configCollectionDAO)
				.setHibernateTemplate(hibernateTemplate);
	}

	@Test
	public void testCreateConfigCollectionSuccess() throws MMException {
		configCollectionDAO.createConfigCollection(mockedConfigCollectionDTO);
		Mockito.verify(hibernateTemplate, Mockito.times(1)).save(Mockito.any());
	}

	@Test(expected = Exception.class)
	public void testCreateConfigCollectionFailure() throws MMException {
		Mockito.when(hibernateTemplate.save(Mockito.any())).thenThrow(
				Exception.class);
		configCollectionDAO.createConfigCollection(mockedConfigCollectionDTO);
		Mockito.verify(hibernateTemplate, Mockito.times(1)).save(Mockito.any());
	}

	@Test
	public void testUpdateConfigCollectionSuccess() throws MMException {
		configCollectionDAO.updateConfigCollection(mockedConfigCollectionDTO);
		Mockito.verify(hibernateTemplate).update(Mockito.any());
	}

	@Test(expected = RuntimeException.class)
	public void testUpdateConfigCollectionFailure() throws MMException {
		Mockito.doThrow(new RuntimeException()).when(hibernateTemplate)
				.update(Mockito.any());
		configCollectionDAO.updateConfigCollection(mockedConfigCollectionDTO);
	}

	@Test
	public void testDeleteConfigCollectionTemporarilySuccess()
			throws MMException {
		Mockito.when(
				hibernateTemplate.load(Mockito.eq(ConfigCollectionDTO.class),
						Mockito.anyString())).thenReturn(
				mockedConfigCollectionDTO);

		Mockito.when(mockedConfigCollectionDTO.isDeleted()).thenReturn(false);

		configCollectionDAO.deleteConfigCollection(1);
		Mockito.verify(mockedConfigCollectionDTO).setDeleted(true);
	}

	@Test
	public void testRestoreDeleteConfigCollection() throws MMException {
		Mockito.when(mockedConfigCollectionDTO.isDeleted()).thenReturn(true);
		configCollectionDAO.updateConfigCollection(mockedConfigCollectionDTO);
		Mockito.verify(mockedConfigCollectionDTO).setDeleted(false);
		Mockito.verify(hibernateTemplate).update(mockedConfigCollectionDTO);
	}

	@Test
	public void testDeleteConfigCollectionPermanentlySuccess()
			throws MMException {
		Mockito.when(
				hibernateTemplate.load(Mockito.eq(ConfigCollectionDTO.class),
						Mockito.anyString())).thenReturn(
				mockedConfigCollectionDTO);

		Mockito.when(mockedConfigCollectionDTO.isDeleted()).thenReturn(true);

		configCollectionDAO.deleteConfigCollection(1);
		Mockito.verify(hibernateTemplate).delete(mockedConfigCollectionDTO);
	}

	@Test
	public void testGetConfigCollectionbyCollIdSuccess() throws MMException {
		Mockito.when(
				hibernateTemplate.load(Mockito.eq(ConfigCollectionDTO.class),
						Mockito.anyString())).thenReturn(
				mockedConfigCollectionDTO);
		ConfigCollectionDTO expectedCollectionDTO = configCollectionDAO
				.getConfigCollection(1);
		Assert.assertSame(expectedCollectionDTO, mockedConfigCollectionDTO);
	}

	@Test(expected = Exception.class)
	public void testGetConfigCollectionbyCollIdFailure() throws MMException {
		Mockito.when(
				hibernateTemplate.load(Mockito.eq(ConfigCollectionDTO.class),
						Mockito.anyString())).thenThrow(Exception.class);
		ConfigCollectionDTO expectedCollectionDTO = configCollectionDAO
				.getConfigCollection(1);
	}

	@Test
	public void testGetAllConfigCollection() throws MMException {
		Mockito.when(hibernateTemplate.loadAll(ConfigCollectionDTO.class))
				.thenReturn(new ArrayList<ConfigCollectionDTO>());
		List<ConfigCollectionDTO> configCollectionDTOs = configCollectionDAO
				.getAllConfigCollections();
		Mockito.verify(hibernateTemplate).loadAll(ConfigCollectionDTO.class);
		Assert.assertEquals(0, configCollectionDTOs.size());
	}
}