package com.hazardousholdings.unkj.cache;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class NoOpCache implements Cache {

	@Override
	public Object get(String key) {
		return null;
	}

	@Override
	public Future<Object> getAsync(String key) {
		return null;
	}

	@Override
	public void set(String key, Object value) {
	}

	@Override
	public void set(String key, Object value, long ttl, TimeUnit ttlUnit) {
	}
}
