package com.hazardousholdings.unkj.util;

import org.junit.Test;
import org.junit.Assert;

public class QueryStringBuilderTest {

	@Test
	public void paramsSeperatedByAmpersand() {
		QueryStringBuilder builder = new QueryStringBuilder();
		builder.addParam("a", "a");
		builder.addParam("b", "b");
		Assert.assertEquals("a=a&b=b", builder.toString());
	}
	
	@Test
	public void paramValuesEscaped() {
		QueryStringBuilder builder = new QueryStringBuilder();
		builder.addParam("a", "a b+c");
		Assert.assertEquals("a=a+b%2Bc", builder.toString());
	}
	
	@Test
	public void withStart() {
		QueryStringBuilder builder = new QueryStringBuilder("a=a");
		builder.addParam("b", "b");
		Assert.assertEquals("a=a&b=b", builder.toString());
	}
	
	@Test
	public void withBlankStart() {
		QueryStringBuilder builder = new QueryStringBuilder("");
		builder.addParam("b", "b");
		Assert.assertEquals("b=b", builder.toString());
	}
}
