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
		for(int i = 0; i < startIndex; i++) {
			iterator.next();
		}

		while(iterator.hasNext()) {
			addResult(iterator.next());
		}
	}

	public void addResult(T item) {
		if(this.results.size() < this.currentPageInfo.getSize()) {
			this.results.add(item);
		} else {
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
