package com.ericsson.cm.manager.datamanagement.dao.hibernate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ericsson.cm.manager.datamanagement.dao.IdRangeDAO;
import com.ericsson.cm.manager.datamanagement.dto.IdRangeDTO;

/**
 * Implementation of {@link IdRangeDAO} using Hibernate and Spring ORM.
 */
@Repository("HibernateIdRangeDAO")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, isolation = Isolation.DEFAULT)
public class IdRangeDAOImpl implements IdRangeDAO {

	protected HibernateTemplate hibernateTemplate;

	@Override
	public IdRangeDTO getIdRange(String name) {
		IdRangeDTO idRange = hibernateTemplate.load(IdRangeDTO.class, name);

		// create a new instance to be returned because the instance associated
		// with hibernate session is to be updated for incrementing the start
		// value counter
		IdRangeDTO idRangeToBeReturned = new IdRangeDTO(idRange);

		// update the instance associated with hibernate session to increment
		// the start value counter
		idRange.setStartValue(idRange.getStartValue() + 1);

		return idRangeToBeReturned;
	}

	@Autowired
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
}
