package com.hazardousholdings.unkj.db;

import com.hazardousholdings.unkj.TestConfigurationFactory;
import com.hazardousholdings.unkj.config.Configuration;
import com.hazardousholdings.unkj.config.UnkJConfigKeys;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.junit.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class DBConnectionPoolTest {

	@Test
	public void local() throws SQLException, IOException {
		TestConfigurationFactory.setConfiguration(false);
		JDBCConnectInfo localInfo = new Configuration().getDBConnectInfo(UnkJConfigKeys.DEFAULT_DB);
		DBConnectionPool pool = new DBConnectionPool(localInfo);
		ComboPooledDataSource dataSource = (ComboPooledDataSource) pool.getDataSource();

		assertTrue(dataSource.isTestConnectionOnCheckout());
		assertEquals(3, dataSource.getAcquireIncrement());
		assertEquals(5, dataSource.getAcquireRetryAttempts());

		Connection connection = dataSource.getConnection();
		PreparedStatement statement = connection.prepareStatement("select version();");
		ResultSet rs = statement.executeQuery();

		while(rs.next()) {
			assertNotNull(rs.getString(1));
		}

		rs.close();
		statement.close();
		connection.close();
	}

}
