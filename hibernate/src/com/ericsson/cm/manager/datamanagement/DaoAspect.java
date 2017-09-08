package com.ericsson.cm.manager.datamanagement;

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
public class DaoAspect {
    //common Pointcut; can be applied at any joinpoint for e.g. check @After and others
    @Pointcut("execution(* com.ericsson.cm.manager.datamanagement.dao.*DAO.*(..))")
    public void logAtThis(){}
    
    //Method parameter JoinPoint is optional
    /*@Before("execution(* com.bean.*Bean.*(..))")
    public void logBefore(JoinPoint jp){
        System.out.println("DaoAspect: logBefore: " + jp.getSignature());
    }*/
    
    //option method parameter JoinPoint can be added
    //will work for both after returning as well as throwing
    @After("logAtThis()")
    public void logAfter(){
        System.out.println("DaoAspect: (after advice) logAfter");
    }
    
    @AfterReturning(pointcut="logAtThis()")
    public void logOnSuccess(){
        System.out.println("DaoAspect: (after retuning) logOnSuccess: ");
    }
    @AfterReturning(pointcut="logAtThis()",returning="result")
    public void logOnSuccess(JoinPoint jp, Object result){
        System.out.println("DaoAspect: (after retuning) logOnSuccess: "+ result);
    }
    
    //a particular argument exception or take general Exception
    //Note: exception will never be caught
    @AfterThrowing(pointcut="logAtThis()",throwing="e")
    public void logOnFailure(ArrayIndexOutOfBoundsException e){
        System.out.println("DaoAspect: (throwing advice) logOnFailure: " + e.getMessage());
    }
    
    @Around("logAtThis()")
    public Object aroundAdvice(ProceedingJoinPoint jp){
        Object result = null;
        try {
            System.out.println("DaoAspect: aroundAdvice start");
            result = jp.proceed(); // this is must
            System.out.println("DaoAspect: aroundAdvice stop: " + result);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return result;
    }
}
