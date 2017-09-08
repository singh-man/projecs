package com.ericsson.cm.manager.datamanagement.dao;

import com.ericsson.cm.common.exception.MMException;
import com.ericsson.cm.manager.datamanagement.dto.IdRangeDTO;

/**
 * DAO interface to perform operations on ID Range. Each method of this
 * interface runs in a single transaction. If the caller needs to execute
 * multiple methods in a single transaction then the caller should apply
 * transaction itself and call the required methods in spring transaction. The
 * methods in this interface gets associated to the transactions applied by the
 * caller and do not start a new transaction in this case.
 */
public interface IdRangeDAO {

	/**
	 * @param name
	 * @return {@link IdRangeDTO} for the given idRange name. Each call to this
	 *         method shall increment startValue field of {@link IdRangeDTO}
	 *         with 1.
	 * @throws MMException
	 */
	public IdRangeDTO getIdRange(String name) throws MMException;
}
