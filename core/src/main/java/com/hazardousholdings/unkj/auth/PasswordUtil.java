package com.hazardousholdings.unkj.auth;

class PasswordUtil {

	public static String hash(String password) {
		return password;
	}

	public static boolean passwordMatches(String password, String hashedPassword) {
		return password.equals(hashedPassword);
	}

}
