package com.hazardousholdings.unkj;

import com.hazardousholdings.unkj.config.Configuration;
import com.hazardousholdings.unkj.config.UnkJConfigKeys;
import com.hazardousholdings.unkj.db.JDBCConnectInfo;
import org.junit.Test;

import java.util.logging.Level;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class IntegrationTest {

	@Test
	public void testFactoryInit() {
		InfrastructureFactory factory = new InfrastructureFactory(new TestBootstrapConfiguration());
		Infrastructure infrastructure = factory.bootstrap();

		factory.initDefaults(infrastructure);

		Configuration config = infrastructure.getConfiguration();
		String logLevel = config.get(UnkJConfigKeys.LOG_LEVEL);

		assertEquals(Level.WARNING.toString(), logLevel);

		// Defaults read, overwrite with app config

		factory.initFromConfig(infrastructure);

		logLevel = config.get(UnkJConfigKeys.LOG_LEVEL);
		assertEquals(Level.SEVERE.toString(), logLevel);

		JDBCConnectInfo connectInfo = config.getDBConnectInfo("main-db");
		assertNotNull(connectInfo);
		assertEquals("unkjtest", connectInfo.getUser());
	}

}
