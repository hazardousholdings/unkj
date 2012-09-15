package com.hazardousholdings.unkj;

import com.hazardousholdings.unkj.auth.GroupManager;
import com.hazardousholdings.unkj.auth.UserManager;
import com.hazardousholdings.unkj.cache.Cache;
import com.hazardousholdings.unkj.cache.NoOpCache;
import com.hazardousholdings.unkj.config.Configuration;
import com.hazardousholdings.unkj.config.UnkJConfigKeys;
import com.hazardousholdings.unkj.db.DBConnectionPool;

import javax.sql.DataSource;

public class Infrastructure {

	private DataSource defaultPool;
	private UserManager userManager;
	private GroupManager groupManager;
	private Configuration configuration;
	private Cache cache;

	public Infrastructure() {
		this.configuration = new Configuration();
		this.cache = new NoOpCache();

		this.defaultPool = new DBConnectionPool(this.configuration.getDBConnectInfo(UnkJConfigKeys.DEFAULT_DB)).getDataSource();
		this.userManager = new UserManager(this.defaultPool);
		this.groupManager = new GroupManager(this.defaultPool);
	}

	public DataSource getDefaultDBPool() {
		return this.defaultPool;
	}

	public Configuration getConfiguration() {
		return this.configuration;
	}

	public UserManager getUserManager() {
		return this.userManager;
	}

	public GroupManager getGroupManager() {
		return this.groupManager;
	}

	public Cache getCache() {
		return this.cache;
	}

}
