package com.hazardousholdings.unkj.install;

import com.hazardousholdings.unkj.BootstrapConfiguration;
import com.hazardousholdings.unkj.db.DBConnectionPool;
import com.hazardousholdings.unkj.db.SQLScriptExecutor;

import javax.sql.DataSource;
import java.io.*;

public class DatabaseCreator {

	private BootstrapConfiguration bootstrapConfig;

	public DatabaseCreator(BootstrapConfiguration bootstrapConfig) {
		this.bootstrapConfig = bootstrapConfig;
	}

	public void create() {
		DataSource unkjPool = new DBConnectionPool(this.bootstrapConfig.getDBInfo()).getDataSource();
		SQLScriptExecutor unkjScriptExecutor = new SQLScriptExecutor(unkjPool);

		try (InputStream in = DatabaseCreator.class.getResourceAsStream("/unkj-db-create.sql");
			 Reader reader = new InputStreamReader(in);
			 BufferedReader bufferedReader = new BufferedReader(reader)) {

			unkjScriptExecutor.executeScript(bufferedReader);
		} catch (IOException ex) {
			throw new RuntimeException("Failed to find DB create script", ex);
		}
	}

}
