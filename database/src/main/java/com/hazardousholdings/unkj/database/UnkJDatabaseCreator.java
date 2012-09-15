package com.hazardousholdings.unkj.database;

import com.hazardousholdings.unkj.config.Configuration;
import com.hazardousholdings.unkj.config.UnkJConfigKeys;
import com.hazardousholdings.unkj.db.DBConnectionPool;
import com.hazardousholdings.unkj.db.JDBCConnectInfo;
import com.hazardousholdings.unkj.db.SQLScriptExecutor;

import javax.sql.DataSource;
import java.io.*;

public class UnkJDatabaseCreator {

	private JDBCConnectInfo connectInfo;

	public UnkJDatabaseCreator(Configuration configuration) {
		this.connectInfo = configuration.getDBConnectInfo(UnkJConfigKeys.DEFAULT_DB);
	}

	public void create() {
		DataSource unkjPool = new DBConnectionPool(this.connectInfo).getDataSource();
		SQLScriptExecutor unkjScriptExecutor = new SQLScriptExecutor(unkjPool);

		try (InputStream in = UnkJDatabaseCreator.class.getResourceAsStream("/unkj-db-create.sql");
			 Reader reader = new InputStreamReader(in);
			 BufferedReader bufferedReader = new BufferedReader(reader)) {

			unkjScriptExecutor.executeScript(bufferedReader);
		} catch (IOException ex) {
			throw new RuntimeException("Failed to find DB create script", ex);
		}
	}

	public static void main(String args[]) {
		Configuration configuration = new Configuration();
		UnkJDatabaseCreator creator = new UnkJDatabaseCreator(configuration);
		creator.create();
	}

}
