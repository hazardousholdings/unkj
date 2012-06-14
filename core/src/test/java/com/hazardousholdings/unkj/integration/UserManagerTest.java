package com.hazardousholdings.unkj.integration;

import com.hazardousholdings.unkj.TestInfrastructureFactory;
import com.hazardousholdings.unkj.auth.User;
import com.hazardousholdings.unkj.auth.UserManager;
import com.hazardousholdings.unkj.auth.UsernameTakenException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static junit.framework.Assert.*;

public class UserManagerTest {

	private static UserManager userManager = TestInfrastructureFactory.get().getUserManager();
	private static User existingUser = new User("existing", "existing@example.com", "12345678900");

	@BeforeClass
	public static void beforeClass() throws UsernameTakenException {
		userManager.createUser(existingUser, "password");
	}

	@AfterClass
	public static void afterClass() {
		userManager.deleteUser(existingUser.getId());
	}

	@Test
	public void userCreateUpdateGetAndDelete() throws UsernameTakenException {
		User newUser = new User("create", "test@example.com", "12345678900");
		assertNull(newUser.getId());

		try {
			userManager.createUser(newUser, "password");
			assertNotNull(newUser.getId());

			User userById = userManager.getUser(newUser.getId());
			assertEquals(newUser.getId(), userById.getId());
			assertEquals(newUser.getUsername(), userById.getUsername());
			assertEquals(newUser.getEmail(), userById.getEmail());
			assertEquals(newUser.getPhone(), userById.getPhone());

			newUser.setEmail("test2@example.com");
			userManager.updateUser(newUser);

			User userByName = userManager.getUser(newUser.getUsername());
			assertEquals(newUser.getId(), userByName.getId());
			assertEquals(newUser.getUsername(), userByName.getUsername());
			assertEquals(newUser.getEmail(), userByName.getEmail());
			assertEquals(newUser.getPhone(), userByName.getPhone());
		} finally {
			userManager.deleteUser(newUser.getId());
		}
	}

	@Test
	public void auth() {
		User failedAuthUser = userManager.authorize(existingUser.getUsername(), "fail");
		assertNull(failedAuthUser);

		User goodAuthUser = userManager.authorize(existingUser.getUsername(), "password");
		assertNotNull(goodAuthUser);
	}

	@Test
	public void changePassword() {
		userManager.changePassword(existingUser.getId(), "newPassword");

		try {
			User failedAuthUser = userManager.authorize(existingUser.getUsername(), "password");
			assertNull(failedAuthUser);

			User changedAuthUser = userManager.authorize(existingUser.getUsername(), "newPassword");
			assertNotNull(changedAuthUser);
		} finally {
			userManager.changePassword(existingUser.getId(), "password");
		}
	}

	@Test
	public void dupUser() {
		try {
			User newUser = new User(existingUser.getUsername(), "test@example.com", "12345678900");
			userManager.createUser(newUser, "password");
			fail("Expected username taken exception");
		} catch (UsernameTakenException ex) {
			assertEquals(existingUser.getUsername(), ex.getUsername());
		}
	}

}
