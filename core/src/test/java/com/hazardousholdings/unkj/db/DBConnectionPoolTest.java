package com.hazardousholdings.unkj.db;

import org.junit.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConnectionPoolTest {

	@Test
	public void local() throws SQLException {
		JDBCConnectInfo localInfo = new JDBCConnectInfo("org.postgresql.Driver", "jdbc:postgresql://localhost:5432/unkj", "unkj", "unkj");
		DBConnectionPool pool = new DBConnectionPool(localInfo);
		DataSource dataSource = pool.getDataSource();

		Connection connection = dataSource.getConnection();
		PreparedStatement statement = connection.prepareStatement("select version();");
		ResultSet rs = statement.executeQuery();

		while(rs.next()) {
			System.out.println(rs.getString(1));
		}

		rs.close();
		statement.close();
		connection.close();
	}

}
