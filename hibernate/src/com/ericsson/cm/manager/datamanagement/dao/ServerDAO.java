package com.ericsson.cm.manager.datamanagement.dao;

import java.util.List;

import com.ericsson.cm.common.exception.MMException;
import com.ericsson.cm.manager.datamanagement.dto.AbstractServerDTO;
import com.ericsson.cm.manager.datamanagement.dto.FEServerDTO;
import com.ericsson.cm.manager.datamanagement.dto.OnlineServerDTO;

/**
 * @author emmhssh
 */
public interface ServerDAO {

	public <T extends AbstractServerDTO<T>> void createServer(T serverDTO) throws MMException;

	public void deleteServer(long id) throws MMException;

	public <T extends AbstractServerDTO<T>> void updateServer(T serverDTO) throws MMException;

	public FEServerDTO getFEServer(long id) throws MMException;
	
	public OnlineServerDTO getOnlineServer(long id) throws MMException;

	public List<FEServerDTO> getAllFEServers() throws MMException;

	public List<OnlineServerDTO> getAllOnlineServers() throws MMException;

	public <T extends AbstractServerDTO<T>> List<T> getAllServers(Class<T> glass) throws MMException;

	//public <T extends AbstractServerDTO<T>> List<T> getAllServers(Class<T> glass, long id) throws MMException;

}
