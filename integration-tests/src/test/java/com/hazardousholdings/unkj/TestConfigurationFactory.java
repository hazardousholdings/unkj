package com.hazardousholdings.unkj;

import java.io.IOException;

public class TestConfigurationFactory {

	public static void setConfiguration(final boolean fresh) throws IOException {
		if(fresh) {
			System.setProperty("DB_DRIVER_CLASS", "org.postgresql.Driver");
			System.setProperty("DB_CONNECT_STRING", "jdbc:postgresql://localhost:5432/unkjtesttemp");
			System.setProperty("DB_USER", "unkjtest");
			System.setProperty("DB_PASSWORD", "unkjtest");
		} else {
			System.setProperty("DB_DRIVER_CLASS", "org.postgresql.Driver");
			System.setProperty("DB_CONNECT_STRING", "jdbc:postgresql://localhost:5432/unkjtest");
			System.setProperty("DB_USER", "unkjtest");
			System.setProperty("DB_PASSWORD", "unkjtest");
		}
	}
}
