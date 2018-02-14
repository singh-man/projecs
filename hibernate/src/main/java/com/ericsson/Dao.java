package com.ericsson;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public interface Dao {

	void saveOrUpdate(Object object);

	void delete(Object object);

	void flush();

	<T> List<T> findAll(Class<T> glass);

	<T> T load(Class<T> glass, Serializable id);

	/**
	 * Use Cautiously
	 */
	public <T> T load(Class<T> glass, String property, Serializable id);

	/* Added clear method to enable to clear the session*/
	void clear();

	/**
	 * This method is used to delete a collection/list of objects
	 */
	void deleteAll(Collection items);
}

