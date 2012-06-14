package com.hazardousholdings.unkj.auth;

import org.junit.Test;

import static junit.framework.Assert.*;

public class PasswordUtilTest {

	@Test
	public void hash() {
		final String password = "password";
		final String hashedPassword = PasswordUtil.hash(password);

		assertNotNull(hashedPassword);
		assertFalse(password.equals(hashedPassword));
	}

	@Test
	public void check() {
		final String password = "password";
		final String hashedPassword = PasswordUtil.hash(password);

		assertTrue(PasswordUtil.passwordMatches(password, hashedPassword));
	}

}
