package com.ericsson.cm.manager.datamanagement.dto;

import java.io.Serializable;

public class ConfigVersionId implements Serializable {

	private static final long serialVersionUID = 1L;
	private ConfigurationDTO configurationDTO;
	private Long versionId;

	public ConfigVersionId() {
	}

	public ConfigVersionId(ConfigurationDTO configurationDTO, Long versionId) {
		super();
		this.configurationDTO = configurationDTO;
		this.versionId = versionId;
	}

	public Long getVersionId() {
		return versionId;
	}

	public void setVersionId(Long versionId) {
		this.versionId = versionId;
	}

	public ConfigurationDTO getConfigurationDTO() {
		return configurationDTO;
	}

	public void setConfigurationDTO(ConfigurationDTO configurationDTO) {
		this.configurationDTO = configurationDTO;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((getConfigurationDTO() == null) ? 0 : configurationDTO.hashCode());
		result = prime * result
				+ ((versionId == null) ? 0 : versionId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConfigVersionId other = (ConfigVersionId) obj;
		if (getConfigurationDTO() == null) {
			if (other.getConfigurationDTO() != null)
				return false;
		} else if (!getConfigurationDTO().equals(other.getConfigurationDTO()))
			return false;
		if (versionId == null) {
			if (other.versionId != null)
				return false;
		} else if (!versionId.equals(other.versionId))
			return false;
		return true;
	}

}
