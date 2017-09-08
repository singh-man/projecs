package com.ericsson.cm.manager.datamanagement.dao.hibernate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ericsson.cm.manager.datamanagement.dao.ConfigVersionDAO;
import com.ericsson.cm.manager.datamanagement.dto.ConfigVersionCompositeKey;
import com.ericsson.cm.manager.datamanagement.dto.ConfigVersionDTO;
import com.ericsson.cm.manager.datamanagement.dto.ConfigurationDTO;

/**
 * Implementation of {@link ConfigVersionDAO} using Hibernate and Spring ORM.
 */
@Repository("HibernateConfigVersionDAO")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, isolation = Isolation.DEFAULT)
public class ConfigVersionDAOImpl implements ConfigVersionDAO {

	protected HibernateTemplate hibernateTemplate;

	@Override
	public void createConfigVersion(ConfigVersionDTO configVer) {
		hibernateTemplate.save(configVer);
	}

	@Override
	public void updateConfigVersion(ConfigVersionDTO configVer) {
		hibernateTemplate.update(configVer);
	}

	@Override
	public void deleteConfigVersion(long configId, long versionId) {
		ConfigurationDTO configurationDTO = new ConfigurationDTO();
		configurationDTO.setConfigId(configId);
		ConfigVersionCompositeKey configVersionId = new ConfigVersionCompositeKey(
				configurationDTO, versionId);
		hibernateTemplate.delete(hibernateTemplate.load(ConfigVersionDTO.class,
				configVersionId));
	}

	@Override
	@Transactional(readOnly = true)
	public ConfigVersionDTO getConfigVersion(long configId, long versionId) {
		ConfigurationDTO configurationDTO = new ConfigurationDTO();
		configurationDTO.setConfigId(configId);
		ConfigVersionCompositeKey configVersionId = new ConfigVersionCompositeKey(
				configurationDTO, versionId);
		return hibernateTemplate.load(ConfigVersionDTO.class, configVersionId);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<ConfigVersionDTO> getAllConfigVersions(long configId) {
		return hibernateTemplate.findByNamedQueryAndNamedParam(
				"getAllConfigVersionsByConfigId", "configId", configId);
	}

	@Autowired
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
}
