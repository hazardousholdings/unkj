package com.hazardousholdings.unkj;

import com.hazardousholdings.unkj.auth.GroupManager;
import com.hazardousholdings.unkj.auth.UserManager;
import com.hazardousholdings.unkj.cache.Cache;
import com.hazardousholdings.unkj.cache.NoOpCache;
import com.hazardousholdings.unkj.config.Configuration;
import com.hazardousholdings.unkj.db.DBConnectionPool;

public class Infrastructure {

	private UserManager userManager;
	private GroupManager groupManager;
	private Configuration configuration;
	private Cache cache;

	Infrastructure(BootstrapConfiguration bootstrapConfiguration) {
		DBConnectionPool unkjPool = new DBConnectionPool(bootstrapConfiguration.getDBInfo());

		this.userManager = new UserManager(unkjPool.getDataSource());
		this.groupManager = new GroupManager(unkjPool.getDataSource());
		this.configuration = new Configuration(unkjPool.getDataSource());
		this.cache = new NoOpCache();
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
