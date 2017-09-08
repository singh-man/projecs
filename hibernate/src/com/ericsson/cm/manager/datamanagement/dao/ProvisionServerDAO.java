package com.ericsson.cm.manager.datamanagement.dao;

import java.util.List;

import com.ericsson.cm.common.exception.MMException;
import com.ericsson.cm.manager.datamanagement.dto.AbstractServerDTO;
import com.ericsson.cm.manager.datamanagement.dto.ProvisionServerDTO;

public interface ProvisionServerDAO {
	
	public <T> void createProvisionServer(ProvisionServerDTO<? extends AbstractServerDTO<?>> provisionServerDTO) 
			throws MMException;

	public <T> void updateProvisionServer(ProvisionServerDTO<? extends AbstractServerDTO<?>> provisionServerDTO)
			throws MMException;

	public <T extends AbstractServerDTO<T>> void deleteProvisionServer(long collId, long configId, long versionId, long serverId) throws MMException;

	public <T> void deleteProvisionServer(ProvisionServerDTO<? extends AbstractServerDTO<?>> provisionServerDTO);
	
	@SuppressWarnings("rawtypes")
	public List<ProvisionServerDTO> getAllProvisionServers() throws MMException;

	public <T extends AbstractServerDTO<T>> ProvisionServerDTO<T> getProvisionServer(long collId, long configId,
																					 long versionId, long serverId) throws MMException;

	

}
