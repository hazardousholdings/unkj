package com.hazardousholdings.unkj;

import com.hazardousholdings.unkj.db.JDBCConnectInfo;
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

	private boolean fresh;

	public TestBootstrapConfiguration() {
		this(false);
	}

	public TestBootstrapConfiguration(boolean fresh) {
		super(TEST_CONFIG, new Version(1, 1, 0, "test"));
		this.fresh = fresh;
	}

	@Override
	public JDBCConnectInfo getDBInfo() {
		if(!this.fresh) {
			return super.getDBInfo();
		} else {
			JDBCConnectInfo old = super.getDBInfo();
			return new JDBCConnectInfo(old.getDriverClassName(), old.getConnectionString() + "temp", old.getUser(), old.getPass());
		}
	}
}
