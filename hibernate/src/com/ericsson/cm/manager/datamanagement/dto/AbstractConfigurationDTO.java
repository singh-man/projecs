package com.ericsson.cm.manager.datamanagement.dto;

import java.util.Set;

public class AbstractConfigurationDTO {

	private int configId;
	private ConfigCollectionDTO configCollectDTO;
	private String name;
	private boolean checkedOut;
	private boolean deleted;

	private Set<ConfigVersionDTO> configVersionDTOs;

	public int getConfigId() {
		return configId;
	}

	public void setConfigId(int configId) {
		this.configId = configId;
	}

	public ConfigCollectionDTO getConfigCollectDTO() {
		return configCollectDTO;
	}

	public void setConfigCollectDTO(ConfigCollectionDTO configCollectDTO) {
		this.configCollectDTO = configCollectDTO;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isCheckedOut() {
		return checkedOut;
	}

	public void setCheckedOut(boolean checkedOut) {
		this.checkedOut = checkedOut;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public Set<ConfigVersionDTO> getConfigVersionDTOs() {
		return configVersionDTOs;
	}

	public void setConfigVersionDTOs(Set<ConfigVersionDTO> configVersionDTOs) {
		this.configVersionDTOs = configVersionDTOs;
	}

	
}
