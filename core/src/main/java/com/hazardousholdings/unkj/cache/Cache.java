package com.hazardousholdings.unkj.cache;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public interface Cache {

	public Object get(String key);

	public Future<Object> getAsync(String key);

	public void set(String key, Object value);

	public void set(String key, Object value, long ttl, TimeUnit ttlUnit);

}
