package com.hazardousholdings.unkj.auth;

import com.google.inject.Inject;
import com.hazardousholdings.unkj.db.DBStore;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class DBUserStore extends DBStore implements UserStore {

	@Inject
	public DBUserStore(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public User authorize(final String email, final String password) {
		return executeSelect(new SelectQuery<User>() {
			@Override
			public String getQueryTemplate() {
				return "select * from users where email = ? and password = ?";
			}

			@Override
			public void bindParameters(PreparedStatement statement) throws SQLException {
				statement.setString(1, email);
				statement.setString(2, password);
			}

			@Override
			public User handleResults(ResultSet resultSet) throws SQLException {
				return handleUser(resultSet);
			}
		});
	}

	@Override
	public User getUser(final long userId) {
		return executeSelect(new SelectQuery<User>() {
			@Override
			public String getQueryTemplate() {
				return "select * from users where id = ?";
			}

			@Override
			public void bindParameters(PreparedStatement statement) throws SQLException {
				statement.setLong(1, userId);
			}

			@Override
			public User handleResults(ResultSet resultSet) throws SQLException {
				return handleUser(resultSet);
			}
		});
	}

	@Override
	public long addUser(User user) {
		return executeInsertWithGeneratedKey(new Query() {
			@Override
			public String getQueryTemplate() {
				return "insert into users () values ()";
			}

			@Override
			public void bindParameters(PreparedStatement statement) throws SQLException {

			}
		});
	}

	@Override
	public void updateUser(User user) {
		int affectedRows = executeUpdate(new Query() {
			@Override
			public String getQueryTemplate() {
				return "update users where id = ?";
			}

			@Override
			public void bindParameters(PreparedStatement statement) throws SQLException {

			}
		});

		if(affectedRows < 1) {

		} else if(affectedRows > 1) {

		}
	}

	private User handleUser(ResultSet resultSet) throws SQLException {
		return new User();
	}
}
