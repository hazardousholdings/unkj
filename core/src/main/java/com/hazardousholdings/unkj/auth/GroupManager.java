package com.hazardousholdings.unkj.auth;

import javax.sql.DataSource;

public class GroupManager {

	private GroupStore groupStore;

	public GroupManager(DataSource dataSource) {
		this(new DBGroupStore(dataSource));
	}

	protected GroupManager(GroupStore groupStore) {
		this.groupStore = groupStore;
	}

	public void addGroup(Group group) {
		this.groupStore.addGroup(group);
	}

}
