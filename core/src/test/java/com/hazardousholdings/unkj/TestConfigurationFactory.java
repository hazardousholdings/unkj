package com.hazardousholdings.unkj;

import com.hazardousholdings.unkj.install.ApplicationConfiguration;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class TestConfigurationFactory {

	public static BootstrapConfiguration getBootstrapConfiguration(final boolean fresh) throws IOException {
		String filename;
		if(fresh) {
			filename = "/test-fresh-unkj-config.xml";
		} else {
			filename = "/test-unkj-config.xml";
		}
		try (Reader unkjConfigReader = new InputStreamReader(ExistingDBIntegrationTestSuite.class.getResourceAsStream(filename))) {
			return new BootstrapConfiguration(unkjConfigReader);
		}
	}

	public static ApplicationConfiguration getApplicationConfiguration() throws IOException {
		try (Reader appConfigReader = new InputStreamReader(TestConfigurationFactory.class.getResourceAsStream("/test-app-config.xml"))) {
			return new ApplicationConfiguration(appConfigReader);
		}
	}
}
