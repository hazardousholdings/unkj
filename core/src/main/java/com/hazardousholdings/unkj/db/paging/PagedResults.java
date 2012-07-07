package com.hazardousholdings.unkj.db.paging;

import com.hazardousholdings.unkj.db.ModelEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class PagedResults<T extends ModelEntity> {

	private List<T> results;
	private PageInfo currentPageInfo;
	private PageInfo nextPageInfo;

	public PagedResults(PageInfo currentPageInfo) {
		this.currentPageInfo = currentPageInfo;
		this.results = new ArrayList<>(currentPageInfo.getSize());
	}

	public void setResults(Iterable<T> items) {
		setResults(items, 0);
	}

	public void setResultsFromAll(Iterable<T> items) {
		setResults(items, this.currentPageInfo.getPageStartingIndex());
	}

	private void setResults(Iterable<T> items, int startIndex) {
		Iterator<T> iterator = items.iterator();
		int index = 0;
		while(iterator.hasNext() && this.results.size() < this.currentPageInfo.getSize()) {
			T item = iterator.next();
			if(index >= startIndex) {
				this.results.add(item);
			}
			index++;
		}

		if(iterator.hasNext()) {
			this.nextPageInfo = new PageInfo(this.currentPageInfo.getPageIndex() + 1, this.currentPageInfo.getSize());
		}
	}

	public List<T> getResults() {
		return Collections.unmodifiableList(this.results);
	}

	public PageInfo getPageInfo() {
		return this.currentPageInfo;
	}

	public PageInfo getNextPageInfo() {
		return this.nextPageInfo;
	}

	public boolean hasMorePages() {
		return this.nextPageInfo != null;
	}

}
