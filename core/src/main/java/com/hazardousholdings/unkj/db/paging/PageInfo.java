package com.hazardousholdings.unkj.db.paging;

public class PageInfo {

	private int pageIndex;
	private int size;

	public PageInfo(int size) {
		this.pageIndex = 0;
		this.size = size;
	}

	public PageInfo(int pageIndex, int size) {
		this.pageIndex = pageIndex;
		this.size = size;
	}

	public int getPageIndex() {
		return this.pageIndex;
	}

	public int getSize() {
		return this.size;
	}

	public int getPageStartingIndex() {
		return this.pageIndex * this.size;
	}

}
