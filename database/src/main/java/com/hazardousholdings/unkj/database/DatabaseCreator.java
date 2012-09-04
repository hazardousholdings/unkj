package com.hazardousholdings.unkj.database;

import com.hazardousholdings.unkj.BootstrapConfiguration;
import com.hazardousholdings.unkj.db.DBConnectionPool;
import com.hazardousholdings.unkj.db.JDBCConnectInfo;
import com.hazardousholdings.unkj.db.SQLScriptExecutor;

import javax.sql.DataSource;
import java.io.*;

public class DatabaseCreator {

	private JDBCConnectInfo connectInfo;

	public DatabaseCreator(BootstrapConfiguration bootstrapConfig) {
		this.connectInfo = bootstrapConfig.getDBInfo();
	}

	public void create() {
		DataSource unkjPool = new DBConnectionPool(this.connectInfo).getDataSource();
		SQLScriptExecutor unkjScriptExecutor = new SQLScriptExecutor(unkjPool);

		try (InputStream in = DatabaseCreator.class.getResourceAsStream("/unkj-db-create.sql");
			 Reader reader = new InputStreamReader(in);
			 BufferedReader bufferedReader = new BufferedReader(reader)) {

			unkjScriptExecutor.executeScript(bufferedReader);
		} catch (IOException ex) {
			throw new RuntimeException("Failed to find DB create script", ex);
		}
	}

	public static void main(String args[]) {
		DatabaseCreator creator = new DatabaseCreator(new BootstrapConfiguration());
		creator.create();
	}

}
