package com.hazardousholdings.unkj.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCConnectInfo {

	private String className;
	private String connectionString;
	private String user;
	private String pass;

	public JDBCConnectInfo(String className, String connectionString, String user, String pass) {
		this.className = className;
		this.connectionString = connectionString;
		this.user = user;
		this.pass = pass;
	}

	public String getConnectionString() {
		return this.connectionString;
	}

	public String getUser() {
		return this.user;
	}

	public String getPass() {
		return this.pass;
	}

	public String getDriverClassName() {
		return this.className;
	}

	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(this.getConnectionString(), this.getUser(), this.getPass());
	}

}
