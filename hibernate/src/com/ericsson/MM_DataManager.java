package com.ericsson;

import java.io.Serializable;
import java.util.List;

public interface MM_DataManager<T, K extends Serializable> 
{
	public boolean create(T object);

	public boolean update(T object);

	public boolean delete(K key);

	public boolean deleteAll(List<K> keys);

	public T get(K key);

	public List<T> getAll();

}
