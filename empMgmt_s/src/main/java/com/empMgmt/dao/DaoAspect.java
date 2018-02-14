package com.empMgmt.dao;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.dao.EmptyResultDataAccessException;

@Aspect
public class DaoAspect {

	//common Pointcut; can be applied at any joinpoint for e.g. check @After and others
	@Pointcut("execution(* LoginDao.*(..))")
	public void aspectThis(){}

	@Around("aspectThis()")
	public Object around(ProceedingJoinPoint jp) {
		Object result = null;
		try {
			result = jp.proceed(); // this is must
		} catch (Throwable e) {
			if(e instanceof EmptyResultDataAccessException)
				;
			else
				throw new RuntimeException(e);
		}
		return result;
	}

	//@AfterThrowing(pointcut="logAtThis()",throwing="e")
	public <T> T logOnFailure(Exception e){
		System.out.println("Advice handled " + e.getMessage());
		return null;
	}
}
