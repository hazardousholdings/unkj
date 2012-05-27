package com.hazardousholdings.unkj.db;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLScriptExecutor {

	private DataSource dataSource;

	public SQLScriptExecutor(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void executeScript(BufferedReader script) throws IOException {
		String statementText = "";

		try(Connection connection = this.dataSource.getConnection()) {
			String line;
			while((line = script.readLine()) != null) {
				if(!line.isEmpty() && !line.trim().startsWith("--") && !line.trim().startsWith("//")) {
					statementText += "\n" + line;
				}

				if(line.trim().endsWith(";")) {
					statementText = statementText.substring(0, line.length() - 1);
					try(Statement statement = connection.createStatement()) {
						statement.executeUpdate(statementText);
					}

					statementText = "";
				}
			}

			connection.commit();
		} catch (SQLException ex) {
			throw new RuntimeException("Failed to execute statement as part of script: " + statementText, ex);
		}
	}



}
