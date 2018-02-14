package com.dynamicProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class Testing {

	public static void main(String[] args) {
		
		// impl object need to be passed while creating the handler object
		InvocationHandler handler = new MyInvocationHandler(new MyInterfaceImpl());	

		// impl class is the impl of interface arrays passed here
		// loader is just a representation of this class loader can be many diff things in same pkg
		Object proxy = Proxy.newProxyInstance(
				Testing.class.getClassLoader(),
				new Class[] { MyInterface.class, MyInterface2.class },
				handler);
		
		MyInterface myInterface = (MyInterface) proxy;

		myInterface.print();
		System.out.println(myInterface.getValue(20));

		MyInterface2 myInterface2 = (MyInterface2) proxy;
		myInterface2.iPrint();
		System.out.println(myInterface2.getIValue());

	}
}
