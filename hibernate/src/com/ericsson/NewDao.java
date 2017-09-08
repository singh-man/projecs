package com.ericsson;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface NewDao<T, K extends Serializable> {

        void flush();

        K save(T t) ;

        void update(T t);

        void saveOrUpdate(T t);

        List<T> findAll(Class<T> glass);

        T loadByPrimaryKey(Class<T> glass, K id);

        void delete(T t);

        void deleteByPrimaryKey(Class<T> glass, K id);

        void deleteAll(Collection<T> items);

        /**
         * Returns number of rows modified
         */
        int executeNamedQuery(String query, Map<?, ?> props);

}
