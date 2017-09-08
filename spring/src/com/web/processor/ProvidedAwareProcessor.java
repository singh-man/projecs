package com.web.processor;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;

public class ProvidedAwareProcessor implements InstantiationAwareBeanPostProcessor{

	/**
	 * Idea:
	 * To set the blank ProvidedBean in the processor first and using the processor this 
	 * singleton object will be inject to all the @Provided places
	 * 
	 * Somewhere in the application this ProvidedBean object will be populated(of-course in the class 
	 * that used @SessionBean)
	 * 
	 * Once the object is populated/modified it will reflect in the complete application
	 * 
	 * 
	 * ****Note: with spring 2.5 @Autowired principally works in the same way.****
	 * 
	 */
	
	/*
	 * • The @Autowire and @Required annotations are accomplished in Spring with BeanPostProcessors.
	 * • Again, the BeanPostProcessor requires use of an application context.
	 * • In order to use the @Autowire annotation, a single Spring provided 
	 * AutowiredAnnotationBeanPostProcessor bean must be registered.
	 * <bean class="org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor"/>
	 */
	private ProvidedBean providedBean;
	private ProvidedBeanSingleton providedBeanSingleton;
	
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	@Override
	public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
		if(bean.getClass().isAnnotationPresent(SessionInjectedBean.class)) {
			Method[] methods = bean.getClass().getMethods();
			for(Method m : methods)
				if(m.isAnnotationPresent(Provided.class)) {
					try {
						for(Class clazz : m.getParameterTypes()) {
							if(clazz.getCanonicalName().equals(ProvidedBean.class.getCanonicalName()))
								m.invoke(bean, providedBean);
							else
								m.invoke(bean, providedBeanSingleton);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
		}
		return true;
	}

	@Override
	public Object postProcessBeforeInstantiation(Class bean, String beanName) throws BeansException {
		return null;
	}

	@Override
	public PropertyValues postProcessPropertyValues(PropertyValues pvs, PropertyDescriptor[] pds, Object bean, String beanName) throws BeansException {
		return pvs;
	}

	public void setProvidedBean(ProvidedBean providedBean) {
		this.providedBean = providedBean;
	}

	@Autowired
	public void setProvidedBeanSingleton(ProvidedBeanSingleton providedBeanSingleton) {
		this.providedBeanSingleton = providedBeanSingleton;
	}

}
