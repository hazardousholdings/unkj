package com.hazardousholdings.unkj;

import com.hazardousholdings.unkj.integration.InfrastructureFactoryTest;
import com.hazardousholdings.unkj.integration.UserManagerTest;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@RunWith(Suite.class)
@Suite.SuiteClasses({ InfrastructureFactoryTest.class, UserManagerTest.class })
public class FreshDBIntegrationTestSuite {

	private static BootstrapConfiguration freshBootstrapConfig;

	@BeforeClass
	public static void initInfrastructure() throws SQLException {
		try(Connection connection = new TestBootstrapConfiguration(false).getDBInfo().getConnection();
			Statement statement = connection.createStatement()) {

			statement.execute("drop database if exists unkjtesttemp");
			statement.execute("create database unkjtesttemp");
		}

		freshBootstrapConfig = new TestBootstrapConfiguration(true);
		TestInfrastructureFactory.set(InfrastructureFactory.init(freshBootstrapConfig));
	}

	@AfterClass
	public static void destroyInfrastructure() throws SQLException {
		TestInfrastructureFactory.reset();
		freshBootstrapConfig = null;
	}

}
