package com.hazardousholdings.unkj.auth;

import javax.sql.DataSource;

public class UserManager {

	private UserStore userStore;

	public UserManager(DataSource dataSource) {
		this(new DBUserStore(dataSource));
	}

	protected UserManager(UserStore userStore) {
		this.userStore = userStore;
	}

	public User authorize(String username, String password) {
		String hashedPassword = this.userStore.getHashedPassword(username);
		if(PasswordUtil.passwordMatches(password, hashedPassword)) {
			return this.userStore.getUser(username);
		} else {
			return null;
		}
	}

	public User getUser(UserId userId) {
		return this.userStore.getUser(userId);
	}

	public User getUser(String username) {
		return this.userStore.getUser(username);
	}

	public void createUser(User user, String password) throws UsernameTakenException {
		UserId id = this.userStore.createUser(user, password);
		user.setId(id);
	}

	public void updateUser(User user) {
		this.userStore.updateUser(user);
	}

	public void changePassword(UserId userId, String newPassword) {
		this.userStore.changePassword(userId, newPassword);
	}

	public void deleteUser(UserId userId) {
		this.userStore.deleteUser(userId);
	}

}
