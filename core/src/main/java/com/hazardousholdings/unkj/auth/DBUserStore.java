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
	public User authorize(final String username, final String password) {
		return executeSelect(new SelectQuery<User>() {
			@Override
			public String getQueryTemplate() {
				return "select id, username, password, email, phone from users where username = ?";
			}

			@Override
			public void bindParameters(PreparedStatement statement) throws SQLException {
				statement.setString(1, username);
			}

			@Override
			public User handleResults(ResultSet resultSet) throws SQLException {
				if(resultSet.next()) {
					String hashedPassword = resultSet.getString("password");
					if(PasswordUtil.passwordMatches(password, hashedPassword)) {
						return handleUser(resultSet);
					} else {
						return null;
					}
				} else {
					return null;
				}
			}
		});
	}

	@Override
	public User getUser(final UserId userId) {
		return executeSelect(new SelectQuery<User>() {
			@Override
			public String getQueryTemplate() {
				return "select id, username, email, phone from users where id = ?";
			}

			@Override
			public void bindParameters(PreparedStatement statement) throws SQLException {
				statement.setLong(1, userId.getLong());
			}

			@Override
			public User handleResults(ResultSet resultSet) throws SQLException {
				if(resultSet.next()) {
					return handleUser(resultSet);
				} else {
					return null;
				}
			}
		});
	}

	@Override
	public long createUser(final User user, final String password) {
		final String hashedPassword = PasswordUtil.hash(password);
		return executeInsertWithGeneratedKey(new Query() {
			@Override
			public String getQueryTemplate() {
				return "insert into users (username, password, email, phone) values (?, ?, ?, ?)";
			}

			@Override
			public void bindParameters(PreparedStatement statement) throws SQLException {
				statement.setString(1, user.getUsername());
				statement.setString(2, hashedPassword);
				statement.setString(3, user.getEmail());
				statement.setString(4, user.getPhone());
			}
		});
	}

	@Override
	public void updateUser(final User user) {
		executeUpdate(new Query() {
			@Override
			public String getQueryTemplate() {
				return "update users set email = ?, phone = ? where id = ?";
			}

			@Override
			public void bindParameters(PreparedStatement statement) throws SQLException {
				statement.setString(1, user.getEmail());
				statement.setString(2, user.getPhone());
				statement.setLong(3, user.getId().getLong());
			}
		});
	}

	public void changePassword(final UserId userId, String newPassword) {
		final String hashedPassword = PasswordUtil.hash(newPassword);
		executeUpdate(new Query() {
			@Override
			public String getQueryTemplate() {
				return "update users set password = ? where id = ?";
			}

			@Override
			public void bindParameters(PreparedStatement statement) throws SQLException {
				statement.setString(1, hashedPassword);
				statement.setLong(2, userId.getLong());
			}
		});
	}

	@Override
	public void deleteUser(final UserId userId) {
		executeUpdate(new Query() {
			@Override
			public String getQueryTemplate() {
				return "delete from users where id = ?";
			}

			@Override
			public void bindParameters(PreparedStatement statement) throws SQLException {
				statement.setLong(1, userId.getLong());
			}
		});
	}

	private User handleUser(ResultSet resultSet) throws SQLException {
		return new User(
				new UserId(resultSet.getLong("id")),
				resultSet.getString("username"),
				resultSet.getString("email"),
				resultSet.getString("phone"));
	}
}
