package com.ericsson.cm.manager.datamanagement.dto;

import java.util.List;


public class OnlineServerDTO extends AbstractServerDTO<OnlineServerDTO> {

	
	public OnlineServerDTO() {}

	public OnlineServerDTO(String description, String name, String ip,
			String hostName, Long port, Boolean reserved, String ownerGroup,
			ServerGroupDTO serverGroupDTO,
			List<ProvisionServerDTO<OnlineServerDTO>> provisionServerDTOs,
			String instanceInfo) {
		super(description, name, ip, hostName, port, reserved, ownerGroup,
				serverGroupDTO, provisionServerDTOs, instanceInfo);
	}

	public OnlineServerDTO(String description, String name, String ip,
			String hostName, Long port, Boolean reserved, String ownerGroup,
			ServerGroupDTO serverGroupDTO,
			List<ProvisionServerDTO<OnlineServerDTO>> provisionServerDTOs) {
		super(description, name, ip, hostName, port, reserved, ownerGroup,
				serverGroupDTO, provisionServerDTOs);
	}

	public OnlineServerDTO(String description, String name, String ip,
			String hostName, Long port, Boolean reserved, String ownerGroup,
			ServerGroupDTO serverGroupDTO) {
		super(description, name, ip, hostName, port, reserved, ownerGroup,
				serverGroupDTO);
	}

	

	
	

}
