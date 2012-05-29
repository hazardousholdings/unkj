package com.hazardousholdings.unkj;

import com.hazardousholdings.unkj.integration.InfrastructureFactoryTest;
import com.hazardousholdings.unkj.integration.SomeOtherTest;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ InfrastructureFactoryTest.class, SomeOtherTest.class })
public class FreshDBIntegrationTestSuite {

	private static BootstrapConfiguration bootstrapConfig;

	@BeforeClass
	public static void initInfrastructure() {
		bootstrapConfig = new TestBootstrapConfiguration(true);
		// Create temp DB

		TestInfrastructureFactory.set(InfrastructureFactory.init(bootstrapConfig));
	}

	@AfterClass
	public static void destroyInfrastructure() {
		// Delete temp DB

		TestInfrastructureFactory.reset();
		bootstrapConfig = null;
	}

}
