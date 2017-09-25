package com.db;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;


public enum DbProps {

	POST_GRES("org.postgresql.Driver",
			"jdbc:postgresql://150.236.18.151:5433/bgw",
			"emmhssh_mm7",
			"thule"),
	ORACLE("oracle.jdbc.driver.OracleDriver",
			"jdbc:oracle:thin:@10.184.16.12:1521:bgw",
			"emmhssh_mm72",
			"thule"),
	MYSQL("com.mysql.jdbc.Driver",
			"jdbc:mysql://localhost:3306/mmdb",
			"root",
			""),
	DERBY("org.apache.derby.jdbc.EmbeddedDriver",
			"jdbc:derby:C:\\dev\\dev_mm73\\FMM_7.3_web\\mmdb1;create=true",
			"",
			""),
	H2("org.h2.Driver",
			"jdbc:h2:~/h2/test",
			"sa",
			""),
	SQLITEdb1("org.sqlite.JDBC",
			"jdbc:sqlite:/opt/lampp/htdocs/phpSqlLite/db1",
			"",
			""),
	SQLITEgre("org.sqlite.JDBC",
			"jdbc:sqlite:/opt/lampp/htdocs/phpSqlLite/gre.db",
			"",
			"");

	private DbProps(String driverClass, String url, String username,
			String password) {
		this.driverClass = driverClass;
		this.url = url;
		this.username = username;
		this.password = password;
		this.ds = getDataSource(this);
	}

	private DataSource ds;
	private String driverClass;
	private String url;
	private String username;
	private String password;

	public String getDriverClass() {
		return driverClass;
	}
	public String getUrl() {
		return url;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}

	private DataSource getDataSource(DbProps dbType) {

		/*Class.forName(driverClass);
		Connection conn = DriverManager.getConnection(url, username, password);*/
		BasicDataSource bds = new BasicDataSource();
		bds.setDriverClassName(dbType.getDriverClass());
		bds.setUrl(dbType.getUrl());
		bds.setUsername(dbType.getUsername());
		bds.setPassword(dbType.getPassword());
		return bds;
	}

	public Connection getConn() {

		try {
			return ds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
