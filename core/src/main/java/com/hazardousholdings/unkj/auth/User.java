package com.hazardousholdings.unkj.auth;

public class User {

	private UserId id;
	private String username;
	private String email;
	private String phone;

	public User(UserId id, String username, String email, String phone) {
		this(username, email, phone);
		this.id = id;
	}

	public User(String username, String email, String phone) {
		this.username = username;
		this.email = email;
		this.phone = phone;
	}

	public UserId getId() {
		return this.id;
	}

	protected void setId(UserId id) {
		this.id = id;
	}

	public String getUsername() {
		return this.username;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
