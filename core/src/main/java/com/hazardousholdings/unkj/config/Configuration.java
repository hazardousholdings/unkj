package com.hazardousholdings.unkj.config;

import com.hazardousholdings.unkj.db.JDBCConnectInfo;

public class Configuration {

	private ConfigurationStore configStore;

	public Configuration() {
		this(new EnvConfiguration());
	}

	protected Configuration(ConfigurationStore configStore) {
		this.configStore = configStore;
	}

	public String get(String key) {
		return this.configStore.get(key);
	}

	private String get(String key, String suffix) {
		return get(key + "_" + suffix);
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
}
