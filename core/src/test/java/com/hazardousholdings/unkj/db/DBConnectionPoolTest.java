package com.hazardousholdings.unkj.db;

import com.hazardousholdings.unkj.TestConfigurationFactory;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.junit.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class DBConnectionPoolTest {

	@Test
	public void local() throws SQLException, IOException {
		JDBCConnectInfo localInfo = TestConfigurationFactory.getBootstrapConfiguration(false).getDBInfo();
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
