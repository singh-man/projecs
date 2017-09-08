package com.ericsson.cm.manager.datamanagement.dto;

import java.util.List;


public class FEServerDTO extends AbstractServerDTO<FEServerDTO> {

	
	public FEServerDTO() {}

	public FEServerDTO(String description, String name, String ip,
			String hostName, Long port, Boolean reserved, String ownerGroup,
			ServerGroupDTO serverGroupDTO,
			List<ProvisionServerDTO<FEServerDTO>> provisionServerDTOs,
			String instanceInfo) {
		super(description, name, ip, hostName, port, reserved, ownerGroup,
				serverGroupDTO, provisionServerDTOs, instanceInfo);
	}

	public FEServerDTO(String description, String name, String ip,
			String hostName, Long port, Boolean reserved, String ownerGroup,
			ServerGroupDTO serverGroupDTO,
			List<ProvisionServerDTO<FEServerDTO>> provisionServerDTOs) {
		super(description, name, ip, hostName, port, reserved, ownerGroup,
				serverGroupDTO, provisionServerDTOs);
	}

	public FEServerDTO(String description, String name, String ip,
			String hostName, Long port, Boolean reserved, String ownerGroup,
			ServerGroupDTO serverGroupDTO) {
		super(description, name, ip, hostName, port, reserved, ownerGroup,
				serverGroupDTO);
	}

	




}
