package com.empMgmt.db;

import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.commons.dbcp.BasicDataSource;

public enum Db {

	CONNECTION;

	private BasicDataSource bds;
	
	private Db() {
		/*bds = new BasicDataSource();
		bds.setUrl(DbProps.H2.url);
		bds.setDriverClassName(DbProps.H2.driverClass);
		bds.setUsername(DbProps.H2.username);
		bds.setPassword(DbProps.H2.password);
		bds.setInitialSize(5);*/
	}
	
	public Connection getConnection() {
		Connection conn = null;
		try {
			/*Class.forName(DbProps.MYSQL.driverClass);
			conn = DriverManager.getConnection(DbProps.MYSQL.url, DbProps.MYSQL.username, DbProps.MYSQL.password);
			*/
			/*Class.forName(DbProps.DERBY.driverClass);
			conn = DriverManager.getConnection(DbProps.DERBY.url, DbProps.DERBY.username, DbProps.DERBY.password);
			*/
			Class.forName(DbProps.H2.driverClass);
			conn = DriverManager.getConnection(DbProps.H2.url, DbProps.H2.username, DbProps.H2.password);
			
			return conn;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void setDerbyDbPath(String path) {
		//bds.setUrl("jdbc:derby:"+path+";create=true");
	}
	
	public void setH2DbPath(String path) {
		//bds.setUrl("jdbc:h2:"+path);
		DbProps.H2.setUrl("jdbc:h2:"+path);
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
