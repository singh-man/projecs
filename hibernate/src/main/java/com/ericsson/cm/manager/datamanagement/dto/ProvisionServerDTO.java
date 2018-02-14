package com.ericsson.cm.manager.datamanagement.dto;

import java.io.Serializable;

/**
 * Represents Provisioning details about the Server.
 *  
 * @author emmhssh
 *
 */
public class ProvisionServerDTO<T extends AbstractServerDTO<T>> extends VersionedEntity implements Serializable {

	private static final long serialVersionUID = 3722783191702957607L;
	
	private ConfigCollectionDTO configCollectionDTO;
	private ConfigVersionDTO configVersionDTO;	
	private T serverDTO;

	public ProvisionServerDTO(ConfigCollectionDTO configCollectionDTO,
			ConfigVersionDTO configVersionDTO,
			T serverDTO) {
		super();
		this.configCollectionDTO = configCollectionDTO;
		this.configVersionDTO = configVersionDTO;
		this.serverDTO = serverDTO;
	}

	public ProvisionServerDTO() {}

	public ConfigCollectionDTO getConfigCollectionDTO() {
		return configCollectionDTO;
	}

	public void setConfigCollectionDTO(ConfigCollectionDTO configCollectionDTO) {
		this.configCollectionDTO = configCollectionDTO;
	}

	public ConfigVersionDTO getConfigVersionDTO() {
		return configVersionDTO;
	}

	public void setConfigVersionDTO(ConfigVersionDTO configVersionDTO) {
		this.configVersionDTO = configVersionDTO;
	}

	public T getServerDTO() {
		return serverDTO;
	}

	public void setServerDTO(T serverDTO) {
		this.serverDTO = serverDTO;
	}
	
	@Override
	public boolean equals(Object other) {
		if ((this == other)) return true;
		if ((other == null)) return false;
		if (!(other instanceof ProvisionServerDTO)) return false;
		
		ProvisionServerDTO<?> castOther = (ProvisionServerDTO<?>) other;

		return ((this.getConfigCollectionDTO() == castOther.getConfigCollectionDTO()) || (this
				.getConfigCollectionDTO() != null
				&& castOther.getConfigCollectionDTO() != null && this
				.getConfigCollectionDTO().equals(castOther.getConfigCollectionDTO())))
				&& ((this.getConfigVersionDTO() == castOther
						.getConfigVersionDTO()) || (this.getConfigVersionDTO() != null
						&& castOther.getConfigVersionDTO() != null && this
						.getConfigVersionDTO().equals(
								castOther.getConfigVersionDTO())))
				&& ((this.getServerDTO() == castOther.getServerDTO()) || (this
						.getServerDTO() != null
						&& castOther.getServerDTO() != null && this
						.getServerDTO().equals(castOther.getServerDTO())));
	}

	@Override
	public int hashCode() {
		int prime = 17;

		prime = 37
				* prime
				+ (getConfigCollectionDTO() == null ? 0 : this.getConfigCollectionDTO()
						.hashCode());
		prime = 37
				* prime
				+ (getConfigVersionDTO() == null ? 0 : this.getConfigVersionDTO()
						.hashCode());
		prime = 37 * prime
				+ (getServerDTO() == null ? 0 : this.getServerDTO().hashCode());
		return prime;
	}

}
