package com.ericsson.cm.manager.datamanagement.dao.hibernate;

import java.util.List;

import org.hibernate.LockMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ericsson.cm.common.exception.MMException;
import com.ericsson.cm.manager.datamanagement.dao.ServerGroupDAO;
import com.ericsson.cm.manager.datamanagement.dto.ServerGroupDTO;

/**
 * Implementation of {@link ServerGroupDAO} using Hibernate and Spring
 * ORM.
 */
@Repository("HibernateServerGroupDAO")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, isolation = Isolation.DEFAULT)
public class ServerGroupDAOImpl implements ServerGroupDAO {

	private HibernateTemplate hibernateTemplate;

	@Override
	public void createServerGroup(ServerGroupDTO serverGroupDTO)
			throws MMException {
		hibernateTemplate.save(serverGroupDTO);
	}

	@Override
	public void updateServerGroup(ServerGroupDTO serverGroupDTO)
			throws MMException {
		hibernateTemplate.update(serverGroupDTO);
	}


	@Override
	public void deleteServerGroup(ServerGroupDTO serverGroupDTO) throws MMException {
		if(serverGroupDTO != null)
			hibernateTemplate.delete(serverGroupDTO, LockMode.NONE);
	}
	
	@Override
	public void deleteServerGroup(long id) throws MMException {
		hibernateTemplate.delete(getServerGroup(id));
	}

	@Override
	@Transactional(readOnly=true)
	public ServerGroupDTO getServerGroup(long id) throws MMException {
		return hibernateTemplate.load(ServerGroupDTO.class, id);
	}

	@Override
	@Transactional(readOnly=true)
	public List<ServerGroupDTO> getAllServerGroup() throws MMException {
		return hibernateTemplate.loadAll(ServerGroupDTO.class);
	}

	@Autowired
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

}
