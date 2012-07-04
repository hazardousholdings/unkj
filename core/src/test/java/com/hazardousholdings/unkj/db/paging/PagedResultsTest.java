package com.hazardousholdings.unkj.db.paging;

import com.hazardousholdings.unkj.db.ModelEntity;
import com.hazardousholdings.unkj.db.SurrogateKey;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class PagedResultsTest {

	@Test
	public void requiredOnlyPage() {
		List<TestEntity> stuff = makeStuff(5);

		PageInfo<TestEntity.TestEntityKey> firstPageInfo = new PageInfo<>(5);
		PagedResults<TestEntity.TestEntityKey, TestEntity> firstPage = new PagedResults<>(stuff, firstPageInfo);

		assertEquals(5, firstPage.getPageInfo().getSize());
		assertEquals(5, firstPage.getResults().size());
		assertEquals(0, firstPage.getResults().get(0).getKey().getId());
		assertEquals(4, firstPage.getResults().get(4).getKey().getId());
		assertFalse(firstPage.hasMorePages());
	}

	@Test
	public void requiredFirstPage() {
		List<TestEntity> stuff = makeStuff(6);

		PageInfo<TestEntity.TestEntityKey> firstPageInfo = new PageInfo<>(5);
		PagedResults<TestEntity.TestEntityKey, TestEntity> firstPage = new PagedResults<>(stuff, firstPageInfo);

		assertEquals(5, firstPage.getPageInfo().getSize());
		assertEquals(5, firstPage.getResults().size());
		assertEquals(0, firstPage.getResults().get(0).getKey().getId());
		assertEquals(4, firstPage.getResults().get(4).getKey().getId());
		assertTrue(firstPage.hasMorePages());
	}

	@Test
	public void requiredSubsequentPage() {
		List<TestEntity> stuff = makeStuff(3);

		PageInfo<TestEntity.TestEntityKey> firstPageInfo = new PageInfo<>(4, 5, new TestEntity.TestEntityKey(0));
		PagedResults<TestEntity.TestEntityKey, TestEntity> fourthPage = new PagedResults<>(stuff, firstPageInfo);

		assertEquals(5, fourthPage.getPageInfo().getSize());
		assertEquals(3, fourthPage.getResults().size());
		assertEquals(0, fourthPage.getResults().get(0).getKey().getId());
		assertEquals(2, fourthPage.getResults().get(2).getKey().getId());
		assertFalse(fourthPage.hasMorePages());
	}

	@Test
	public void extraPagedEven() {
		List<TestEntity> stuff = makeStuff(10);

		PageInfo<TestEntity.TestEntityKey> firstPageInfo = new PageInfo<>(5);
		PagedResults<TestEntity.TestEntityKey, TestEntity> firstPage = new PagedResults<>(stuff, firstPageInfo);

		assertEquals(0, firstPage.getPageInfo().getIndex());
		assertEquals(0, firstPage.getPageInfo().getIndex());
		assertEquals(5, firstPage.getResults().size());
		assertEquals(0, firstPage.getResults().get(0).getKey().getId());
		assertEquals(4, firstPage.getResults().get(4).getKey().getId());
		assertTrue(firstPage.hasMorePages());
		assertEquals(5, firstPage.getNextPageInfo().getStartingKey().getId());

		PagedResults<TestEntity.TestEntityKey, TestEntity> secondPage = new PagedResults<>(stuff, firstPage.getNextPageInfo());

		assertEquals(1, secondPage.getPageInfo().getIndex());
		assertEquals(5, secondPage.getResults().size());
		assertEquals(5, secondPage.getResults().get(0).getKey().getId());
		assertEquals(9, secondPage.getResults().get(4).getKey().getId());
		assertFalse(secondPage.hasMorePages());
	}

	@Test
	public void extraPagedRagged() {
		List<TestEntity> stuff = makeStuff(8);

		PageInfo<TestEntity.TestEntityKey> firstPageInfo = new PageInfo<>(5);
		PagedResults<TestEntity.TestEntityKey, TestEntity> firstPage = new PagedResults<>(stuff, firstPageInfo);

		assertEquals(0, firstPage.getPageInfo().getIndex());
		assertEquals(0, firstPage.getPageInfo().getIndex());
		assertEquals(5, firstPage.getResults().size());
		assertEquals(0, firstPage.getResults().get(0).getKey().getId());
		assertEquals(4, firstPage.getResults().get(4).getKey().getId());
		assertTrue(firstPage.hasMorePages());
		assertEquals(5, firstPage.getNextPageInfo().getStartingKey().getId());

		PagedResults<TestEntity.TestEntityKey, TestEntity> secondPage = new PagedResults<>(stuff, firstPage.getNextPageInfo());

		assertEquals(1, secondPage.getPageInfo().getIndex());
		assertEquals(3, secondPage.getResults().size());
		assertEquals(5, secondPage.getResults().get(0).getKey().getId());
		assertEquals(7, secondPage.getResults().get(2).getKey().getId());
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
		public static class TestEntityKey extends SurrogateKey {
			private TestEntityKey(long id) {
				super(id);
			}
		}
	}

}
