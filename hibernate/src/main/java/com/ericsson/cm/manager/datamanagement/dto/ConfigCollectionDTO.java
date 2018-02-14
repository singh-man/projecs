package com.ericsson.cm.manager.datamanagement.dto;

import java.util.Set;

/**
 * Data Transfer object for Configuration collection
 */
public class ConfigCollectionDTO {

	private Long collectionId;
	private String name;
	private String description;
	private String ownerGroup;
	private Boolean deleted;

	private Set<ConfigurationDTO> configurationDTOs;

	public ConfigCollectionDTO() {
		super();
	}

	public ConfigCollectionDTO(Long collectionId, String name,
			String description, String ownerGroup, Boolean deleted,
			Set<ConfigurationDTO> configurationDTOs) {
		super();
		this.collectionId = collectionId;
		this.name = name;
		this.description = description;
		this.ownerGroup = ownerGroup;
		this.deleted = deleted;
		this.configurationDTOs = configurationDTOs;
	}

	/**
	 * @return id for the configuration collection
	 */
	public Long getCollectionId() {
		return collectionId;
	}

	/**
	 * Set the id for configuration collection
	 * 
	 * @param collectionId
	 */
	public void setCollectionId(Long collectionId) {
		this.collectionId = collectionId;
	}

	/**
	 * @return the name of configuration collection
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the name for this configuration collection
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description for this configuration collection.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Set the description for this configuration collection.
	 * 
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the user group of the user which created this configuration
	 *         collection
	 */
	public String getOwnerGroup() {
		return ownerGroup;
	}

	/**
	 * Set the user group which owns this configuration collection
	 * 
	 * @param ownerGroup
	 *            - user group for the user which created this configuration
	 *            collection
	 */
	public void setOwnerGroup(String ownerGroup) {
		this.ownerGroup = ownerGroup;
	}

	/**
	 * @return true if the configuration collection is in deleted hierarchy,
	 *         false otherwise.
	 */
	public Boolean isDeleted() {
		return deleted;
	}

	/**
	 * Set the deleted flag for this configuration collection
	 * 
	 * @param deleted
	 *            - flag to mark whether is configuration collection is deleted
	 *            and should move to deleted hierarchy
	 */
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	/**
	 * @return a Set of Configuration objects for this collection
	 */
	public Set<ConfigurationDTO> getConfigurationDTOs() {
		return configurationDTOs;
	}

	/**
	 * Set a collection of Configuration objects in this Collection
	 * 
	 * @param configurationDTOs
	 *            - Set of Configurations for this collection
	 */
	public void setConfigurationDTOs(Set<ConfigurationDTO> configurationDTOs) {
		this.configurationDTOs = configurationDTOs;
	}
}
