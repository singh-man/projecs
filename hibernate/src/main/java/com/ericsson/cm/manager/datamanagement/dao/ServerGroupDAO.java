package com.ericsson.cm.manager.datamanagement.dao;

import java.util.List;

import com.ericsson.cm.common.exception.MMException;
import com.ericsson.cm.manager.datamanagement.dto.ServerGroupDTO;

public interface ServerGroupDAO {
	

	public void createServerGroup(ServerGroupDTO serverGroupDTO) throws MMException;

	public void updateServerGroup(ServerGroupDTO serverGroupDTO) throws MMException;

	public void deleteServerGroup(ServerGroupDTO serverGroupDTO) throws MMException;
	
	public void deleteServerGroup(long id) throws MMException;

	//public void deleteAllServerGroup() throws MMException;

	public ServerGroupDTO getServerGroup(long id) throws MMException;

	public List<ServerGroupDTO> getAllServerGroup() throws MMException;
	
}
