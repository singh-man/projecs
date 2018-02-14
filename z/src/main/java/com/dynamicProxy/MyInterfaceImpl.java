package com.dynamicProxy;

public class MyInterfaceImpl implements MyInterface, MyInterface2 {

	public void print() {
		System.out.println("class printing");
		
	}

	public Object getValue(int i) {
		return "impl class : " + i;
	}
	
	public void iPrint() {
		System.out.println("i print");;
	}
	public Object getIValue() {
		return "i value";
	}

}
