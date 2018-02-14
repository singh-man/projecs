package com.cache;

import java.util.Map;

import com.cache.MapCachingStrategy.Payload;

public class MapCachingStrategy implements CachingStrategy<Payload> {

	private Map<String, Payload> map;

	MapCachingStrategy(Map<String, Payload> map) {
		this.map = map;
	}

	protected Payload cacheIt(String key, Payload payload) {
		return map.put(key, payload);
	}

	protected Payload load(String key) {
		return map.get(key);
	}

	@Override
	public Object get(String key) {
		Payload load = load(key);
		return load.o;
	}

	@Override
	public Payload add(String key, Object o, int secondsToLive) {
		Payload load = load(key);
		if(load != null) {
			load.o = o;
			load.secondsToLive = secondsToLive;
		} else
			load = new Payload(o, secondsToLive);
		return cacheIt(key, load);
	}

	@Override
	public Payload evict(String key) {
		return map.remove(key);
	}

	@Override
	public boolean isCached(String key) {
		return map.containsKey(key);
	}

	@Override
	public boolean isExpired(String key) {
		Payload load = load(key);
		return load.secondsToLive == -1 ? false: (load.secondsToLive > System.currentTimeMillis()/1000) ? false : true;
	}

	public class Payload {
		Object o;
		int secondsToLive;

		public Payload(Object o, int secondsToLive) {
			super();
			this.o = o;
			int secs = (int) (System.currentTimeMillis()/1000);
			this.secondsToLive = secondsToLive == -1 ? secondsToLive : secs + secondsToLive;
		}
	}



}
