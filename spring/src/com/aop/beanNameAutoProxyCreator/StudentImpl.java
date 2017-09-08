package com.aop.beanNameAutoProxyCreator;

public class StudentImpl implements Resource {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void printMessage() {
		System.out.println("I am a student");
	}
}
