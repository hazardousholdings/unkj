package com.hazardousholdings.unkj.config;

import com.hazardousholdings.unkj.cache.Cache;

public class CacheConfigurationStore implements ConfigurationStore {

	private Cache cache;
	private ConfigurationStore backingStore;

	public CacheConfigurationStore(Cache cache, ConfigurationStore backingStore) {
		this.cache = cache;
		this.backingStore = backingStore;
	}

	@Override
	public String get(String key) {
		String cachedValue = (String) this.cache.get("config." + key);
		if(cachedValue != null) {
			return cachedValue;
		} else {
			return this.backingStore.get(key);
		}
	}

	@Override
	public void set(String key, String value, boolean isDefault) {
		this.backingStore.set(key, value, isDefault);
		this.cache.set("config." + key, value);
	}
}
