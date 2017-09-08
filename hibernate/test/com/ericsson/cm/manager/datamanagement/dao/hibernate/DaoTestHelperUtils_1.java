package com.ericsson.cm.manager.datamanagement.dao.hibernate;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class DaoTestHelperUtils_1 {

	private final static ApplicationContext ctx = new ClassPathXmlApplicationContext(
			"classpath:applicationContext.xml",
			"classpath:dataManagementContext.xml");

	public static ApplicationContext getContext() {
		return ctx;
	}
	
	/**
	 * Clears the tables; as by the given classes
	 */
	public static <T> void deleteAll(HibernateTemplate hibernateTemplate, Class... glass) {
		for(Class<T> g : glass) {
			hibernateTemplate.deleteAll(hibernateTemplate.loadAll(g));
		}
	}

}
