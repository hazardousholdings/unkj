package com.hazardousholdings.unkj;

import com.hazardousholdings.unkj.auth.GroupManager;
import com.hazardousholdings.unkj.auth.UserManager;
import com.hazardousholdings.unkj.cache.Cache;
import com.hazardousholdings.unkj.cache.NoOpCache;
import com.hazardousholdings.unkj.config.Configuration;
import com.hazardousholdings.unkj.db.DBConnectionPool;

import javax.sql.DataSource;

public class Infrastructure {

	private UserManager userManager;
	private GroupManager groupManager;
	private Configuration configuration;
	private Cache cache;

	public Infrastructure(BootstrapConfiguration bootstrapConfig) {
		DataSource unkjPool = new DBConnectionPool(bootstrapConfig.getDBInfo()).getDataSource();

		Configuration cacheConfiguration = new Configuration(unkjPool, new NoOpCache());
		this.cache = new NoOpCache();

		this.configuration = new Configuration(unkjPool, this.cache);
		this.userManager = new UserManager(unkjPool);
		this.groupManager = new GroupManager(unkjPool);
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
