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

import com.ericsson.cm.manager.datamanagement.dto.AbstractServerDTO;
import com.ericsson.cm.manager.datamanagement.dto.ConfigCollectionDTO;
import com.ericsson.cm.manager.datamanagement.dto.ConfigVersionDTO;
import com.ericsson.cm.manager.datamanagement.dto.ConfigurationDTO;
import com.ericsson.cm.manager.datamanagement.dto.FEServerDTO;
import com.ericsson.cm.manager.datamanagement.dto.OnlineServerDTO;
import com.ericsson.cm.manager.datamanagement.dto.ProvisionServerDTO;
import com.ericsson.cm.manager.datamanagement.dto.ServerGroupDTO;

public class ServerAndProviTest_1 {

	Logger log = Logger.getLogger(ServerAndProviTest_1.class.getName());

	static ApplicationContext ctx;
	static HibernateTemplate hibernateTemplate;
	final static Long primaryKey = (long) Math.abs((Math.random() * 1000));

	@BeforeClass
	public static void oneTimeSetUp() {
		ctx = DaoTestHelperUtils.getContext();
		hibernateTemplate = ctx.getBean(HibernateTemplate.class);
		
		ConfigCollectionDTO configCollectionDTO = hibernateTemplate.load(ConfigCollectionDTO.class, 1l);
		ConfigurationDTO configurationDTO = new ArrayList<ConfigurationDTO>(configCollectionDTO.getConfigurationDTOs()).get(0);
		ConfigVersionDTO configVersionDTO = new ArrayList<ConfigVersionDTO>(configurationDTO.getConfigVersionDTOs()).get(0);
		
		ServerGroupDTO serverGroupDTO = new ServerGroupDTO("sgr5", new ArrayList<AbstractServerDTO>());
		
		FEServerDTO feServerDTO = new FEServerDTO("fDesc", "fName", "10.10.10.10", "fHName", 8090, false, "fow",serverGroupDTO);
		feServerDTO.setProvisionServerDTOs(new ArrayList<ProvisionServerDTO>());
		
		OnlineServerDTO onlineServerDTO = new OnlineServerDTO("oDesc", "oName", "10.10.10.10", "oHName", 8080, false, "oow", serverGroupDTO);
		onlineServerDTO.setProvisionServerDTOs(new ArrayList<ProvisionServerDTO>());
		
		//ProvisionServerDTO provisionServerDTO = new ProvisionServerDTO(configCollectionDTO, configVersionDTO, onlineServerDTO);
		ProvisionServerDTO provisionServerDTO = new ProvisionServerDTO(configCollectionDTO, configVersionDTO, feServerDTO);
		
		//onlineServerDTO.getProvisionServerDTOs().add(provisionServerDTO);
		feServerDTO.getProvisionServerDTOs().add(provisionServerDTO);
		
		serverGroupDTO.getServerDTOs().add(feServerDTO);
		serverGroupDTO.getServerDTOs().add(onlineServerDTO);
		hibernateTemplate.save(serverGroupDTO);
	}

	@Before
	public void setUp() {
		// configCollectionDAO = ctx.getBean(ConfigCollectionDAO.class);
	}

	@After
	public void tearDown() throws Exception {

	}

	@AfterClass
	public static void expungeTable() throws Exception {
		System.out.println("expunging");
	}
	
	@Test
	public void t() {
		
	}

}
