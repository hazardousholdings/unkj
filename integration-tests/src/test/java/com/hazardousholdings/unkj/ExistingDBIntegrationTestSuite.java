package com.hazardousholdings.unkj;

import com.hazardousholdings.unkj.config.Configuration;
import com.hazardousholdings.unkj.config.UnkJConfigKeys;
import com.hazardousholdings.unkj.database.DatabaseCreator;
import com.hazardousholdings.unkj.integration.InfrastructureFactoryTest;
import com.hazardousholdings.unkj.integration.UserManagerTest;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import java.io.IOException;

@RunWith(Suite.class)
@Suite.SuiteClasses({ InfrastructureFactoryTest.class, UserManagerTest.class })
public class ExistingDBIntegrationTestSuite {

	@BeforeClass
	public static void initInfrastructure() throws IOException {
		TestConfigurationFactory.setConfiguration(false);

		DatabaseCreator creator = new DatabaseCreator(new Configuration().getDBConnectInfo(UnkJConfigKeys.DB));
		creator.create();

		TestInfrastructureFactory.set(new Infrastructure());
	}

	@AfterClass
	public static void destroyInfrastructure() {
		TestInfrastructureFactory.reset();
	}

}
