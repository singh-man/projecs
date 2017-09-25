package com.singleton;

public class AClass {
	
	private int i = 10;

	private AClass(){
		System.out.println("AClass constructor called");
	}
	
	public int getI(){
		return i;
	}
	static AClass getObject() {
		System.out.println("should be called only once");
		return new AClass();
	}
}
