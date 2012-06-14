package com.hazardousholdings.unkj.auth;

public class UsernameTakenException extends Exception {

	private String username;

	public UsernameTakenException(String username, Throwable ex) {
		super("Username not unique: " + username, ex);
		this.username = username;
	}

	public String getUsername() {
		return this.username;
	}

}
