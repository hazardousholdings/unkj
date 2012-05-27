package com.hazardousholdings.unkj.auth;

import com.hazardousholdings.unkj.db.DBStore;

import javax.sql.DataSource;

class DBGroupStore extends DBStore implements GroupStore {

	public DBGroupStore(DataSource dataSource) {
		super(dataSource);
	}

	public void addGroup(Group group) {

	}

	public Group getGroup(long id) {
		return null;
	}

	public Group getGroup(String name) {
		return null;
	}

}
