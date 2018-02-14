package com.state;

public class Testing {

	public static void main(String[] args) {
		testEntity();
		
	}

	private static void testEntity() {
		Entity entity = new Entity();
		System.out.println(entity.getState());
		entity.proceed();
		System.out.println(entity.getState());
		entity.proceed();
		System.out.println(entity.getState());
		entity.proceed();
		System.out.println(entity.getState());
	}
}
