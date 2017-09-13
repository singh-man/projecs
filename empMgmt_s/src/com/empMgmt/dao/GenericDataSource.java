package com.empMgmt.dao;

import java.sql.SQLException;

import org.apache.commons.dbcp.BasicDataSource;

import com.empMgmt.db.Db;


public class GenericDataSource extends BasicDataSource {

	private static final long serialVersionUID = 3638051110380254833L;
	
	private String dbProductName;
	
	private GenericDataSource() {
		super();
		setUrl(Db.MYSQL.getUrl());
		setDriverClassName(Db.MYSQL.getDriverClass());
		setUsername(Db.MYSQL.getUsername());
		setPassword(Db.MYSQL.getPassword());
	}
	
	private void setUp() throws SQLException {
		dbProductName = getConnection().getMetaData().getDatabaseProductName();
	}

	public String getDbProductName() {
		return dbProductName;
	}

}

