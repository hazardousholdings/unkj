package com.hazardousholdings.unkj.config;

import com.hazardousholdings.unkj.db.DBStore;
import com.hazardousholdings.unkj.db.Key;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class DBConfigurationStore extends DBStore implements ConfigurationStore {

	public DBConfigurationStore(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public void set(final String key, final String value, final boolean isDefault) {
		try (Connection connection = getConnection()) {

			int originalIsolation = connection.getTransactionIsolation();
			connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

			Boolean setDefault = executeSelect(new SelectQuery<Boolean>() {
				@Override
				public String getQueryTemplate() {
					return "select \"default\" from config where \"key\" = ?";
				}

				@Override
				public void bindParameters(PreparedStatement statement) throws SQLException {
					statement.setString(1, key);
				}

				@Override
				public Boolean handleResults(ResultSet resultSet) throws SQLException {
					if(resultSet.next()) {
						return resultSet.getBoolean(1);
					} else {
						return null;
					}
				}
			});

			if(setDefault == null) {
				executeInsert(new InsertQuery<ConfigKey>() {
					@Override
					public String getQueryTemplate() {
						return "insert into config (\"key\", \"value\", \"default\") values (?, ?, ?)";
					}

					@Override
					public void bindParameters(PreparedStatement statement) throws SQLException {
						statement.setString(1, key);
						statement.setString(2, value);
						statement.setBoolean(3, isDefault);
					}

					@Override
					public ConfigKey getInsertedKey(ResultSet resultSet) throws SQLException {
						return new ConfigKey(key);
					}
				});
			} else if(setDefault == isDefault) {
				executeUpdate(new Query() {
					@Override
					public String getQueryTemplate() {
						return "update config set value = ? where key = ?";
					}

					@Override
					public void bindParameters(PreparedStatement statement) throws SQLException {
						statement.setString(1, value);
						statement.setString(2, key);
					}
				}, connection);
			}

			connection.setTransactionIsolation(originalIsolation);

		} catch (SQLException ex) {
			throw new RuntimeException("Failed to get DB connection: " + ex.getMessage(), ex);
		}
	}

	@Override
	public String get(final String key) {
		return executeSelect(new SelectQuery<String>() {
			@Override
			public String getQueryTemplate() {
				return "select value from config where key = ?";
			}

			@Override
			public void bindParameters(PreparedStatement statement) throws SQLException {
				statement.setString(1, key);
			}

			@Override
			public String handleResults(ResultSet resultSet) throws SQLException {
				if(resultSet.next()) {
					return resultSet.getString(1);
				} else {
					return null;
				}
			}
		});
	}

	public class ConfigKey extends Key<String> {
		public ConfigKey(String key) {
			super(key);
		}
	}
}
