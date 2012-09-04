package com.hazardousholdings.unkj.config;

class EnvConfiguration implements ConfigurationStore {

	public EnvConfiguration() {
	}

	@Override
	public String get(final String key) {
		String property = System.getProperty(key);
		if(property == null || property.isEmpty()) {
			return System.getenv(key);
		} else {
			return property;
		}
	}
}
