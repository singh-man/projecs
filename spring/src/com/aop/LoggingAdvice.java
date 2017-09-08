package com.aop;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.ThrowsAdvice;

/**
 * An Advice
 * Note: LoggingImpl is just used to show how other things can be invoked
 * @author Acer
 *
 */
public class LoggingAdvice implements MethodBeforeAdvice, AfterReturningAdvice, ThrowsAdvice, MethodInterceptor {
	private LoggingImpl logging;
	public void setLogging(LoggingImpl logging){
		this.logging = logging;
	}

	@Override
	public void before(Method arg0, Object[] arg1, Object arg2) throws Throwable {
		System.out.println("LoggingAdvice: before");
		logging.beforeRead();// just a test of autowiring pls check app.xml
	}

	@Override
	public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
		System.out.println("LoggingAdvice: after Returning: " + returnValue);
	}

	public void afterThrowing(Throwable throwable) {
		System.out.println("LoggingAdvice: after Throwing: " + throwable);
	}

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		try {
			System.out.println("LoggingAdvice: around start:");
			Object returnValue = invocation.proceed();
			System.out.println("LoggingAdvice: around stop:" + returnValue);
			return returnValue;
		} catch (Exception throwable) {
			throw throwable;
		}
	}

}
