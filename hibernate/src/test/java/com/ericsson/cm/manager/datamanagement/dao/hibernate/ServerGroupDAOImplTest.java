package com.ericsson.cm.manager.datamanagement.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.LockMode;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.ericsson.cm.common.exception.MMException;
import com.ericsson.cm.manager.datamanagement.dao.ServerGroupDAO;
import com.ericsson.cm.manager.datamanagement.dto.ServerGroupDTO;

public class ServerGroupDAOImplTest {

	@Mock private HibernateTemplate hibernateTemplate;
	@Mock private ServerGroupDTO mockedServerGroupDTO;

	private ServerGroupDAO serverGroupDAO;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		serverGroupDAO = new ServerGroupDAOImpl();

		((ServerGroupDAOImpl) serverGroupDAO)
				.setHibernateTemplate(hibernateTemplate);
	}

	@Test
	public void testCreateServerGroupSuccess() throws MMException {
		serverGroupDAO.createServerGroup(mockedServerGroupDTO);
		Mockito.verify(hibernateTemplate, Mockito.times(1)).save(Mockito.any());
	}

	@Test(expected = Exception.class)
	public void testCreateServerGroupFailure() throws MMException {
		Mockito.when(hibernateTemplate.save(Mockito.any())).thenThrow(
				Exception.class);
		serverGroupDAO.createServerGroup(mockedServerGroupDTO);
		Mockito.verify(hibernateTemplate, Mockito.times(1)).save(Mockito.any());
	}

	@Test
	public void testUpdateServerGroupSuccess() throws MMException {
		serverGroupDAO.updateServerGroup(mockedServerGroupDTO);
		Mockito.verify(hibernateTemplate).update(Mockito.any());
	}

	@Test(expected = RuntimeException.class)
	public void testUpdateServerGroupFailure() throws MMException {
		Mockito.doThrow(new RuntimeException()).when(hibernateTemplate)
				.update(Mockito.any());
		serverGroupDAO.updateServerGroup(mockedServerGroupDTO);
	}

	@Test
	public void testDeleteServerGroup()
			throws MMException {
		serverGroupDAO.deleteServerGroup(mockedServerGroupDTO);
		Mockito.verify(hibernateTemplate).delete(mockedServerGroupDTO, LockMode.NONE);
	}

	@Test
	public void testGetServerGroupbyServerIdSuccess() throws MMException {
		Mockito.when(
				hibernateTemplate.load(Mockito.eq(ServerGroupDTO.class),
						Mockito.anyString())).thenReturn(
				mockedServerGroupDTO);
		ServerGroupDTO expectedCollectionDTO = serverGroupDAO
				.getServerGroup(1);
		Assert.assertSame(expectedCollectionDTO, mockedServerGroupDTO);
	}

	@Test(expected = Exception.class)
	public void testGetServerGroupbyServerIdFailure() throws MMException {
		Mockito.when(
				hibernateTemplate.load(Mockito.eq(ServerGroupDTO.class),
						Mockito.anyString())).thenThrow(Exception.class);
		ServerGroupDTO expectedCollectionDTO = serverGroupDAO
				.getServerGroup(1);
	}

	@Test
	public void testGetAllServerGroup() throws MMException {
		Mockito.when(hibernateTemplate.loadAll(ServerGroupDTO.class))
				.thenReturn(new ArrayList<ServerGroupDTO>());
		List<ServerGroupDTO> configCollectionDTOs = serverGroupDAO
				.getAllServerGroup();
		Mockito.verify(hibernateTemplate).loadAll(ServerGroupDTO.class);
		Assert.assertEquals(0, configCollectionDTOs.size());
	}
}