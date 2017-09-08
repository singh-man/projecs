package com.ericsson;

import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public class AbstractDao implements Dao {

	@Autowired
	protected HibernateTemplate hibernateTemplate;

	//private static final Transformer unWrapper = new UnWrapperImpl();

	protected AbstractDao() {}

	@Override
	public void saveOrUpdate(Object object) {
		hibernateTemplate.saveOrUpdate(object);
	}

	@Override
	public void delete(Object object) {
		if (object == null) return;

		/**
		 * To avoid exception indicating that we have this object pre-existing in the current
		 * session and can't thus remove it.
		 */
		Session currentSession = hibernateTemplate.getSessionFactory().getCurrentSession();
		currentSession.lock(object, LockMode.NONE);        
		currentSession.delete(object);
	}

	@Override
	public void flush() {
		hibernateTemplate.getSessionFactory().getCurrentSession().flush();
	}
	
	@Override
	public final <T> List<T> findAll(Class<T> glass) {
		return hibernateTemplate.loadAll(glass);
	}

	@Override
	public final <T> T load(Class<T> glass, Serializable id) {
		return (T)hibernateTemplate.load(glass, id);
	}

	@Override
	public <T> T load(Class<T> glass, String property, Serializable id) {
		return (T)hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(glass).add(Restrictions.naturalId().set(property, id)).uniqueResult();
	}

	@Override
	public void clear() {
		hibernateTemplate.getSessionFactory().getCurrentSession().clear();
	}

	@Override
	public void deleteAll(Collection items) {
		if(items == null) {
			return;
		}

		if(items.isEmpty()) {
			return;
		}

		hibernateTemplate.deleteAll(items);
	}
	
	protected final <R> List<R> find(HibernateCallback callback) {
		return (List<R>)hibernateTemplate.execute(callback);
	}
	
	protected final <R> List<R> find(String query) {
		return hibernateTemplate.findByNamedQuery(query);
	}
	
	protected final <R> List<R> find(String query, String name, Object param) {
		return hibernateTemplate.findByNamedQueryAndNamedParam(query, name, param);
	}
	
	protected final <R> List<R> find(String query, String[] names, Object[] params) {
		return hibernateTemplate.findByNamedQueryAndNamedParam(query, names, params);
	}

	protected <R> List<R> find(final String queryName, final String[] names, final Object[] params, final Integer limit) {
		if(names.length != params.length) {
			throw new IllegalArgumentException("Length of values array must match length of types array");
		}

		return (List<R>)hibernateTemplate.executeWithNativeSession(new NamedQueryHibernateCallback<R>(limit, params, queryName, names));
	}   
	
	protected <R> List<R> find(final String queryName, final String[] names, final Object[] params, final Integer beginIndex, final Integer limit) {
		if(names.length != params.length) {
			throw new IllegalArgumentException("Length of values array must match length of types array");
		}

		return (List<R>)hibernateTemplate.executeWithNativeSession(new NamedQueryHibernateCallback<R>(beginIndex, limit, params, queryName, names));
	}
	
	void setParameter(Query queryObject, String paramName, Object paramValue) {
		if(paramValue instanceof Collection) {
			queryObject.setParameterList(paramName, (Collection)paramValue);
		} else if(paramValue instanceof Object[]) {
			queryObject.setParameterList(paramName, (Object[])paramValue);
		} else {
			queryObject.setParameter(paramName, paramValue);
		}
	}

	private class NamedQueryHibernateCallback<T> implements HibernateCallback<T> {
		private final Integer limit;
		private final Integer beginIndex;
		private final String name;
		private final String[] names;
		private final Object[] params;

		NamedQueryHibernateCallback(Integer limit, Object[] params, String name, String[] names) {
			this.limit = limit;
			this.params = params;
			this.name = name;
			this.names = names;
			this.beginIndex = null;
		}

		NamedQueryHibernateCallback(Integer beginIndex, Integer limit, Object[] params, String name, String[] names) {
			this.limit = limit;
			this.params = params;
			this.name = name;
			this.names = names;
			this.beginIndex = beginIndex;
		}

		public T doInHibernate(Session session) throws HibernateException {
			Query queryObject = session.getNamedQuery(name);

			if (beginIndex != null) {
				queryObject.setFirstResult(beginIndex);
			}

			if(limit != null) {
				queryObject.setMaxResults(limit);
			}

			// from prepareQuery of HibernateTemplate
			if(AbstractDao.this.hibernateTemplate.isCacheQueries()) {
				queryObject.setCacheable(true);

				if(AbstractDao.this.hibernateTemplate.getQueryCacheRegion() != null) {
					queryObject.setCacheRegion(AbstractDao.this.hibernateTemplate.getQueryCacheRegion());
				}
			}

			SessionFactoryUtils.applyTransactionTimeout(queryObject, AbstractDao.this.hibernateTemplate.getSessionFactory());

			for(int i = 0; i < names.length; i++) {
				AbstractDao.this.setParameter(queryObject, names[i], params[i]);
			}

			return (T) queryObject.list();
		}
	}
}