package com.empMgmt.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

import com.empMgmt.db.DbMsgs;
import com.empMgmt.entity.Employee;
import com.empMgmt.entity.LoginDetails;

public class LoginDao {

	final String LOGIN = "select * from employee where eid = ? and pwd = ?";
	final String INSERT_LOGIN = "insert into login_details (the_date, in_time, emp_id) values (?,?,?)";
	final String INSERT_LOGOUT = "update login_details set out_time = ? where id = ?";
	final String ALREADY_LOGGED = "select * from login_details where emp_id = ? and the_date = ?";

	private DaoHandlerSupport daoHandlerSupport;
	private Employee employee;

	public LoginDao(DaoHandlerSupport daoHandlerSupport) {
		this.daoHandlerSupport = daoHandlerSupport;
		this.employee = new Employee();
	}


	public Employee authenticate(final String eid, final String password, final Connection conn) {

		employee = daoHandlerSupport.doInConn(conn, new DaoHandler() {

			@Override
			public Employee execute() {
				try {
					PreparedStatement ps = conn.prepareStatement(LOGIN);
					ps.setString(1, eid);
					ps.setString(2, password);
					ResultSet rs = ps.executeQuery();
					while(rs.next()) {
						employee.setId(rs.getInt("id"));
						employee.setEid(rs.getString("eid"));
						employee.setEmpName(rs.getString("emp_name"));
						employee.setRole(rs.getString("role"));
						return employee;
					} 
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return null;
			}
		});
		return employee;
	}

	public DbMsgs loginToday(final String eid, final String password, final Connection conn) {

		DbMsgs dbMsgs = daoHandlerSupport.doInConn(conn, new DaoHandler() {

			@Override
			public DbMsgs execute() {
				try {
					if(isAlreadyLoggedForToday(employee.getId(), conn) != null) {
						return DbMsgs.ALREADY_LOGGED;
					}

					long time = System.currentTimeMillis();
					PreparedStatement ps = conn.prepareStatement(INSERT_LOGIN);
					ps.setDate(1, new Date(time));
					ps.setTime(2, new Time(time));
					ps.setInt(3, employee.getId());
					if(ps.executeUpdate() == 1) {
						return DbMsgs.LOGIN_SUCCESS;
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return null;
			}
		});
		return dbMsgs;
	}

	private LoginDetails isAlreadyLoggedForToday(final int id, final Connection conn) {

		try {
			PreparedStatement ps = conn.prepareStatement(ALREADY_LOGGED);
			ps.setInt(1, id);
			ps.setDate(2, new Date(System.currentTimeMillis()));
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				LoginDetails loginDetails = new LoginDetails();
				loginDetails.setId(rs.getInt("id"));
				return loginDetails;
			}
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
		return null;

	}

	public DbMsgs logoutToday(final String eid, final String password, final Connection conn) {
		DbMsgs dbMsgs = daoHandlerSupport.doInConn(conn, new DaoHandler() {

			@Override
			public DbMsgs execute() {
				try {
					LoginDetails loginDetails = null;
					if((loginDetails = isAlreadyLoggedForToday(employee.getId(), conn)) == null) {
						return DbMsgs.LOGIN_FORGET;
					}

					long time = System.currentTimeMillis();
					PreparedStatement ps = conn.prepareStatement(INSERT_LOGOUT);
					ps.setTime(1, new Time(time));
					ps.setInt(2, loginDetails.getId());
					if(ps.executeUpdate() == 1) {
						return DbMsgs.LOGOUT_SUCCESS;
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return null;
			}
		});
		return dbMsgs;		
	}

}
