package com.hazardousholdings.unkj.auth;

interface UserStore {

	public String getHashedPassword(String username);

	public User getUser(String username);

	public User getUser(UserId userId);

	public UserId createUser(User user, String password) throws UsernameTakenException;

	public void updateUser(User user);

	public void changePassword(UserId userId, String newPassword);

	public void deleteUser(UserId userId);

}
