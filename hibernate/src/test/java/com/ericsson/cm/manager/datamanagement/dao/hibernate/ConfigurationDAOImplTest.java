package com.ericsson.cm.manager.datamanagement.dao.hibernate;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.never;

import org.mockito.MockitoAnnotations;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.ericsson.cm.common.exception.MMException;
import com.ericsson.cm.manager.datamanagement.dao.ConfigurationDAO;
import com.ericsson.cm.manager.datamanagement.dto.ConfigCollectionDTO;
import com.ericsson.cm.manager.datamanagement.dto.ConfigurationDTO;

public class ConfigurationDAOImplTest {

	@Mock private HibernateTemplate hibernateTemplate;
	@Mock private ConfigurationDTO mockedConfigurationDTO;
	@Mock private ConfigCollectionDTO mockedConfigCollectionDTO;
	@Mock private Set<ConfigurationDTO> mockedConfigurationDTOs;

	private ConfigurationDAO configurationDAO;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		configurationDAO = new ConfigurationDAOImpl();

		((ConfigurationDAOImpl) configurationDAO).setHibernateTemplate(hibernateTemplate);
	}

	@Test
	public void testCreateConfigurationSuccess() throws MMException {
		configurationDAO.createConfiguration(mockedConfigurationDTO);
		verify(hibernateTemplate, times(1)).save(any());
	}

	@Test(expected = Exception.class)
	public void testCreateConfigurationFailure() throws MMException {
		when(hibernateTemplate.save(any())).thenThrow(Exception.class);
		configurationDAO.createConfiguration(mockedConfigurationDTO);
		verify(hibernateTemplate, times(1)).save(any());
	}

	@Test
	public void testUpdateConfigurationSuccess() throws MMException {
		configurationDAO.updateConfiguration(mockedConfigurationDTO);
		verify(hibernateTemplate, times(1)).update(any());
	}

	@Test(expected = RuntimeException.class)
	public void testUpdateConfigurationFailure() throws MMException {
		doThrow(new RuntimeException()).when(hibernateTemplate).update(any());
		configurationDAO.updateConfiguration(mockedConfigurationDTO);
	}

	@Test
	public void testDeleteConfigurationSuccess() throws MMException {
		when(hibernateTemplate.load(eq(ConfigurationDTO.class), anyLong()))
				.thenReturn(mockedConfigurationDTO);

		when(mockedConfigurationDTO.getConfigCollectDTO()).thenReturn(
				mockedConfigCollectionDTO);

		when(mockedConfigCollectionDTO.getConfigurationDTOs()).thenReturn(
				mockedConfigurationDTOs);

		configurationDAO.deteleConfiguration(1l);
		verify(mockedConfigurationDTOs).remove(mockedConfigurationDTO);
	}

	@Test(expected = Exception.class)
	public void testDeleteConfigurationFailure() throws MMException {
		when(hibernateTemplate.load(eq(ConfigurationDTO.class), anyLong()))
				.thenReturn(mockedConfigurationDTO);

		when(mockedConfigurationDTO.getConfigCollectDTO()).thenThrow(
				Exception.class);
		configurationDAO.deteleConfiguration(1l);
	}

	@Test
	public void testGetConfigurationbyConfIdSuccess() throws MMException {
		configurationDAO.getConfiguration(Mockito.eq(Mockito.anyLong()));
		verify(hibernateTemplate, times(1)).load(eq(ConfigurationDTO.class),
				anyLong());
	}

	@Test(expected = Exception.class)
	public void testGetConfigurationbyConfIdFailure() throws MMException {
		Mockito.when(hibernateTemplate.load(eq(ConfigurationDTO.class), anyLong()))
				.thenThrow(Exception.class);
		configurationDAO.getConfiguration(1l);
	}

	@Test
	public void testGetAllConfigurationSuccess() throws MMException {
		when(hibernateTemplate.load(eq(ConfigCollectionDTO.class), anyString()))
				.thenReturn(mockedConfigCollectionDTO);

		when(mockedConfigCollectionDTO.getConfigurationDTOs()).thenReturn(
				new HashSet<ConfigurationDTO>());

		List<ConfigurationDTO> expectedCollectionDTOs = configurationDAO
				.getAllConfigurations(1);
		Assert.assertNotSame(expectedCollectionDTOs, mockedConfigurationDTOs);
	}
}