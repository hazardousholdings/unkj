package com.hazardousholdings.unkj.integration;

import com.hazardousholdings.unkj.Infrastructure;
import com.hazardousholdings.unkj.TestInfrastructureFactory;
import com.hazardousholdings.unkj.config.Configuration;
import com.hazardousholdings.unkj.config.UnkJConfigKeys;
import com.hazardousholdings.unkj.db.JDBCConnectInfo;
import org.junit.Test;

import java.util.logging.Level;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class InfrastructureFactoryTest {

	@Test
	public void testFactoryInit() {
		Infrastructure infrastructure = TestInfrastructureFactory.get();
		Configuration config = infrastructure.getConfiguration();

		String logLevel = config.get(UnkJConfigKeys.LOG_LEVEL);
		assertEquals(Level.WARNING.toString(), logLevel);

		JDBCConnectInfo connectInfo = config.getDBConnectInfo("main-db");
		assertNotNull(connectInfo);
		assertEquals("unkjtest", connectInfo.getUser());
	}

}