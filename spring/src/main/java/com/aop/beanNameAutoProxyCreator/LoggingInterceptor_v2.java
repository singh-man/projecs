package com.aop.beanNameAutoProxyCreator;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class LoggingInterceptor_v2 implements MethodInterceptor {

	@Override
	public Object invoke(MethodInvocation method) throws Throwable {
		String classMethodIdentifier = method.getMethod().getDeclaringClass() + "." + method.getMethod().getName();
		System.out.println("by v2 : " + classMethodIdentifier + " starts");
		Object result = method.proceed();
		System.out.println("by v2 : " + classMethodIdentifier + " finished");
		return result;
	}
}
