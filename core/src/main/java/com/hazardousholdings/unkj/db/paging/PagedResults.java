package com.hazardousholdings.unkj.db.paging;

import com.hazardousholdings.unkj.db.Key;
import com.hazardousholdings.unkj.db.ModelEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class PagedResults<K extends Key, T extends ModelEntity<K>> {

	private List<T> results;
	private PageInfo<K> currentPageInfo;
	private PageInfo<K> nextPageInfo;

	private PagedResults(PageInfo<K> currentPageInfo) {
		this.currentPageInfo = currentPageInfo;
		this.results = new ArrayList<>(currentPageInfo.getSize());
	}

	public PagedResults(Iterable<T> items, PageInfo<K> currentPageInfo) {
		this(currentPageInfo);
		Iterator<T> iterator = items.iterator();

		boolean useResults = currentPageInfo.getStartingKey() == null;
		while(iterator.hasNext() && this.results.size() < currentPageInfo.getSize()) {
			T item = iterator.next();
			if(item.getKey().equals(currentPageInfo.getStartingKey())) {
				useResults = true;
			}
			if(useResults) {
				this.results.add(item);
			}
		}

		if(iterator.hasNext()) {
			this.nextPageInfo = new PageInfo<>(currentPageInfo.getIndex() + 1, currentPageInfo.getSize(), iterator.next().getKey());
		}
	}

	public List<T> getResults() {
		return Collections.unmodifiableList(this.results);
	}

	public PageInfo<K> getPageInfo() {
		return this.currentPageInfo;
	}

	public PageInfo<K> getNextPageInfo() {
		return this.nextPageInfo;
	}

	public boolean hasMorePages() {
		return this.nextPageInfo != null;
	}

}
