package com.empMgmt.dao;

import java.sql.Connection;
import java.sql.SQLException;

public class DaoHandlerSupport {
	
	private Connection conn;
	
	public <T> T doInConn(Connection conn, DaoHandler daoHandler) {
		this.conn = conn;
		
		T t = daoHandler.execute();
		
		closeConn();
		
		return t;
	}
	
	private void closeConn() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
