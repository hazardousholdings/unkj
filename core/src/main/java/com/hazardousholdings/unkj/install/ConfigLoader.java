package com.hazardousholdings.unkj.install;

import com.hazardousholdings.unkj.Infrastructure;
import com.hazardousholdings.unkj.auth.Group;
import com.hazardousholdings.unkj.auth.GroupManager;
import com.hazardousholdings.unkj.config.Configuration;
import com.hazardousholdings.unkj.config.UnkJConfigKeys;
import com.hazardousholdings.unkj.db.JDBCConnectInfo;

import java.util.Map;
import java.util.logging.Level;

public class ConfigLoader {

	private Infrastructure infrastructure;

	public ConfigLoader(Infrastructure infrastructure) {
		this.infrastructure = infrastructure;
	}

	public void loadDefaults() {
		Configuration config = infrastructure.getConfiguration();
		config.set(UnkJConfigKeys.LOG_LEVEL, Level.WARNING, true);
	}

	public void loadFromFile(ApplicationConfiguration appConfig) {
		Configuration config = this.infrastructure.getConfiguration();
		for(Map.Entry<String, JDBCConnectInfo> entry : appConfig.getDBConfigEntries().entrySet()) {
			config.setDBConnectionInfo(entry.getKey(), entry.getValue(), true);
		}
		for(Map.Entry<String, String> entry : appConfig.getConfigEntries().entrySet()) {
			config.set(entry.getKey(), entry.getValue(), true);
		}

		GroupManager groupManager = infrastructure.getGroupManager();
		for(String groupName : appConfig.getGroups()) {
			Group group = new Group(groupName);
			groupManager.addGroup(group);
		}
	}

}
