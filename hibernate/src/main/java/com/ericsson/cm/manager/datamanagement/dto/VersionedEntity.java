package com.ericsson.cm.manager.datamanagement.dto;

import com.ericsson.cm.manager.datamanagement.Ignore;

/**
 * Table contains versioned data;
 * Helpful in long help transactions.
 * Managed Internally by Hibernate.
 * 
 * Ideally P.K. should be system generated.
 * 
 * @author emmhssh
 *
 */
public abstract class VersionedEntity {
	
	private Integer version = -1; // negative unsaved-value

	public VersionedEntity() {
		super();
	}

	protected Integer getVersion() {
		return version;
	}

	@Ignore
	protected void setVersion(Integer version) {
		this.version = version;
	}

}
