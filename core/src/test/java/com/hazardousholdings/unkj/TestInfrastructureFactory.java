package com.hazardousholdings.unkj;

public class TestInfrastructureFactory {

	private static Infrastructure INFRASTRUCTURE;

	public static synchronized Infrastructure get() {
		return INFRASTRUCTURE;
	}

	public static synchronized void set(Infrastructure infrastructure) {
		INFRASTRUCTURE = infrastructure;
	}

	public static synchronized void reset() {
		set(null);
	}

}
