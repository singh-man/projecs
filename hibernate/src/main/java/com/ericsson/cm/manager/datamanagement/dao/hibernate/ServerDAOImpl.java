package com.ericsson.cm.manager.datamanagement.dao.hibernate;

import com.ericsson.cm.manager.datamanagement.dao.ServerDAO;
import com.ericsson.cm.manager.datamanagement.dto.AbstractServerDTO;
import com.ericsson.cm.manager.datamanagement.dto.FEServerDTO;
import com.ericsson.cm.manager.datamanagement.dto.OnlineServerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of {@link ServerDAO} using Hibernate and Spring
 * ORM.
 */
@Repository("HibernateServerDAO")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, isolation = Isolation.DEFAULT)
public class ServerDAOImpl implements ServerDAO {

	@Autowired
	private HibernateTemplate hibernateTemplate;

	@Override
	public <T extends AbstractServerDTO<T>> void createServer(T serverDTO) {
		hibernateTemplate.save(serverDTO);
	}

	/**
	 * Running in transactions; hence required to remove from parent association and then update parent
	 * without transaction child can be directly deleted.
	 */
	@Override
	public void deleteServer(long id)  {
		AbstractServerDTO<?> server = getServer(AbstractServerDTO.class, id);
		server.getServerGroupDTO().getServerDTOs().remove(server);
	}

	@Override
	public <T extends AbstractServerDTO<T>> void updateServer(T serverDTO) {
		hibernateTemplate.update(serverDTO);
	}

	protected <T extends AbstractServerDTO<T>> T getServer(Class<T> glass, long id)  {
		return hibernateTemplate.load(glass, id);
	}
	
	@Override
	@Transactional(readOnly=true)
	public FEServerDTO getFEServer(long id)  {
		return getServer(FEServerDTO.class, id);
	}

	@Override
	@Transactional(readOnly=true)
	public OnlineServerDTO getOnlineServer(long id)  {
		return getServer(OnlineServerDTO.class, id);
	}

	@Override
	@Transactional(readOnly=true)
	public List<FEServerDTO> getAllFEServers()  {
		return getAllServers(FEServerDTO.class);
	}

	@Override
	@Transactional(readOnly=true)
	public List<OnlineServerDTO> getAllOnlineServers()  {
		return getAllServers(OnlineServerDTO.class);
	}

	/**
	 * use NULL if all the servers need to be fetched.
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly=true)
	public <T extends AbstractServerDTO<T>> List<T> getAllServers(Class<T> glass) {
		if(glass == null)
			glass = (Class<T>) AbstractServerDTO.class;
		return hibernateTemplate.loadAll(glass);
	}
	
}
