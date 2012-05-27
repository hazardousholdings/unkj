package com.hazardousholdings.unkj.auth;

public class Group {

	private long id;
	private String name;

	public Group(long id) {

	}

	public Group(String name) {

	}

	public long getId() {
		return this.id;
	}

	void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
