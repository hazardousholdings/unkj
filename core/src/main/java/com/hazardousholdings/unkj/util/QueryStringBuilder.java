package com.hazardousholdings.unkj.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class QueryStringBuilder {

	private static final String UTF8_CHARSET = "UTF-8";
	
	private StringBuilder builder;
	private boolean first;
	
	public QueryStringBuilder() {
		this.builder = new StringBuilder();
		this.first = true;
	}
	
	public QueryStringBuilder(String start) {
		this.builder = new StringBuilder(start);
		
		if(start.length() > 0) {
			this.first = false;
		} else {
			this.first = true;
		}
	}
	
	public void addParam(String key, String value) {
		if(!this.first) {
			this.builder.append("&");
		} else {
			this.first = false;
		}
		
		this.builder.append(key).append("=").append(encode(value));
	}
	
	protected String encode(String value) {
		try {
			return URLEncoder.encode(value, UTF8_CHARSET);
		} catch (UnsupportedEncodingException ex) {
			throw new RuntimeException("Failed to encode value", ex);
		}
	}
	
	@Override
	public String toString() {
		return this.builder.toString();
	}
	
}
