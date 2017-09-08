package com.aspectj.annotation;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class Testing {

	public static void main(String[] args){
		//Note: config files should be in the classpath and should be loaded with ClassPathApplicationXmlContext
		ApplicationContext context = new FileSystemXmlApplicationContext("src//com//aspectj//annotation//aspectjAnnotation.xml");
		
		/*
		 * Aspects give proxied objects; like ArithmaticCalculator will be proxied here.
		 * 1. Always refer to ""interface"" of the class if that class is aspected via AOP
		 * 2. Pointcut can have Direct class name; no issues there
		 * but 
		 * only interfaces should be made to call the method.
		 */
		Calculator calculator = (Calculator)context.getBean("arithmaticCalculator");
		System.out.println("Testing: " +calculator.add(5, 6));
		calculator.print();
		calculator.throwException();
	}
}
