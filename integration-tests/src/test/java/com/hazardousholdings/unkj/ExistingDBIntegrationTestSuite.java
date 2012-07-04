package com.hazardousholdings.unkj;

import com.hazardousholdings.unkj.install.ApplicationConfiguration;
import com.hazardousholdings.unkj.install.Installer;
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
		BootstrapConfiguration bootstrapConfig = TestConfigurationFactory.getBootstrapConfiguration(false);
		ApplicationConfiguration appConfig = TestConfigurationFactory.getApplicationConfiguration();

		Installer installer = new Installer(bootstrapConfig, appConfig);
		installer.init();

		TestInfrastructureFactory.set(new Infrastructure(bootstrapConfig));
	}

	@AfterClass
	public static void destroyInfrastructure() {
		TestInfrastructureFactory.reset();
	}

}
