package com.hazardousholdings.unkj.integration;

import com.hazardousholdings.unkj.Infrastructure;
import com.hazardousholdings.unkj.TestInfrastructureFactory;
import com.hazardousholdings.unkj.config.Configuration;
import com.hazardousholdings.unkj.config.UnkJConfigKeys;
import com.hazardousholdings.unkj.db.JDBCConnectInfo;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InfrastructureFactoryTest {

	@Test
	public void testFactoryInit() throws SQLException {
		Infrastructure infrastructure = TestInfrastructureFactory.get();
		Configuration config = infrastructure.getConfiguration();

		JDBCConnectInfo defaultConnectInfo = config.getDBConnectInfo(UnkJConfigKeys.DEFAULT_DB);

		try (Connection connection = defaultConnectInfo.getConnection();
			 PreparedStatement statement = connection.prepareStatement("select version();")) {
			statement.executeQuery();
		}
	}

}
