package com.ericsson;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class AbstractNewDao<T, K extends Serializable> implements NewDao<T, K> {

        protected HibernateTemplate hibernateTemplate;

        @Autowired
        public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
                this.hibernateTemplate = hibernateTemplate;
        }

        @Override
        public K save(T t) {
                return (K) hibernateTemplate.save(t);
        }

        @Override
        public void update(T t) {
                hibernateTemplate.update(t);
        }

        @Override
        public void saveOrUpdate(T t) {
            hibernateTemplate.saveOrUpdate(t);
        }

        @Override
        public List<T> findAll(Class<T> glass) {
                return hibernateTemplate.loadAll(glass);
        }

        @Override
        public T loadByPrimaryKey(Class<T> glass, K id) {
                return hibernateTemplate.load(glass, id);
        }

        @Override
        public void flush() {
                hibernateTemplate.flush();
        }

        @Override
        public void delete(T t) {
                hibernateTemplate.delete(t);
        }

        @Override
        public void deleteByPrimaryKey(Class<T> glass, K id) {
                hibernateTemplate.delete(hibernateTemplate.load(glass, id));
        }

        @Override
        public void deleteAll(Collection<T> items) {
                hibernateTemplate.deleteAll(items);
        }

        @Override
        public int executeNamedQuery(final String query, final Map<?, ?> props) {
                hibernateTemplate.execute(new HibernateCallback<Integer>() {
                        @Override
                        public Integer doInHibernate(Session arg0) throws HibernateException,
                                        SQLException {
                                Query q = arg0.createQuery(query);
                                q.setProperties(props);
                                return q.executeUpdate();
                        }
                });
                return 0;
        }

}
