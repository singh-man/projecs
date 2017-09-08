package com.ericsson;


import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 * @author  esghpet
 */
public abstract class AbstractDataManagerImpl<T, K extends Serializable> implements MM_DataManager<T, K> 
{

	@Autowired
	protected HibernateTemplate hibernateTemplate;
	
	@Override
	public boolean create(T object) 
	{
		throw new UnsupportedOperationException("Operation not Supported");
	}

	@Override
	public boolean update(T object) 
	{
		throw new UnsupportedOperationException("Operation not Supported");
	}

	@Override
	public boolean delete(K key) 
	{
		throw new UnsupportedOperationException("Operation not Supported");
	}

	@Override
	public boolean deleteAll(List<K> keys) 
	{
		throw new UnsupportedOperationException("Operation not Supported");
	}

	@Override
	public T get(K key) 
	{
		throw new UnsupportedOperationException("Operation not Supported");
	}

	@Override
	public List<T> getAll() 
	{
		throw new UnsupportedOperationException("Operation not Supported");
	}
	
	protected List<T> getAll(Class<T> glass)
	{
		return hibernateTemplate.loadAll(glass);
	}
	
	protected T get(Class<T> glass, Serializable id)
	{
		return hibernateTemplate.load(glass, id);
	}
	
	protected void delete(Class<T> glass, Serializable id)
	{
		hibernateTemplate.delete(hibernateTemplate.get(glass, id));
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

}
