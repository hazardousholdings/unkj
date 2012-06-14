package com.hazardousholdings.unkj.auth;

interface UserStore {

	public User authorize(String username, String password);

	public User getUser(UserId userId);

	public long createUser(User user, String password);

	public void updateUser(User user);

	public void changePassword(UserId userId, String newPassword);

	public void deleteUser(UserId userId);

}
