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

	public Infrastructure(BootstrapConfiguration bootstrapConfiguration) {
		this.configuration = new Configuration();
		this.cache = new NoOpCache();

		DataSource unkjPool = new DBConnectionPool(bootstrapConfiguration.getDBInfo()).getDataSource();
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
