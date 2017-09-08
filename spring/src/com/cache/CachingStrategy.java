package com.cache;

public interface CachingStrategy<T> {

	public Object get(String key);

	public T add(String key, Object o, int secondsToLive);
	
	public T evict(String key);
	
	public boolean isExpired(String key);
	
	public boolean isCached(String key);

}
