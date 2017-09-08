package com.ericsson.cm.manager.datamanagement.dao;

import java.util.List;

import com.ericsson.cm.common.exception.MMException;
import com.ericsson.cm.manager.datamanagement.dto.ConfigurationDTO;

/**
 * DAO interface to perform operations on Configurations. Each method of this
 * interface runs in a single transaction. If the caller needs to execute
 * multiple methods in a single transaction then the caller should apply
 * transaction itself and call the required methods in spring transaction. The
 * methods in this interface gets associated to the transactions applied by the
 * caller and do not start a new transaction in this case.
 * 
 * @author emmhssh
 * 
 */
public interface ConfigurationDAO {
	/**
	 * Method to persist a configuration in Data Management.
	 * 
	 * @param config
	 * @throws MMException
	 */
	public void createConfiguration(ConfigurationDTO config) throws MMException;

	/**
	 * Method to delete a persisted record of configuration from Data
	 * Management.
	 * 
	 * @param configId
	 * @throws MMException
	 */
	public void deteleConfiguration(long configId) throws MMException;

	/**
	 * Method to update a persisted record of configuration in Data Management
	 * 
	 * @param config
	 * @throws MMException
	 */
	public void updateConfiguration(ConfigurationDTO config) throws MMException;

	/**
	 * Method to retrieve a persisted configuration from Data Management
	 * 
	 * @param configId
	 *            - configuration id of the configuration to be retrieved.
	 * @return {@link ConfigurationDTO} instance for the configuration id
	 * @throws MMException
	 */
	public ConfigurationDTO getConfiguration(long configId) throws MMException;

	/**
	 * @param collId
	 *            collection id of the collection for which the associated
	 *            configurations are to retrieved
	 * @return a list of all the persisted configurations associated with a
	 *         collection from Data Management.
	 * @throws MMException
	 */
	public List<ConfigurationDTO> getAllConfigurations(long collId)
			throws MMException;
}
