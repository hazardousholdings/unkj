package com.hazardousholdings.unkj.db;

import java.io.Serializable;

public abstract class Key<T extends Comparable<T>> implements Serializable, Comparable<Key<T>> {

	private T id;

	public Key(T id) {
		this.id = id;
	}

	public T getId() {
		return this.id;
	}

	public boolean equals(Object otherObject) {
		if(otherObject != null && otherObject instanceof Key) {
			return this.id.equals(((Key) otherObject).id);
		}
		return super.equals(otherObject);
	}

	public int hashCode() {
		return this.id.hashCode();
	}

	public String toString() {
		return "Key: " + this.id;
	}

	@Override
	public int compareTo(Key<T> otherKey) {
		return this.id.compareTo(otherKey.id);
	}
}
