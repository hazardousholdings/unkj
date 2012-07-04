package com.hazardousholdings.unkj.db;

import java.io.Serializable;

public abstract class Key implements Serializable, Comparable<Key> {

	protected abstract String getSortOrder();

	@Override
	public int compareTo(Key key) {
		return this.getSortOrder().compareTo(key.getSortOrder());
	}
}
