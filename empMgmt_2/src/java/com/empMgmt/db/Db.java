package com.empMgmt.db;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp.BasicDataSource;

public enum Db {

	CONN;

	private BasicDataSource bds;

	private Db() {
		bds = new BasicDataSource();
		bds.setUrl(DbProps.H2.url);
		bds.setDriverClassName(DbProps.H2.driverClass);
		bds.setUsername(DbProps.H2.username);
		bds.setPassword(DbProps.H2.password);
		bds.setInitialSize(5);
	}

	private Connection getConnection() {
		try {
			return bds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Connection open() {
		Connection conn = getConnection();
		if(conn == null)
			throw new RuntimeException("Failed to obtain the Db Connection");
		return conn;

	}

	public void close(Connection conn) {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void setDerbyDbPath(String path) {
		bds.setUrl("jdbc:derby:"+path+";create=true");
	}

	public void setH2DbPath(String path) {
		bds.setUrl("jdbc:h2:"+path);
	}

	private enum DbProps {

		MYSQL("com.mysql.jdbc.Driver", 
				"jdbc:mysql://localhost:3306/emp", 
				"root", 
				""),

		DERBY("org.apache.derby.jdbc.EmbeddedDriver", 
				"jdbc:derby:emp;create=true", 
				"",
				""),

		H2("org.h2.Driver",
				"jdbc:h2:~/h2/emp",
				"emp",
				"h2Admin");

		private DbProps(String driverClass, String url, String username,
				String password) {
			this.driverClass = driverClass;
			this.url = url;
			this.username = username;
			this.password = password;
		}

		private String driverClass;
		private String url;
		private String username;
		private String password;

		public void setUrl(String url) {
			this.url = url;
		}
	}
}
