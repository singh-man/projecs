package com.ericsson.cm.manager.datamanagement.dao;

import java.util.List;

import com.ericsson.cm.common.exception.MMException;
import com.ericsson.cm.manager.datamanagement.dto.ConfigVersionDTO;

/**
 * DAO interface to perform operations on Configuration version. Each method of
 * this interface runs in a single transaction. If the caller needs to execute
 * multiple methods in a single transaction then the caller should apply
 * transaction itself and call the required methods in spring transaction. The
 * methods in this interface gets associated to the transactions applied by the
 * caller and do not start a new transaction in this case.
 */
public interface ConfigVersionDAO {

	/**
	 * Persist a configuration version in Data Management
	 * 
	 * @param configVer
	 * @throws MMException
	 */
	public void createConfigVersion(ConfigVersionDTO configVer)
			throws MMException;

	/**
	 * Update a persisted Configuration version in Data Management.
	 * 
	 * @param configVer
	 * @throws MMException
	 */
	public void updateConfigVersion(ConfigVersionDTO configVer)
			throws MMException;

	/**
	 * Delete a configuration version from Data Management.
	 * 
	 * @param configId
	 * @param versionId
	 * @throws MMException
	 */
	public void deleteConfigVersion(long configId, long versionId)
			throws MMException;

	/**
	 * @param configId
	 * @param versionId
	 * @return Configuration version for the given configuration id and version
	 *         id.
	 * @throws MMException
	 */
	public ConfigVersionDTO getConfigVersion(long configId, long versionId)
			throws MMException;

	/**
	 * @param configId
	 * @return all the configuration versions for the given configuration id.
	 * @throws MMException
	 */
	public List<ConfigVersionDTO> getAllConfigVersions(long configId)
			throws MMException;

}
