package com.web.spring.fileUpDown;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringContextAware implements ApplicationContextAware {

	private static ApplicationContext ctx;
	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		ctx = arg0;
	}
	
	public static <T> T getBean(Class<T> bean) {
		return ctx.getBean(bean);
	}

}
