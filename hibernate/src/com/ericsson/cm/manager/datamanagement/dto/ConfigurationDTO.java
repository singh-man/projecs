package com.ericsson.cm.manager.datamanagement.dto;

import java.util.Set;

import com.ericsson.cm.common.constant.ConfigType;

/**
 * Data Transfer object for Configuration
 */
public class ConfigurationDTO {

	private Long configId;
	private ConfigCollectionDTO configCollectDTO;
	private String name;
	private ConfigType configType;
	private Boolean checkedOut;
	private Boolean deleted;

	private Set<ConfigVersionDTO> configVersionDTOs;

	public ConfigurationDTO() {
		super();
	}

	public ConfigurationDTO(Long configId,
			ConfigCollectionDTO configCollectDTO, String name,
			ConfigType configType, Boolean checkedOut, Boolean deleted,
			Set<ConfigVersionDTO> configVersionDTOs) {
		super();
		this.configId = configId;
		this.configCollectDTO = configCollectDTO;
		this.name = name;
		this.configType = configType;
		this.checkedOut = checkedOut;
		this.deleted = deleted;
		this.configVersionDTOs = configVersionDTOs;
	}

	/**
	 * @return Configuration Id for the configuration
	 */
	public Long getConfigId() {
		return configId;
	}

	/**
	 * Set configuration id for the configuration
	 * 
	 * @param configId
	 */
	public void setConfigId(Long configId) {
		this.configId = configId;
	}

	/**
	 * @return Configuration collection instance to which this configuration
	 *         belongs
	 */
	public ConfigCollectionDTO getConfigCollectDTO() {
		return configCollectDTO;
	}

	/**
	 * Set the configuration collection instance to which this configuration
	 * belongs
	 * 
	 * @param configCollectDTO
	 */
	public void setConfigCollectDTO(ConfigCollectionDTO configCollectDTO) {
		this.configCollectDTO = configCollectDTO;
	}

	/**
	 * @return the name of this configuration
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the name of this configuration
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the type of this configuration, Online/Offline.
	 */
	public ConfigType getConfigType() {
		return configType;
	}

	/**
	 * Set the type of this configuration, Online/Offline.
	 * 
	 * @param configType
	 */
	public void setConfigType(ConfigType configType) {
		this.configType = configType;
	}

	/**
	 * @return true if any version of this configuration is checked out, false
	 *         otherwise
	 */
	public Boolean isCheckedOut() {
		return checkedOut;
	}

	/**
	 * Set the checkout flag for this configuration to mark if any of its
	 * version is checked out.
	 * 
	 * @param checkedOut
	 */
	public void setCheckedOut(Boolean checkedOut) {
		this.checkedOut = checkedOut;
	}

	/**
	 * @return true if this configuration is in deleted hierarchy, false
	 *         otherwise
	 */
	public Boolean isDeleted() {
		return deleted;
	}

	/**
	 * Set the deleted flag for this configuration to mark whether this
	 * configuration is in the deleted hierarchy.
	 * 
	 * @param deleted
	 */
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	/**
	 * @return collection of configuration versions for this configuration
	 */
	public Set<ConfigVersionDTO> getConfigVersionDTOs() {
		return configVersionDTOs;
	}

	/**
	 * Set the collection of configuration versions for this configuration
	 * 
	 * @param configVersionDTOs
	 */
	public void setConfigVersionDTOs(Set<ConfigVersionDTO> configVersionDTOs) {
		this.configVersionDTOs = configVersionDTOs;
	}
}