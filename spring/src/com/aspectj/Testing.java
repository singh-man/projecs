package com.aspectj;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class Testing {

	public static void main(String[] args){
		//Note: config files should be in the classpath and should be loaded with ClassPathApplicationXmlContext
		ApplicationContext context = new FileSystemXmlApplicationContext("src//com//aspectj//aspectj.xml");
		Calculator calculator = (Calculator) context.getBean("arithmaticCalculator");
		System.out.println("Testing: " + calculator.add(7, 6));
		calculator.print();;
		calculator.throwException();
	}
}
