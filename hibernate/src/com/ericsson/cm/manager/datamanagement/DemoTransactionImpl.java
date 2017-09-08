package com.ericsson.cm.manager.datamanagement;

import com.ericsson.cm.common.exception.MMException;
import com.ericsson.cm.manager.datamanagement.dao.ConfigCollectionDAO;
import com.ericsson.cm.manager.datamanagement.dao.ServerDAO;
import com.ericsson.cm.manager.datamanagement.dao.ServerGroupDAO;
import com.ericsson.cm.manager.datamanagement.dto.AbstractServerDTO;
import com.ericsson.cm.manager.datamanagement.dto.ConfigCollectionDTO;
import com.ericsson.cm.manager.datamanagement.dto.ServerGroupDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@Transactional(propagation=Propagation.REQUIRED, readOnly=false, isolation=Isolation.DEFAULT)
public class DemoTransactionImpl implements DemoTrasaction {

	@Autowired
	private ServerDAO serverDAO;
	@Autowired
	private ConfigCollectionDAO configCollectionDAO;
	@Autowired
	private ServerGroupDAO serverGroupDAO;

	@Override
	public boolean saveSuccessOperation(long collId, String serverGroupName) throws MMException {
		ServerGroupDTO serverGroupDTO = new ServerGroupDTO(serverGroupName, new ArrayList<AbstractServerDTO<?>>());
		serverGroupDAO.createServerGroup(serverGroupDTO);

		ConfigCollectionDTO configCollectionDTO = new ConfigCollectionDTO(
				collId,"name","desc","fas",false, null);
		
		configCollectionDAO.createConfigCollection(configCollectionDTO);
		
		return false;
	}
	
}

