package com.singleton;

import static org.junit.Assert.*;

import java.lang.reflect.Constructor;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SingletonFactoryTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetSingletonObject() {
		AClass a1 = SingletonFactory.getSingletonObject(AClass.class);
		AClass a2 = SingletonFactory.getSingletonObject(AClass.class);
		AClass a4 = SingletonFactory.getSingletonObject(AClass.class);
		AClass a3 = getInstance(AClass.class);
		System.out.println("" + a1 + a2 + a3 + " " + a1.hashCode() +  " " + a2.hashCode() + " " + a3.hashCode() + " " + a4.hashCode());
		System.out.println("" + a1.getI() + a2.getI() + a3.getI() + a4.getI());
		assertNotSame(a1, a3);
		assertEquals(a1, a2);
		
		BClass b1 = SingletonFactory.getSingletonObject(BClass.class);
		BClass b2 = SingletonFactory.getSingletonObject(BClass.class);
		BClass b4 = SingletonFactory.getSingletonObject(BClass.class);
		BClass b3 = getInstance(BClass.class);
		System.out.println("" + b1 + b2 + b3 + " " + b1.hashCode() +  " " + b2.hashCode() + " " + b3.hashCode() + " " + b4.hashCode());
		System.out.println("" + b1.getI() + b2.getI() + b3.getI() + b4.getI());
		assertNotSame(b1, b3);
		assertEquals(b1, b2);
		
	}

	private <T> T getInstance(Class<T> clazz) {
		Constructor<T>[] constructors = (Constructor<T>[]) clazz.getDeclaredConstructors();
		T t = null;
		for(Constructor<T> constructor : constructors) {
			constructor.setAccessible(true);
			try {
				t = constructor.newInstance(null);
				constructor.setAccessible(false);
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		return t;
	}
}
