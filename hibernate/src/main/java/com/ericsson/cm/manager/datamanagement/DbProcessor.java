package com.ericsson.cm.manager.datamanagement;

import java.util.logging.Logger;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.orm.hibernate3.HibernateSystemException;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;

/**
 * This class shall only be used in Developer environment. When registered in
 * spring application context, this class shall validate the Database schema on
 * startup and shall create the Database schema from hibernate mapping files, if
 * the schema is not already present.
 * 
 * @author emmhssh
 * 
 */
public class DbProcessor implements BeanPostProcessor {

	static final Logger log = Logger.getLogger(DbProcessor.class.getName());

	@Override
	public Object postProcessAfterInitialization(Object bean, String arg1)
			throws BeansException {

		if (bean instanceof LocalSessionFactoryBean) {
			LocalSessionFactoryBean lsfb = (LocalSessionFactoryBean) bean;
			try {
				lsfb.validateDatabaseSchema();
			} catch (HibernateSystemException e) {
				try {
					log.info("Db validation failed trying to create the tables:");
					lsfb.createDatabaseSchema();
				} catch (HibernateSystemException e1) {
					log.severe("Failed to Validate/Create tables; possible that table already exist");
				}
			}
		}
		return bean;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String arg1)
			throws BeansException {
		return bean;
	}

}
