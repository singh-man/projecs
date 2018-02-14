package com.singleton;

public class BClass {
	
	private int i = 100;

	private BClass(){
		System.out.println("BClass constructor called");
	}
	
	public int getI(){
		return i;
	}
	
	static BClass getObject() {
		System.out.println("should be called only once");
		return new BClass();
	}
}
