package com.aspectj.annotation;

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
public class LogCalculatorAspect {
	
	//common Pointcut; can be applied at any joinpoint for e.g. check @After and others
	@Pointcut("execution(* ArithmaticCalculator.*(..))")
	public void logAtThis(){}
	
	//Method parameter JoinPoint is optional
	@Before("execution(* ArithmaticCalculator.*(..))")
	public void logBefore(JoinPoint jp){
		System.out.println("LogCalculatorAspect: logBefore: " + jp.getSignature());
	}
	
	//option method parameter JoinPoint can be added
	//will work for both after returning as well as throwing
	@After("logAtThis()")
	public void logAfter(){
		System.out.println("LogCalculatorAspect: (after advice) logAfter");
	}
	
	@AfterReturning(pointcut="logAtThis()")
	public void logOnSuccess(){
		System.out.println("LogCalculatorAspect: (after retuning) logOnSuccess: ");
	}
	@AfterReturning(pointcut="logAtThis()",returning="result")
	public void logOnSuccess(JoinPoint jp, Object result){
		System.out.println("LogCalculatorAspect: (after retuning) logOnSuccess: "+ result);
	}
	
	//a particular argument exception or take general Exception
	//Note: exception will never be caught
	@AfterThrowing(pointcut="logAtThis()",throwing="e")
	public void logOnFailure(ArrayIndexOutOfBoundsException e){
		System.out.println("LogCalculatorAspect: (throwing advice) logOnFailure: " + e.getMessage());
	}
	
	@Around("logAtThis()")
	public Object aroundAdvice(ProceedingJoinPoint jp){
		Object result = null;
		try {
			System.out.println("LogCalculatorAspect: aroundAdvice start");
			result = jp.proceed(); // this is must
			System.out.println("LogCalculatorAspect: aroundAdvice stop: " + result);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return result;
	}
}
