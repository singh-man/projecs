package com.ericsson.cm.manager.datamanagement.dao.hibernate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ericsson.cm.manager.datamanagement.dao.ConfigCollectionDAO;
import com.ericsson.cm.manager.datamanagement.dto.ConfigCollectionDTO;

/**
 * Implementation of {@link ConfigCollectionDAO} using Hibernate and Spring ORM.
 */
@Repository("HibernateConfigCollectionDAO")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, isolation = Isolation.DEFAULT)
public class ConfigCollectionDAOImpl implements ConfigCollectionDAO {

	protected HibernateTemplate hibernateTemplate;

	@Override
	public void createConfigCollection(ConfigCollectionDTO configColl) {
		hibernateTemplate.save(configColl);
	}

	@Override
	public void updateConfigCollection(ConfigCollectionDTO configColl) {
		hibernateTemplate.update(configColl);
	}

	@Override
	public void deleteConfigCollection(long collId) {
		ConfigCollectionDTO configColl = hibernateTemplate.load(
				ConfigCollectionDTO.class, collId);
		hibernateTemplate.delete(configColl);
	}

	@Override
	@Transactional(readOnly = true)
	public ConfigCollectionDTO getConfigCollection(long collId) {
		return hibernateTemplate.load(ConfigCollectionDTO.class, collId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ConfigCollectionDTO> getAllConfigCollections() {
		return hibernateTemplate.loadAll(ConfigCollectionDTO.class);
	}

	@Autowired
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
}
