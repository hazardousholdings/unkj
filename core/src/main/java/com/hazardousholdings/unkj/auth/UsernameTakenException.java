package com.hazardousholdings.unkj.auth;

public class UsernameTakenException extends Exception {

	public UsernameTakenException(String username, Throwable ex) {
		super(username, ex);
	}

}
