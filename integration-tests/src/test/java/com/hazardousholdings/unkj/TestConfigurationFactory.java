package com.hazardousholdings.unkj;

import com.hazardousholdings.unkj.db.JDBCConnectInfo;

import java.io.IOException;

public class TestConfigurationFactory {

	public static BootstrapConfiguration getBootstrapConfiguration(final boolean fresh) throws IOException {
		if(fresh) {
			return new BootstrapConfiguration(new JDBCConnectInfo(
					org.postgresql.Driver.class.getName(),
					"jdbc:postgresql://localhost:5432/unkjtesttemp",
					"unkjtest",
					"unkjtest"));
		} else {
			return new BootstrapConfiguration(new JDBCConnectInfo(
					org.postgresql.Driver.class.getName(),
					"jdbc:postgresql://localhost:5432/unkjtest",
					"unkjtest",
					"unkjtest"));
		}
	}
}
