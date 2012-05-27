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



}
