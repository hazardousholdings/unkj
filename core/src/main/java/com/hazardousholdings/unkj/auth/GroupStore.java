package com.hazardousholdings.unkj.auth;

interface GroupStore {

	public void addGroup(Group group);
	public Group getGroup(long id);
	public Group getGroup(String name);

}
