package com.hazardousholdings.unkj;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

public class TestBootstrapConfiguration extends BootstrapConfiguration {

	private static Document TEST_CONFIG;

	static {
		try {
			SAXReader reader = new SAXReader();
			TEST_CONFIG = reader.read(TestBootstrapConfiguration.class.getResource("/test-config.xml"));
		} catch (DocumentException ex) {
			throw new RuntimeException("Failed to parse test config", ex);
		}
	}

	public TestBootstrapConfiguration() {
		super(TEST_CONFIG, new Version(1, 1, 0, "test"));
	}
}
