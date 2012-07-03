package com.hazardousholdings.unkj.db;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class DBStore {

	private DataSource dataSource;

	public DBStore(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	protected Connection getConnection() throws SQLException {
		return this.dataSource.getConnection();
	}

	protected <T> T executeSelect(SelectQuery<T> select) {
		try (Connection connection = getConnection()) {
			return executeSelect(select, connection);
		} catch (SQLException ex) {
			throw new RuntimeException("Failed to get DB connection: " + ex.getMessage(), ex);
		}
	}

	protected <T> T executeSelect(SelectQuery<T> select, Connection connection) {
		try (PreparedStatement statement = connection.prepareStatement(select.getQueryTemplate())) {

			select.bindParameters(statement);
			try (ResultSet result = statement.executeQuery()) {
				return select.handleResults(result);
			}

		} catch (SQLException ex) {
			throw new RuntimeException("Failed to execute select query: " + select.getQueryTemplate(), ex);
		}
	}

	protected int executeUpdate(Query update) {
		try (Connection connection = getConnection()) {
			return executeUpdate(update, connection);
		} catch (SQLException ex) {
			throw new RuntimeException("Failed to get DB connection: " + ex.getMessage(), ex);
		}
	}

	protected int executeUpdate(Query update, Connection connection) {
		try (PreparedStatement statement = connection.prepareStatement(update.getQueryTemplate())) {

			update.bindParameters(statement);
			return statement.executeUpdate();

		} catch (SQLException ex) {
			throw new RuntimeException("Failed to execute update statement: " + update.getQueryTemplate(), ex);
		}
	}

	protected <K extends Key> K executeInsert(InsertQuery<K> insert) {
		try (Connection connection = getConnection()) {

			return executeInsert(insert, connection);

		} catch (SQLException ex) {
			throw new RuntimeException("Failed to get DB connection: " + ex.getMessage(), ex);
		}
	}

	protected <K extends Key> K executeInsert(InsertQuery<K> insert, Connection connection) {
		try (PreparedStatement statement = connection.prepareStatement(insert.getQueryTemplate(), PreparedStatement.RETURN_GENERATED_KEYS)) {

			insert.bindParameters(statement);
			statement.executeUpdate();
			try (ResultSet resultSet = statement.getGeneratedKeys()) {
				return insert.getInsertedKey(resultSet);
			}

		} catch (SQLException ex) {
			throw new RuntimeException("Failed to execute insert statement: " + insert.getQueryTemplate(), ex);
		}
	}

	protected void executeUpdateOrInsert(Query update, InsertQuery<?> insert) {
		try (Connection connection = getConnection()) {

			int originalIsolation = connection.getTransactionIsolation();
			connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

			int affectedRows = executeUpdate(update, connection);
			if(affectedRows == 0) {
				executeInsert(insert);
			}

			connection.setTransactionIsolation(originalIsolation);

		} catch (SQLException ex) {
			throw new RuntimeException("Failed to get DB connection: " + ex.getMessage(), ex);
		}
	}

	protected interface Query {
		public String getQueryTemplate();
		public void bindParameters(PreparedStatement statement) throws SQLException;
	}

	protected interface SelectQuery<T> extends Query {
		public T handleResults(ResultSet resultSet) throws SQLException;
	}

	public interface InsertQuery<K extends Key> extends Query {
		public K getInsertedKey(ResultSet resultSet) throws SQLException;
	}


}
