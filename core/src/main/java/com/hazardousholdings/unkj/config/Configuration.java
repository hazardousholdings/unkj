package com.hazardousholdings.unkj.config;

import com.hazardousholdings.unkj.cache.Cache;
import com.hazardousholdings.unkj.db.JDBCConnectInfo;

import javax.sql.DataSource;

public class Configuration {

	private ConfigurationStore configStore;

	public Configuration(DataSource dataSource, Cache cache) {
		this(new CacheConfigurationStore(cache, new DBConfigurationStore(dataSource)));
	}

	protected Configuration(ConfigurationStore configStore) {
		this.configStore = configStore;
	}

	public String get(String key) {
		return this.configStore.get(key);
	}

	private String get(String key, String suffix) {
		return get(key + "." + suffix);
	}

	public JDBCConnectInfo getDBConnectInfo(String name) {
		String driver = get(name, "driverClass");
		if(driver != null) {
			String connectString = get(name, "connectString");
			String user = get(name, "user");
			String password = get(name, "password");

			return new JDBCConnectInfo(driver, connectString, user, password);
		} else {
			return null;
		}
	}

	public void set(String key, Object value) {
		set(key, value.toString());
	}

	public void set(String key, Object value, boolean isDefault) {
		set(key, value.toString(), isDefault);
	}

	public void set(String key, String value) {
		set(key, value, false);
	}

	public void set(String key, String value, boolean isDefault) {
		this.configStore.set(key, value, isDefault);
	}

	private void set(String key, String suffix, String value, boolean isDefault) {
		set(key + "." + suffix, value, isDefault);
	}

	public void setDBConnectionInfo(String name, JDBCConnectInfo connectInfo) {
		setDBConnectionInfo(name, connectInfo, false);
	}

	public void setDBConnectionInfo(String name, JDBCConnectInfo connectInfo, boolean isDefault) {
		set(name, "driverClass", connectInfo.getDriverClassName(), isDefault);
		set(name, "connectString", connectInfo.getConnectionString(), isDefault);
		set(name, "user", connectInfo.getUser(), isDefault);
		set(name, "password", connectInfo.getPass(), isDefault);
	}
}
