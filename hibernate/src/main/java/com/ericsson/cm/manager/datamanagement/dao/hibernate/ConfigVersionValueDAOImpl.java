package com.ericsson.cm.manager.datamanagement.dao.hibernate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ericsson.cm.manager.datamanagement.dao.ConfigVersionValueDAO;
import com.ericsson.cm.manager.datamanagement.dto.ConfigVersionCompositeKey;
import com.ericsson.cm.manager.datamanagement.dto.ConfigVersionValueDTO;
import com.ericsson.cm.manager.datamanagement.dto.ConfigurationDTO;

/**
 * Implementation of {@link ConfigVersionValueDAO} using Hibernate and Spring
 * ORM.
 */
@Repository("HibernateConfigVersionValueDAO")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, isolation = Isolation.DEFAULT)
public class ConfigVersionValueDAOImpl implements ConfigVersionValueDAO {

	protected HibernateTemplate hibernateTemplate;

	@Override
	public void updateConfigVersionValue(ConfigVersionValueDTO configVerVal) {
		hibernateTemplate.update(configVerVal);
	}

	@Override
	@Transactional(readOnly = true)
	public ConfigVersionValueDTO getConfigVersionValue(long configId,
			long versionId) {
		ConfigurationDTO configurationDTO = new ConfigurationDTO();
		configurationDTO.setConfigId(configId);
		ConfigVersionCompositeKey configVersionId = new ConfigVersionCompositeKey(
				configurationDTO, versionId);
		return hibernateTemplate.load(ConfigVersionValueDTO.class,
				configVersionId);
	}

	@Autowired
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

}
