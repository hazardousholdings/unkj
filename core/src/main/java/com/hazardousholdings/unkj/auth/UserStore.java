package com.hazardousholdings.unkj.auth;

interface UserStore {

	public User authorize(String user, String password);

	public User getUser(long userId);
	public long addUser(User user);
	public void updateUser(User user);

}
