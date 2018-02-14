package com.ericsson.cm.manager.datamanagement.dao;

import java.util.List;

import com.ericsson.cm.common.exception.MMException;
import com.ericsson.cm.manager.datamanagement.dto.ConfigCollectionDTO;

/**
 * DAO interface to perform operations on Configuration collections. Each method
 * of this interface runs in a single transaction. If the caller needs to
 * execute multiple methods in a single transaction then the caller should apply
 * transaction itself and call the required methods in spring transaction. The
 * methods in this interface gets associated to the transactions applied by the
 * caller and do not start a new transaction in this case.
 */
public interface ConfigCollectionDAO {

	/**
	 * Method to persist a configuration collection in Data Management
	 * 
	 * @param configColl
	 * @throws MMException
	 */
	public void createConfigCollection(ConfigCollectionDTO configColl)
			throws MMException;

	/**
	 * Method to update a persisted record of configuration collection in Data
	 * Management
	 * 
	 * @param configColl
	 * @throws MMException
	 */
	public void updateConfigCollection(ConfigCollectionDTO configColl)
			throws MMException;

	/**
	 * Method to delete a persisted record of configuration collection from Data
	 * Management.
	 * 
	 * @param collId
	 * @throws MMException
	 */
	public void deleteConfigCollection(long collId) throws MMException;

	/**
	 * Method to retrieve a persisted configuration collection from Data
	 * Management
	 * 
	 * @param collId
	 *            collection id of the collection to be retrieved.
	 * @return {@link ConfigCollectionDTO} instance for the collection id
	 * @throws MMException
	 */
	public ConfigCollectionDTO getConfigCollection(long collId)
			throws MMException;

	/**
	 * @return a list of all the persisted configuration collections from Data
	 *         Management.
	 * @throws MMException
	 */
	public List<ConfigCollectionDTO> getAllConfigCollections()
			throws MMException;
}
