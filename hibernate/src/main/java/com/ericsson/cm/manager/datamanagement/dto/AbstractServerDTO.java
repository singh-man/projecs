package com.ericsson.cm.manager.datamanagement.dto;

import java.util.List;

import com.ericsson.cm.common.servermanagement.ServerState;
import com.ericsson.cm.manager.datamanagement.Ignore;

/**
 * Base class for FE and Online Server
 * 
 * @author emmhssh
 *
 */
public abstract class AbstractServerDTO<T extends AbstractServerDTO<T>> extends VersionedEntity {

	private Long id;
	private String description;
	private String name;
	private String ip;
	private String hostName;
	private Long port;
	private Boolean reserved;

	private String ownerGroup;

	private ServerGroupDTO serverGroupDTO;

	private List<ProvisionServerDTO<T>> provisionServerDTOs;

	// Details of Online/Offline configurations
	private String instanceInfo;

	// Not a DB property
	private ServerState serverState = ServerState.SHUTDOWN;

	public AbstractServerDTO(String description, String name, String ip, String hostName, Long port, Boolean reserved,
			String ownerGroup, ServerGroupDTO serverGroupDTO, List<ProvisionServerDTO<T>> provisionServerDTOs,
			String instanceInfo) {
		this(description, name, ip, hostName, port, reserved, ownerGroup, serverGroupDTO, provisionServerDTOs);
		this.instanceInfo = instanceInfo;
	}

	public AbstractServerDTO(String description, String name, String ip, String hostName, Long port, Boolean reserved,
			String ownerGroup, ServerGroupDTO serverGroupDTO) {
		super();
		this.description = description;
		this.name = name;
		this.ip = ip;
		this.hostName = hostName;
		this.port = port;
		this.reserved = reserved;
		this.ownerGroup = ownerGroup;
		this.serverGroupDTO = serverGroupDTO;
	}

	public AbstractServerDTO(String description, String name, String ip, String hostName, Long port, Boolean reserved,
			String ownerGroup, ServerGroupDTO serverGroupDTO, List<ProvisionServerDTO<T>> provisionServerDTOs) {
		this(description, name, ip, hostName, port, reserved, ownerGroup, serverGroupDTO);
		this.provisionServerDTOs = provisionServerDTOs;
	}

	public AbstractServerDTO() {
	}

	public Long getId() {
		return id;
	}

	@Ignore
	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public Long getPort() {
		return port;
	}

	public void setPort(Long port) {
		this.port = port;
	}

	public Boolean getReserved() {
		return reserved;
	}

	public void setReserved(Boolean reserved) {
		this.reserved = reserved;
	}

	public String getOwnerGroup() {
		return ownerGroup;
	}

	public void setOwnerGroup(String ownerGroup) {
		this.ownerGroup = ownerGroup;
	}

	public ServerGroupDTO getServerGroupDTO() {
		return serverGroupDTO;
	}

	public void setServerGroupDTO(ServerGroupDTO serverGroupDTO) {
		this.serverGroupDTO = serverGroupDTO;
	}

	public List<ProvisionServerDTO<T>> getProvisionServerDTOs() {
		return provisionServerDTOs;
	}

	public void setProvisionServerDTOs(List<ProvisionServerDTO<T>> provisionServerDTOs) {
		this.provisionServerDTOs = provisionServerDTOs;
	}

	public String getInstanceInfo() {
		return instanceInfo;
	}

	public void setInstanceInfo(String instanceInfo) {
		this.instanceInfo = instanceInfo;
	}

}
