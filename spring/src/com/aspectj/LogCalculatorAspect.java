package com.aspectj;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

public class LogCalculatorAspect {
	
	//Method parameter JoinPoint is optional
	//Note: no reference of any method parameter in xml file
	public void logBefore(JoinPoint jp){
		System.out.println("LogCalculatorAspect: before: " + jp.getSignature());
	}
	
	public void logAfter(){
		System.out.println("LogCalculatorAspect: logafter");
	}
	public void logOnSuccess_1(){
		System.out.println("LogCalculatorAspect: logOnSuccess_1 (after retuning)");
	}

	public void logOnSuccess_2(JoinPoint jp, Object result){
		System.out.println("LogCalculatorAspect: logOnSuccess_2 (after retuning) "+ result);
	}
	
	//a particular argument exception or take general Exception
	//Note: exception will never be caught
	public void logOnFailure(){
		System.out.println("LogCalculatorAspect: logOnFailure (throwing advice) ");
	}
	
	public Object aroundAdvice(ProceedingJoinPoint jp){
		Object result = null;
		try {
			System.out.println("LogCalculatorAspect: aroundAdvice start");
			result = jp.proceed(); //this is a must
			System.out.println("LogCalculatorAspect: aroundAdvice stop: " + result);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return result;
	}
}
