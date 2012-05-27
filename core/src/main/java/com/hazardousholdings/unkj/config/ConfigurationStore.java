package com.hazardousholdings.unkj.config;

interface ConfigurationStore {

	public String get(String key);
	public void set(String key, String value, boolean isDefault);

}
