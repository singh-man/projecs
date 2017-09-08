package com.cache;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class CacheableInterceptor {

	@Autowired
	private CachingStrategy<?> cache;

	@Around("@annotation(com.cache.Cacheable)")
	public Object doCache(ProceedingJoinPoint jp) throws Throwable {
		Object result = null;

		// Gives method of declared type i.e. of interface
		Method method =((MethodSignature)jp.getSignature()).getMethod();

		Class<?> glass = jp.getTarget().getClass();
		Cacheable annotation = glass.getMethod(method.getName(), method.getParameterTypes()).getAnnotation(Cacheable.class);

		String key = annotation.value();
		boolean evict = annotation.evict();
		int secondsToLive = annotation.secondsToLive();

		if(evict) {
			cache.evict(key);
			result = jp.proceed();
		}
		else if(cache.isCached(key) && !cache.isExpired(key)) {
			result = cache.get(key);
		} else {
			result = jp.proceed();
			if(result != null)
				cache.add(key, result, secondsToLive);
		}

		return result;
	}



}
