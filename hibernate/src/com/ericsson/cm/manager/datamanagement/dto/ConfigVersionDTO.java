package com.ericsson.cm.manager.datamanagement.dto;

/**
 * Data Transfer object for Configuration version
 */
public class ConfigVersionDTO {

	private ConfigVersionCompositeKey configVersionId;

	private Long saveTime;
	private String versionText;
	private Boolean checkedOut;
	private Long originalVersion;
	private String configUser;

	public ConfigVersionDTO() {
		super();
	}

	public ConfigVersionDTO(ConfigVersionCompositeKey configVersionId,
			Long saveTime, String versionText, Boolean checkedOut,
			Long originalVersion, String configUser,
			ConfigurationDTO configurationDTO) {
		super();
		this.configVersionId = configVersionId;
		this.saveTime = saveTime;
		this.versionText = versionText;
		this.checkedOut = checkedOut;
		this.originalVersion = originalVersion;
		this.configUser = configUser;
	}

	/**
	 * @return the time at which the configuration version is saved.
	 */
	public Long getSaveTime() {
		return saveTime;
	}

	/**
	 * Set the time at which the configuration version is saved.
	 * 
	 * @param saveTime
	 */
	public void setSaveTime(Long saveTime) {
		this.saveTime = saveTime;
	}

	/**
	 * @return the text/description for the configuration version.
	 */
	public String getVersionText() {
		return versionText;
	}

	/**
	 * Set the text/description for the configuration version.
	 * 
	 * @param versionText
	 */
	public void setVersionText(String versionText) {
		this.versionText = versionText;
	}

	/**
	 * @return true if configuration version is checked out, false otherwise.
	 */
	public Boolean isCheckedOut() {
		return checkedOut;
	}

	/**
	 * Set if the configuration version is checkout.
	 * 
	 * @param checkedOut
	 */
	public void setCheckedOut(Boolean checkedOut) {
		this.checkedOut = checkedOut;
	}

	/**
	 * @return the configuration version id from which this configuration
	 *         version is checked out.
	 */
	public Long getOriginalVersion() {
		return originalVersion;
	}

	/**
	 * Set the configuration version id from which this configuration version is
	 * checked out.
	 * 
	 * @param originalVersion
	 */
	public void setOriginalVersion(Long originalVersion) {
		this.originalVersion = originalVersion;
	}

	/**
	 * @return the user which created the configuration version
	 */
	public String getConfigUser() {
		return configUser;
	}

	/**
	 * Set the user which created the configuration version
	 * 
	 * @param configUser
	 */
	public void setConfigUser(String configUser) {
		this.configUser = configUser;
	}

	/**
	 * @return the {@link ConfigVersionCompositeKey} for the configuration
	 *         version.
	 */
	public ConfigVersionCompositeKey getConfigVersionId() {
		return configVersionId;
	}

	/**
	 * Set the {@link ConfigVersionCompositeKey} for the configuration version
	 * 
	 * @param configVersionId
	 */
	public void setConfigVersionId(ConfigVersionCompositeKey configVersionId) {
		this.configVersionId = configVersionId;
	}
}
