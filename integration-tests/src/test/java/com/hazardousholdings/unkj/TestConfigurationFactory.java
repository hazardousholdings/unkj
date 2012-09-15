package com.hazardousholdings.unkj;

import com.hazardousholdings.unkj.config.UnkJConfigKeys;

import java.io.IOException;

public class TestConfigurationFactory {

	public static void setConfiguration(final boolean fresh) throws IOException {
		if(fresh) {
			System.setProperty(UnkJConfigKeys.DEFAULT_DB, "postgres://unkjtest:unkjtest@localhost:5432/unkjtesttemp");
		} else {
			System.setProperty(UnkJConfigKeys.DEFAULT_DB, "postgres://unkjtest:unkjtest@localhost:5432/unkjtest");
		}
	}
}
