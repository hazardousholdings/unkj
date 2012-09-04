package com.hazardousholdings.unkj;

import com.hazardousholdings.unkj.config.Configuration;
import com.hazardousholdings.unkj.config.UnkJConfigKeys;
import com.hazardousholdings.unkj.db.JDBCConnectInfo;

public class BootstrapConfiguration {

	private JDBCConnectInfo dbInfo;

	public BootstrapConfiguration() {
		Configuration configuration = new Configuration();
		this.dbInfo = configuration.getDBConnectInfo(UnkJConfigKeys.DB);
	}

	public BootstrapConfiguration(JDBCConnectInfo unkjDB) {
		this.dbInfo = unkjDB;
	}

	public JDBCConnectInfo getDBInfo() {
		return this.dbInfo;
	}
}
