package com.hazardousholdings.unkj.db;

import java.io.Serializable;import java.lang.Long;import java.lang.Object;import java.lang.String;

public abstract class SurrogateKey extends Key implements Serializable {

	private long id;

	public SurrogateKey(long id) {
		this.id = id;
	}

	public long getId() {
		return this.id;
	}

	@Override
	protected String getSortOrder() {
		return Long.toString(this.id);
	}

	public boolean equals(Object otherObject) {
		if(otherObject instanceof SurrogateKey) {
			return this.getId() == ((SurrogateKey) otherObject).getId();
		}

		return super.equals(otherObject);
	}

	public int hashCode() {
		return Long.valueOf(this.id).hashCode();
	}

	public String toString() {
		return "Key: " + this.id;
	}

}
