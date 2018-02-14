package com.ericsson.cm.manager.datamanagement.dao.hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.ericsson.cm.common.exception.MMException;
import com.ericsson.cm.manager.datamanagement.dao.ConfigVersionDAO;
import com.ericsson.cm.manager.datamanagement.dto.ConfigVersionDTO;
import com.ericsson.cm.manager.datamanagement.dto.ConfigVersionCompositeKey;
import com.ericsson.cm.manager.datamanagement.dto.ConfigurationDTO;

public class ConfigVersionDAOImplTest {

	@Mock private HibernateTemplate hibernateTemplate;
	@Mock private ConfigVersionDTO mockedConfigVersionDTO;

	ConfigVersionDAO configVersionDAO;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		configVersionDAO = new ConfigVersionDAOImpl();

		((ConfigVersionDAOImpl) configVersionDAO).setHibernateTemplate(hibernateTemplate);
	}

	@Test
	public void testCreateConfigVersionSuccess() throws MMException {
		configVersionDAO.createConfigVersion(mockedConfigVersionDTO);
		Mockito.verify(hibernateTemplate, Mockito.times(1)).save(Mockito.any());
	}

	@Test(expected = Exception.class)
	public void testCreateConfigVersionFailure() throws MMException {
		Mockito.when(hibernateTemplate.save(Mockito.any())).thenThrow(Exception.class);
		configVersionDAO.createConfigVersion(mockedConfigVersionDTO);
		Mockito.verify(hibernateTemplate, Mockito.times(1)).save(Mockito.any());
	}

	@Test
	public void testUpdateConfigVersionSuccess() throws MMException {
		configVersionDAO.updateConfigVersion(mockedConfigVersionDTO);
		Mockito.verify(hibernateTemplate).update(Mockito.any());
	}

	@Test(expected=RuntimeException.class)
	public void testUpdateConfigVersionFailure() throws MMException {
		Mockito.doThrow(new RuntimeException()).when(hibernateTemplate).update(Mockito.any());
		configVersionDAO.updateConfigVersion(mockedConfigVersionDTO);
	}

	@Test(expected=RuntimeException.class)
	public void testDeleteConfigVersionFailure() throws MMException {
		Mockito.when(hibernateTemplate.load(Mockito.eq(ConfigVersionDTO.class), 
				Mockito.any(ConfigVersionCompositeKey.class))).
				thenReturn(mockedConfigVersionDTO);
		Mockito.doThrow(new RuntimeException()).when(hibernateTemplate).delete(Mockito.any(ConfigVersionDTO.class));
		configVersionDAO.deleteConfigVersion(1l, 1l);
	}
	
	@Test
	public void testGetConfigVersionByConfigIdAndVersionId() throws MMException {
		configVersionDAO.getConfigVersion(1l, 2l);
		Mockito.verify(hibernateTemplate, Mockito.times(1)).load(Mockito.eq(ConfigVersionDTO.class), Mockito.anyLong());
	}
	
	@Test
	public void testGetAllConfigVersionSuccess() throws MMException {
		ArrayList<ConfigVersionDTO> actualValue = new ArrayList<ConfigVersionDTO>();
		Mockito.when(hibernateTemplate.
				findByNamedQueryAndNamedParam(Mockito.anyString(), Mockito.anyString(), Mockito.anyLong())).
				thenReturn(actualValue);
		List<ConfigVersionDTO> l = configVersionDAO.getAllConfigVersions(1l);
		Assert.assertSame(l, actualValue);
		
	}
}