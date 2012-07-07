package com.hazardousholdings.unkj.db.paging;

import com.hazardousholdings.unkj.db.Key;
import com.hazardousholdings.unkj.db.ModelEntity;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.*;

public class PagedResultsTest {

	@Test
	public void requiredOnlyPage() {
		PageInfo firstPageInfo = new PageInfo(5);
		PagedResults<TestEntity> firstPage = new PagedResults<>(firstPageInfo);
		firstPage.setResults(makeStuff(5));

		assertEquals(5, firstPage.getPageInfo().getSize());
		assertEquals(5, firstPage.getResults().size());
		assertEquals(Long.valueOf(0), firstPage.getResults().get(0).getKey().getId());
		assertEquals(Long.valueOf(4), firstPage.getResults().get(4).getKey().getId());
		assertFalse(firstPage.hasMorePages());
	}

	@Test
	public void requiredFirstPage() {
		PageInfo firstPageInfo = new PageInfo(5);
		PagedResults<TestEntity> firstPage = new PagedResults<>(firstPageInfo);
		firstPage.setResults(makeStuff(6));

		assertEquals(5, firstPage.getPageInfo().getSize());
		assertEquals(5, firstPage.getResults().size());
		assertEquals(Long.valueOf(0), firstPage.getResults().get(0).getKey().getId());
		assertEquals(Long.valueOf(4), firstPage.getResults().get(4).getKey().getId());
		assertTrue(firstPage.hasMorePages());
	}

	@Test
	public void requiredSubsequentPage() {
		PageInfo firstPageInfo = new PageInfo(4, 5);
		PagedResults<TestEntity> fourthPage = new PagedResults<>(firstPageInfo);
		fourthPage.setResults(makeStuff(3));

		assertEquals(5, fourthPage.getPageInfo().getSize());
		assertEquals(3, fourthPage.getResults().size());
		assertEquals(Long.valueOf(0), fourthPage.getResults().get(0).getKey().getId());
		assertEquals(Long.valueOf(2), fourthPage.getResults().get(2).getKey().getId());
		assertFalse(fourthPage.hasMorePages());
	}

	@Test
	public void extraPagedEven() {
		PageInfo firstPageInfo = new PageInfo(5);
		PagedResults<TestEntity> firstPage = new PagedResults<>(firstPageInfo);
		firstPage.setResultsFromAll(makeStuff(10));

		assertEquals(0, firstPage.getPageInfo().getPageIndex());
		assertEquals(0, firstPage.getPageInfo().getPageIndex());
		assertEquals(5, firstPage.getResults().size());
		assertEquals(Long.valueOf(0), firstPage.getResults().get(0).getKey().getId());
		assertEquals(Long.valueOf(4), firstPage.getResults().get(4).getKey().getId());
		assertTrue(firstPage.hasMorePages());
		assertEquals(5, firstPage.getNextPageInfo().getPageStartingIndex());

		PagedResults<TestEntity> secondPage = new PagedResults<>(firstPage.getNextPageInfo());
		secondPage.setResultsFromAll(makeStuff(10));

		assertEquals(1, secondPage.getPageInfo().getPageIndex());
		assertEquals(5, secondPage.getResults().size());
		assertEquals(Long.valueOf(5), secondPage.getResults().get(0).getKey().getId());
		assertEquals(Long.valueOf(9), secondPage.getResults().get(4).getKey().getId());
		assertFalse(secondPage.hasMorePages());
	}

	@Test
	public void extraPagedRagged() {
		PageInfo firstPageInfo = new PageInfo(5);
		PagedResults<TestEntity> firstPage = new PagedResults<>(firstPageInfo);
		firstPage.setResultsFromAll(makeStuff(8));

		assertEquals(0, firstPage.getPageInfo().getPageIndex());
		assertEquals(0, firstPage.getPageInfo().getPageIndex());
		assertEquals(5, firstPage.getResults().size());
		assertEquals(Long.valueOf(0), firstPage.getResults().get(0).getKey().getId());
		assertEquals(Long.valueOf(4), firstPage.getResults().get(4).getKey().getId());
		assertTrue(firstPage.hasMorePages());
		assertEquals(5, firstPage.getNextPageInfo().getPageStartingIndex());

		PagedResults<TestEntity> secondPage = new PagedResults<>(firstPage.getNextPageInfo());
		secondPage.setResultsFromAll(makeStuff(8));

		assertEquals(1, secondPage.getPageInfo().getPageIndex());
		assertEquals(3, secondPage.getResults().size());
		assertEquals(Long.valueOf(5), secondPage.getResults().get(0).getKey().getId());
		assertEquals(Long.valueOf(7), secondPage.getResults().get(2).getKey().getId());
		assertFalse(secondPage.hasMorePages());
	}

	private List<TestEntity> makeStuff(int size) {
		List<TestEntity> stuff = new ArrayList<>(size);
		for(long i = 0; i < size; i++) {
			TestEntity t = new TestEntity();
			t.setKey(new TestEntity.TestEntityKey(i));
			stuff.add(t);
		}
		return stuff;
	}

	private static class TestEntity extends ModelEntity<TestEntity.TestEntityKey> {
		public static class TestEntityKey extends Key<Long> {
			private TestEntityKey(long id) {
				super(id);
			}
		}
	}

}
