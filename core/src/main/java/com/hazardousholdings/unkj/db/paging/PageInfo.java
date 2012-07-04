package com.hazardousholdings.unkj.db.paging;

import com.hazardousholdings.unkj.db.Key;

public class PageInfo<K extends Key> {

	private int index;
	private int size;
	private K startingKey;

	public PageInfo(int size) {
		this.index = 0;
		this.size = size;
		this.startingKey = null;
	}

	public PageInfo(int index, int size, K startingKey) {
		this.index = index;
		this.size = size;
		this.startingKey = startingKey;
	}

	public int getIndex() {
		return this.index;
	}

	public int getSize() {
		return this.size;
	}

	public K getStartingKey() {
		return this.startingKey;
	}

}
