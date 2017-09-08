package com.aop.beanNameAutoProxyCreator;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Testing {

	
	public static void main(String[] args) {
		BeanFactory app = new ClassPathXmlApplicationContext("com/aop/beanNameAutoProxyCreator/appContext.xml");
		
		Resource admin = (Resource) app.getBean("admin");
		Resource employee = (Resource) app.getBean("employee");
		
		//Resource is not registered to interceptor
//		Resource student = app.getBean(Resource.class);
		
		//Interceptor called for admin and employee
		admin.setName("erics");
		String adminName = admin.getName();
		System.out.println(adminName);
		admin.printMessage();
		
		employee.printMessage();
		
		//Interceptor will not be called here
//		student.printMessage();
		
	}
}
