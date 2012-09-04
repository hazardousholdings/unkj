package com.hazardousholdings.unkj;

import com.hazardousholdings.unkj.config.Configuration;
import com.hazardousholdings.unkj.config.UnkJConfigKeys;
import com.hazardousholdings.unkj.database.UnkJDatabaseCreator;
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
		Configuration configuration = new Configuration();

		TestConfigurationFactory.setConfiguration(false);
		try(Connection connection = configuration.getDBConnectInfo(UnkJConfigKeys.DB).getConnection();
			Statement statement = connection.createStatement()) {

			statement.execute("drop database if exists unkjtesttemp");
			statement.execute("create database unkjtesttemp");
		}

		TestConfigurationFactory.setConfiguration(true);

		UnkJDatabaseCreator creator = new UnkJDatabaseCreator(configuration);
		creator.create();

		TestInfrastructureFactory.set(new Infrastructure());
	}

	@AfterClass
	public static void destroyInfrastructure() throws SQLException {
		TestInfrastructureFactory.reset();
	}

}
