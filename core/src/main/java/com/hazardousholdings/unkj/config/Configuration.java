package com.hazardousholdings.unkj.config;

import com.hazardousholdings.unkj.db.JDBCConnectInfo;

import java.net.URI;

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

	public JDBCConnectInfo getDBConnectInfo(String key) {
		try {
			URI databaseUrl = new URI(get(key)); // postgres://username:password@host:port/database_name
			String username = databaseUrl.getUserInfo().split(":")[0];
			String password = databaseUrl.getUserInfo().split(":")[1];
			String connectString = "jdbc:postgresql://" + databaseUrl.getHost() + databaseUrl.getPath() + ":" + databaseUrl.getPort();

			return new JDBCConnectInfo(org.postgresql.Driver.class.getName(), connectString, username, password);
		} catch (Exception ex) {
			return null;
		}
	}
}
