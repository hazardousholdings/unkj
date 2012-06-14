package com.hazardousholdings.unkj.auth;

public class UserId {

	private long id;

	public UserId(long id) {
		this.id = id;
	}

	public long getLong() {
		return this.id;
	}

	public String toString() {
		return "User ID: " + Long.toString(this.getLong());
	}

	public boolean equals(Object otherObject) {
		return otherObject != null && otherObject instanceof UserId && ((UserId) otherObject).getLong() == this.id;
	}

}
