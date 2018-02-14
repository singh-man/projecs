package com.ericsson.cm.manager.datamanagement.dao;

import com.ericsson.cm.common.exception.MMException;
import com.ericsson.cm.manager.datamanagement.dto.ConfigVersionValueDTO;

/**
 * DAO interface to perform operations on Configuration version value. Each
 * method of this interface runs in a single transaction. If the caller needs to
 * execute multiple methods in a single transaction then the caller should apply
 * transaction itself and call the required methods in spring transaction. The
 * methods in this interface gets associated to the transactions applied by the
 * caller and do not start a new transaction in this case.
 */
public interface ConfigVersionValueDAO {

	/**
	 * Update the content of a configuration version in Data Management.
	 * 
	 * @param configVerVal
	 * @throws MMException
	 */
	public void updateConfigVersionValue(ConfigVersionValueDTO configVerVal)
			throws MMException;

	/**
	 * @param configId
	 * @param versionId
	 * @return the content of a configuration version persisted in Data
	 *         Management.
	 * @throws MMException
	 */
	public ConfigVersionValueDTO getConfigVersionValue(long configId,
													   long versionId) throws MMException;

}
