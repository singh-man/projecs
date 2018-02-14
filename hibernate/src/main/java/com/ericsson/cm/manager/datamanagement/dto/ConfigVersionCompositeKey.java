package com.ericsson.cm.manager.datamanagement.dto;

import java.io.Serializable;

/**
 * This class represents a composite key for identifying a configuration version
 * in Data Management.
 */
public class ConfigVersionCompositeKey implements Serializable {

	private static final long serialVersionUID = 1L;
	private ConfigurationDTO configurationDTO;
	private Long versionId;

	public ConfigVersionCompositeKey() {
	}

	public ConfigVersionCompositeKey(ConfigurationDTO configurationDTO,
			Long versionId) {
		super();
		this.configurationDTO = configurationDTO;
		this.versionId = versionId;
	}

	/**
	 * @return version id for the configuration version
	 */
	public Long getVersionId() {
		return versionId;
	}

	/**
	 * Set the version id for the configuration version
	 * 
	 * @param versionId
	 */
	public void setVersionId(Long versionId) {
		this.versionId = versionId;
	}

	/**
	 * @return the configuration to which the configuration version belongs.
	 */
	public ConfigurationDTO getConfigurationDTO() {
		return configurationDTO;
	}

	/**
	 * Set the configuration to which the configuration version belongs.
	 * 
	 * @param configurationDTO
	 */
	public void setConfigurationDTO(ConfigurationDTO configurationDTO) {
		this.configurationDTO = configurationDTO;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((getConfigurationDTO() == null) ? 0 : configurationDTO
						.hashCode());
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
		ConfigVersionCompositeKey other = (ConfigVersionCompositeKey) obj;
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
