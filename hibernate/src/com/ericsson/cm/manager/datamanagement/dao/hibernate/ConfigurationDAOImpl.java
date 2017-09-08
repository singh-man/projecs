package com.ericsson.cm.manager.datamanagement.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ericsson.cm.manager.datamanagement.dao.ConfigurationDAO;
import com.ericsson.cm.manager.datamanagement.dto.ConfigCollectionDTO;
import com.ericsson.cm.manager.datamanagement.dto.ConfigurationDTO;

/**
 * Implementation of {@link ConfigurationDAO} using Hibernate and Spring ORM.
 */
@Repository("HibernateConfigurationDAO")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, isolation = Isolation.DEFAULT)
public class ConfigurationDAOImpl implements ConfigurationDAO {

	protected HibernateTemplate hibernateTemplate;

	@Override
	public void createConfiguration(ConfigurationDTO config) {
		hibernateTemplate.save(config);
	}

	@Override
	public void deteleConfiguration(long configId) {
		ConfigurationDTO configurationDTO = hibernateTemplate.load(
				ConfigurationDTO.class, configId);
		configurationDTO.getConfigCollectDTO().getConfigurationDTOs()
				.remove(configurationDTO);
	}

	@Override
	public void updateConfiguration(ConfigurationDTO config) {
		hibernateTemplate.update(config);
	}

	@Override
	@Transactional(readOnly = true)
	public ConfigurationDTO getConfiguration(long configId) {
		return hibernateTemplate.load(ConfigurationDTO.class, configId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ConfigurationDTO> getAllConfigurations(long collId) {
		List<ConfigurationDTO> list = new ArrayList<ConfigurationDTO>(
				hibernateTemplate.load(ConfigCollectionDTO.class, collId)
						.getConfigurationDTOs());
		return list;
	}

	@Autowired
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
}
