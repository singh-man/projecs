package com.singleton;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public class SingletonFactory {

	private static Map<String, Object> singletonMap;

	static {
		singletonMap = new HashMap<String, Object>();
	}

	public static <T> T getSingletonObject(Class<T> clazz) {
		if(singletonMap.containsKey(clazz.getName()))
			return (T) singletonMap.get(clazz.getName());
		else {
			Constructor[] constructors = clazz.getDeclaredConstructors();
			for(Constructor constructor : constructors) {
				constructor.setAccessible(true);
				try {
					singletonMap.put(clazz.getName(), constructor.newInstance(null));
					constructor.setAccessible(false);
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
		}
		return (T) singletonMap.get(clazz.getName());

	}
}

