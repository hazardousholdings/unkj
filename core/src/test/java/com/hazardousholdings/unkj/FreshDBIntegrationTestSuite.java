package com.hazardousholdings.unkj;

import com.hazardousholdings.unkj.install.ApplicationConfiguration;
import com.hazardousholdings.unkj.install.Installer;
import com.hazardousholdings.unkj.integration.InfrastructureFactoryTest;
import com.hazardousholdings.unkj.integration.UserManagerTest;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@RunWith(Suite.class)
@Suite.SuiteClasses({ InfrastructureFactoryTest.class, UserManagerTest.class })
public class FreshDBIntegrationTestSuite {

	@BeforeClass
	public static void initInfrastructure() throws SQLException, IOException {
		BootstrapConfiguration bootstrapConfig = TestConfigurationFactory.getBootstrapConfiguration(false);
		try(Connection connection = bootstrapConfig.getDBInfo().getConnection();
			Statement statement = connection.createStatement()) {

			statement.execute("drop database if exists unkjtesttemp");
			statement.execute("create database unkjtesttemp");
		}

		BootstrapConfiguration freshBootstrapConfig = TestConfigurationFactory.getBootstrapConfiguration(false);
		ApplicationConfiguration appConfig = TestConfigurationFactory.getApplicationConfiguration();

		Installer installer = new Installer(freshBootstrapConfig, appConfig);
		installer.init();

		TestInfrastructureFactory.set(new Infrastructure(freshBootstrapConfig));
	}

	@AfterClass
	public static void destroyInfrastructure() throws SQLException {
		TestInfrastructureFactory.reset();
	}

}
