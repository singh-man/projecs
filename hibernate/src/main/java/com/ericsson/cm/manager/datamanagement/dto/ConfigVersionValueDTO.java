package com.ericsson.cm.manager.datamanagement.dto;

/**
 * Data Transfer object for Configuration version value/content
 */
public class ConfigVersionValueDTO {

	private ConfigVersionCompositeKey configVersionId;

	private String value;

	public ConfigVersionValueDTO(ConfigVersionCompositeKey configVersionId,
			String value) {
		super();
		this.configVersionId = configVersionId;
		this.value = value;
	}

	public ConfigVersionValueDTO() {
		super();
	}

	/**
	 * @return value/content of the configuration version
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Set the value/content for the configuration version
	 * 
	 * @param value
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the {@link ConfigVersionCompositeKey} for the configuration
	 *         version
	 */
	public ConfigVersionCompositeKey getConfigVersionId() {
		return configVersionId;
	}

	/**
	 * Set the {@link ConfigVersionCompositeKey} for the configuration version.
	 * 
	 * @param configVersionId
	 */
	public void setConfigVersionId(ConfigVersionCompositeKey configVersionId) {
		this.configVersionId = configVersionId;
	}

}
