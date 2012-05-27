package com.hazardousholdings.unkj.config;

import com.hazardousholdings.unkj.db.DBStore;

import javax.sql.DataSource;

class DBConfigurationStore extends DBStore implements ConfigurationStore {

	public DBConfigurationStore(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public void set(String key, String value, boolean isDefault) {

	}

	@Override
	public String get(String name) {
		return null;
	}
}
