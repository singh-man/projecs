package com.ericsson.cm.manager.datamanagement.dao.hibernate;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.ericsson.cm.common.exception.MMException;
import com.ericsson.cm.manager.datamanagement.dao.ProvisionServerDAO;
import com.ericsson.cm.manager.datamanagement.dao.ServerDAO;
import com.ericsson.cm.manager.datamanagement.dao.ServerGroupDAO;
import com.ericsson.cm.manager.datamanagement.dto.AbstractServerDTO;
import com.ericsson.cm.manager.datamanagement.dto.ConfigCollectionDTO;
import com.ericsson.cm.manager.datamanagement.dto.ConfigVersionDTO;
import com.ericsson.cm.manager.datamanagement.dto.ConfigurationDTO;
import com.ericsson.cm.manager.datamanagement.dto.FEServerDTO;
import com.ericsson.cm.manager.datamanagement.dto.OnlineServerDTO;
import com.ericsson.cm.manager.datamanagement.dto.ProvisionServerDTO;
import com.ericsson.cm.manager.datamanagement.dto.ServerGroupDTO;

public class ServerAndProvisionTest_1 {

	Logger log = Logger.getLogger(ServerAndProvisionTest_1.class.getName());

	static ApplicationContext ctx;
	static HibernateTemplate hibernateTemplate;
	final static Long primaryKey = (long) Math.abs((Math.random() * 1000));

	private ServerGroupDAO serverGroupDAO;
	private ServerDAO serverDAO;
	private ProvisionServerDAO provisionServerDAO;
	
	private ConfigCollectionDTO configCollectionDTO;
	private ConfigurationDTO configurationDTO;
	private ConfigVersionDTO configVersionDTO;
	
		@BeforeClass
	public static void oneTimeSetUp() {
		ctx = DaoTestHelperUtils_1.getContext();
		hibernateTemplate = ctx.getBean(HibernateTemplate.class);
	}

	@Before
	public void setUp() {
		serverGroupDAO = ctx.getBean(ServerGroupDAO.class);
		serverDAO = ctx.getBean(ServerDAO.class);
		provisionServerDAO = ctx.getBean(ProvisionServerDAO.class);
		
		configCollectionDTO = hibernateTemplate.load(ConfigCollectionDTO.class, 1l);
		configurationDTO = new ArrayList<ConfigurationDTO>(configCollectionDTO.getConfigurationDTOs()).get(0);
		configVersionDTO = new ArrayList<ConfigVersionDTO>(configurationDTO.getConfigVersionDTOs()).get(0);
	}

	@Test
	public void testServerGroupFE() throws Exception {

		ServerGroupDTO serverGroupDTO = new ServerGroupDTO("sgr5", new ArrayList<AbstractServerDTO<?>>());

		FEServerDTO feServerDTO = new FEServerDTO("fDesc", "fName", "fIp", "fHostName", 8080l,
				false, "fOW", serverGroupDTO, new ArrayList<ProvisionServerDTO<FEServerDTO>>(), "fCOnf");

		ProvisionServerDTO<FEServerDTO> provisionFEServerDTO = 
				new ProvisionServerDTO<FEServerDTO>(configCollectionDTO, configVersionDTO, feServerDTO);

		feServerDTO.getProvisionServerDTOs().add(provisionFEServerDTO);

		serverGroupDTO.getServerDTOs().add(feServerDTO);
		serverGroupDAO.createServerGroup(serverGroupDTO);
	}

	@Test
	public void testServerGroupOnline() throws Exception {

		ServerGroupDTO serverGroupDTO = new ServerGroupDTO("sgr5", new ArrayList<AbstractServerDTO<?>>());

		OnlineServerDTO onlineServerDTO = new OnlineServerDTO("oDesc", "oName", "oIp", "oHostName", 8080l,
				false, "oOW", serverGroupDTO, new ArrayList<ProvisionServerDTO<OnlineServerDTO>>(), "onlineCOnf");

		ProvisionServerDTO<OnlineServerDTO> provisionOnlineServerDTO = 
				new ProvisionServerDTO<OnlineServerDTO>(configCollectionDTO, configVersionDTO, onlineServerDTO);

		onlineServerDTO.getProvisionServerDTOs().add(provisionOnlineServerDTO);

		serverGroupDTO.getServerDTOs().add(onlineServerDTO);
		serverGroupDAO.createServerGroup(serverGroupDTO);
	}
	
	@Test
	public void testServerOnline() throws Exception {

		ServerGroupDTO serverGroupDTO = new ServerGroupDTO("sgr5", new ArrayList<AbstractServerDTO<?>>());

		OnlineServerDTO onlineServerDTO = new OnlineServerDTO("oDesc", "oName", "oIp", "oHostName", 8080l,
				false, "oOW", serverGroupDTO, new ArrayList<ProvisionServerDTO<OnlineServerDTO>>(), "onliConf");

		ProvisionServerDTO<OnlineServerDTO> provisionOnlineServerDTO = 
				new ProvisionServerDTO<OnlineServerDTO>(configCollectionDTO, configVersionDTO, onlineServerDTO);

		onlineServerDTO.getProvisionServerDTOs().add(provisionOnlineServerDTO);

		serverGroupDTO.getServerDTOs().add(onlineServerDTO);
		
		serverDAO.createServer(onlineServerDTO);
	}
	
	@Test
	public void testDeleteServerGroup() throws Exception {
		serverGroupDAO.deleteServerGroup(138);
	}
	
	@Test
	public void testDeleteServerOnline() throws Exception {
		serverDAO.deleteServer(147);
	}
	
	@Test
	public void testDeleteServerFE() throws Exception {
		serverDAO.deleteServer(145);
	}
	
	@Test
	public void testGetAllFEServer() throws Exception {
		serverDAO.getAllFEServers();
		serverDAO.getAllOnlineServers();
		serverDAO.getAllServers(null);
	}

	@Test
	public void testDeleteProvisionServer() {
		ProvisionServerDTO<?> provisionServerDTO = hibernateTemplate.loadAll(ProvisionServerDTO.class).get(0);
		provisionServerDAO.deleteProvisionServer(provisionServerDTO);
	}
	
	@Test
	public void testGetProvisionServer() throws Exception {
		provisionServerDAO.getProvisionServer(1, 1, 1, 151);
	}
	
	@Test
	public void testDeleteProvisionServerBy() throws Exception {
		provisionServerDAO.deleteProvisionServer(1, 1, 1, 151);
	}

	@Test
	public void t() throws Exception {
		AbstractServerDTO p = hibernateTemplate.load(AbstractServerDTO.class, 103);
		p.setDescription("new Set D");
		hibernateTemplate.update(p);

		hibernateTemplate.delete(hibernateTemplate.load(AbstractServerDTO.class, 105));
	}

	@After
	public void tearDown() throws Exception {

	}

	@AfterClass
	public static void expungeTable() throws Exception {
		System.out.println("expunging");
	}


}