package com.hazardousholdings.unkj;

import com.hazardousholdings.unkj.auth.Group;
import com.hazardousholdings.unkj.auth.GroupManager;
import com.hazardousholdings.unkj.config.Configuration;
import com.hazardousholdings.unkj.config.UnkJConfigKeys;
import com.hazardousholdings.unkj.db.DBConnectionPool;
import com.hazardousholdings.unkj.db.JDBCConnectInfo;
import com.hazardousholdings.unkj.db.SQLScriptExecutor;

import javax.sql.DataSource;
import java.io.*;
import java.util.Map;
import java.util.logging.Level;

public class InfrastructureFactory {

	private BootstrapConfiguration bootstrapConfig;

	protected InfrastructureFactory(BootstrapConfiguration bootstrapConfig) {
		this.bootstrapConfig = bootstrapConfig;
	}

	public static Infrastructure init(File bootstrapConfigFile, Version appVersion) {
		BootstrapConfiguration bootstrapConfig = new BootstrapConfiguration(bootstrapConfigFile, appVersion);
		InfrastructureFactory factory = new InfrastructureFactory(bootstrapConfig);

		Infrastructure infrastructure = factory.bootstrap();
		factory.initDefaults(infrastructure);
		factory.initFromConfig(infrastructure);
		return infrastructure;
	}

	protected Infrastructure bootstrap() {
		DataSource unkjPool = new DBConnectionPool(this.bootstrapConfig.getDBInfo()).getDataSource();
		SQLScriptExecutor unkjScriptExecutor = new SQLScriptExecutor(unkjPool);

		try (InputStream in = InfrastructureFactory.class.getResourceAsStream("db-create.sql");
			 Reader reader = new InputStreamReader(in);
			 BufferedReader bufferedReader = new BufferedReader(reader)) {

			unkjScriptExecutor.executeScript(bufferedReader);
		} catch (IOException ex) {
			throw new RuntimeException("Failed to find DB create script", ex);
		}

		return new Infrastructure(this.bootstrapConfig);
	}

	protected void initDefaults(Infrastructure infrastructure) {
		Configuration config = infrastructure.getConfiguration();
		config.set(UnkJConfigKeys.LOG_LEVEL, Level.WARNING, true);
	}

	protected void initFromConfig(Infrastructure infrastructure) {
		Configuration config = infrastructure.getConfiguration();
		for(Map.Entry<String, JDBCConnectInfo> entry : this.bootstrapConfig.getDBConfigEntries().entrySet()) {
			config.setDBConnectionInfo(entry.getKey(), entry.getValue(), true);
		}
		for(Map.Entry<String, String> entry : this.bootstrapConfig.getConfigEntries().entrySet()) {
			config.set(entry.getKey(), entry.getValue(), true);
		}

		GroupManager groupManager = infrastructure.getGroupManager();
		for(String groupName : this.bootstrapConfig.getGroups()) {
			Group group = new Group(groupName);
			groupManager.addGroup(group);
		}
	}

}
