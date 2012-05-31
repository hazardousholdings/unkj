package com.hazardousholdings.unkj.db;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

public class DBConnectionPool {

	private ComboPooledDataSource dataSource;

	public DBConnectionPool(JDBCConnectInfo connectInfo) {
		try {
			this.dataSource = new ComboPooledDataSource();
			this.dataSource.setDriverClass(connectInfo.getDriverClassName()); //loads the jdbc driver
			this.dataSource.setJdbcUrl(connectInfo.getConnectionString());
			this.dataSource.setUser(connectInfo.getUser());
			this.dataSource.setPassword(connectInfo.getPass());

			this.dataSource.setAcquireIncrement(3);
			this.dataSource.setAcquireRetryAttempts(5);
			this.dataSource.setTestConnectionOnCheckout(true);
		} catch (PropertyVetoException ex) {
			throw new RuntimeException("Failed to instantiate data source: " + ex.getMessage(), ex);
		}

	}

	public DataSource getDataSource() {
		return this.dataSource;
	}
}
