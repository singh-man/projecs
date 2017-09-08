package com.ericsson.cm.manager.datamanagement.dao.hibernate;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ericsson.cm.common.exception.MMException;
import com.ericsson.cm.manager.datamanagement.dao.ProvisionServerDAO;
import com.ericsson.cm.manager.datamanagement.dto.AbstractServerDTO;
import com.ericsson.cm.manager.datamanagement.dto.ProvisionServerDTO;

/**
 * Implementation of {@link ProvisionServerDAO} using Hibernate and Spring
 * ORM.
 */
@Repository("HibernateProvisionServerDAO")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, isolation = Isolation.DEFAULT)
public class ProvisionServerDAOImpl implements ProvisionServerDAO {

	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	@Override
	public <T> void createProvisionServer(
			ProvisionServerDTO<? extends AbstractServerDTO<?>> provisionServerDTO)  {
		hibernateTemplate.save(provisionServerDTO);
	}

	@Override
	public <T> void updateProvisionServer(
			ProvisionServerDTO<? extends AbstractServerDTO<?>> provisionServerDTO)  {
		hibernateTemplate.save(provisionServerDTO);
	}

	@Override
	public <T> void deleteProvisionServer(
			ProvisionServerDTO<? extends AbstractServerDTO<?>> provisionServerDTO) {
		hibernateTemplate.delete(provisionServerDTO, LockMode.NONE);
	}

	@Override
	public <T extends AbstractServerDTO<T>> void deleteProvisionServer(long collId, long configId,
			long versionId, long serverId) throws MMException {
		ProvisionServerDTO<T> provisionServerDTO = getProvisionServer(collId, configId, versionId, serverId);
		provisionServerDTO.getServerDTO().getProvisionServerDTOs().remove(provisionServerDTO);
	}

	@Override
	@Transactional(readOnly=true)
	public <T extends AbstractServerDTO<T>> ProvisionServerDTO<T> getProvisionServer(final long configCollId, final long configId,
			final long versionId, final long serverId)  {
		ProvisionServerDTO<T> provisionServerDTO = hibernateTemplate.executeWithNativeSession(new HibernateCallback<ProvisionServerDTO<T>>() {
			@Override
			public ProvisionServerDTO<T> doInHibernate(Session session) throws HibernateException,
					SQLException {
				Criteria crit = session.createCriteria(ProvisionServerDTO.class);
				crit.add(Restrictions.eq("configCollectionDTO.collectionId", configCollId)).
				add(Restrictions.eq("configVersionDTO.configVersionId.configurationDTO.configId", configId)).
				add(Restrictions.eq("configVersionDTO.configVersionId.versionId", versionId)).
				add(Restrictions.eq("serverDTO.id", serverId));
				return (ProvisionServerDTO<T>) crit.list().get(0);
			}
		});
		return provisionServerDTO;
	}

	@SuppressWarnings("rawtypes")
	@Override
	@Transactional(readOnly=true)
	public List<ProvisionServerDTO> getAllProvisionServers() {
		return hibernateTemplate.loadAll(ProvisionServerDTO.class);
	}
}
