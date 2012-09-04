package com.hazardousholdings.unkj;

import com.hazardousholdings.unkj.install.DatabaseCreator;
import com.hazardousholdings.unkj.integration.InfrastructureFactoryTest;
import com.hazardousholdings.unkj.integration.UserManagerTest;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import java.io.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({ InfrastructureFactoryTest.class, UserManagerTest.class })
public class ExistingDBIntegrationTestSuite {

	@BeforeClass
	public static void initInfrastructure() throws IOException {
		DatabaseCreator creator = new DatabaseCreator(TestConfigurationFactory.getBootstrapConfiguration(false));
		creator.create();

		TestInfrastructureFactory.set(new Infrastructure(TestConfigurationFactory.getBootstrapConfiguration(false)));
	}

	@AfterClass
	public static void destroyInfrastructure() {
		TestInfrastructureFactory.reset();
	}

}
