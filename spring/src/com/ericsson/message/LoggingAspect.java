package com.ericsson.message;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class LoggingAspect {

	//common Pointcut; can be applied at any joinpoint for e.g. check @After and others
	
	/*
	 * Better to keep the Aspect where the class resides
	 * e.g.
	 * Message resides in package com.ericsson.message so as its Aspect LoggingAspect
	 */
	@Pointcut("execution(* Message.*(..))")
	public void logAtThis(){}

	//Method parameter JoinPoint is optional
	//@Before("execution(* SubjectImpl_2.*(..))")
	@Before("logAtThis()")
	public void logBefore(JoinPoint jp){
		System.out.println("LoggingAspect: logBefore: " + jp.getSignature());
	}

	//optional method parameter JoinPoint can be added
	//will work for both after returning as well as throwing
	@After("logAtThis()")
	public void logAfter(){
		System.out.println("LoggingAspect: (after advice) logAfter");
	}

	@AfterReturning(pointcut="logAtThis()")
	public void logOnSuccess(){
		System.out.println("LoggingAspect: (after retuning) logOnSuccess: ");
	}
	//Another variant of ArterReutrning
	@AfterReturning(pointcut="logAtThis()",returning="result")
	public void logOnSuccess(JoinPoint jp, Object result){
		System.out.println("LoggingAspect: (after retuning with returning value) logOnSuccess: "+ result);
	}

	//a particular argument exception or take general Exception
	//Note: exception will never be caught
	@AfterThrowing(pointcut="logAtThis()",throwing="e")
	public void logOnFailure(ArrayIndexOutOfBoundsException e){
		System.out.println("LoggingAspect: (throwing advice) logOnFailure: " + e.getMessage());
	}

	@Around("logAtThis()")
	public Object aroundAdvice(ProceedingJoinPoint jp){
		Object result = null;
		try {
			System.out.println("LoggingAspect: aroundAdvice start");
			result = jp.proceed(); // this is must
			System.out.println("LoggingAspect: aroundAdvice stop: " + result);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return result;
	}

}
