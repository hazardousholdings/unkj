package com.hazardousholdings.unkj;

import com.hazardousholdings.unkj.integration.InfrastructureFactoryTest;
import com.hazardousholdings.unkj.integration.SomeOtherTest;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ InfrastructureFactoryTest.class, SomeOtherTest.class })
public class ExistingDBIntegrationTestSuite {

	@BeforeClass
	public static void initInfrastructure() {
		TestInfrastructureFactory.set(InfrastructureFactory.init(new TestBootstrapConfiguration(false)));
	}

	@AfterClass
	public static void destroyInfrastructure() {
		TestInfrastructureFactory.reset();
	}

}
