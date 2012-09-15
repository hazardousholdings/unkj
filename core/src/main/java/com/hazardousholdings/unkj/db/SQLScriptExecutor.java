package com.hazardousholdings.unkj.db;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLScriptExecutor {

	private JDBCConnectInfo connectInfo;

	public SQLScriptExecutor(JDBCConnectInfo connectInfo) {
		this.connectInfo = connectInfo;
	}

	public void executeScript(BufferedReader script) throws IOException {
		String statementText = "";

		try(Connection connection = this.connectInfo.getConnection()) {
			connection.setAutoCommit(false);

			String line;
			while((line = script.readLine()) != null) {
				if(!line.isEmpty() && !line.trim().startsWith("--") && !line.trim().startsWith("//")) {
					statementText += "\n" + line;
				}

				if(line.trim().endsWith(";")) {
					statementText = statementText.substring(0, statementText.length() - 1);
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
