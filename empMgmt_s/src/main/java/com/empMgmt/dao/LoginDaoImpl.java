package com.empMgmt.dao;

import com.empMgmt.db.DbMsgs;
import com.empMgmt.entity.Employee;
import com.empMgmt.entity.LoginDetails;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

public class LoginDaoImpl implements LoginDao {

	final String LOGIN = "select * from employee where eid = ? and pwd = ?";
	final String INSERT_LOGIN = "insert into login_details (the_date, in_time, emp_id) values (?,?,?)";
	final String INSERT_LOGOUT = "update login_details set out_time = ? where id = ?";
	final String ALREADY_LOGGED = "select * from login_details where emp_id = ? and the_date = ?";

	private Employee employee;
	private SimpleJdbcTemplate simpleJdbcTemplate;

	/*public LoginDao(DaoHandlerSupport daoHandlerSupport) {
		this.daoHandlerSupport = daoHandlerSupport;
		this.employee = new Employee();
	}*/

	public Employee authenticate(final String eid, final String password) {
		employee = simpleJdbcTemplate.queryForObject(LOGIN, new RowMapper<Employee>() {

			@Override
			public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
				Employee employee = new Employee();
				employee.setId(rs.getInt("id"));
				employee.setEid(rs.getString("eid"));
				employee.setEmpName(rs.getString("emp_name"));
				employee.setRole(rs.getString("role"));
				return employee;
			}
		}, eid, password);

		return employee;
	}

	public DbMsgs loginToday(final String eid, final String password) {

		if(isAlreadyLoggedForToday(employee.getId()) != null) {
			return DbMsgs.ALREADY_LOGGED;
		}

		long time = System.currentTimeMillis();
		if(1 == simpleJdbcTemplate.update(INSERT_LOGIN, new Date(time), 
				new Time(time), employee.getId())) {
			return DbMsgs.LOGIN_SUCCESS;
		}
		return null;
	}

	private LoginDetails isAlreadyLoggedForToday(final int id) {
		LoginDetails loginDetails = null;
		try {
			loginDetails = simpleJdbcTemplate.queryForObject(ALREADY_LOGGED, new RowMapper<LoginDetails>() {

				@Override
				public LoginDetails mapRow(ResultSet rs, int arg1) throws SQLException {
					LoginDetails loginDetails = new LoginDetails();
					loginDetails.setId(rs.getInt("id"));
					return loginDetails;
				}
			}, id, new Date(System.currentTimeMillis()));
		} catch(EmptyResultDataAccessException ex) {
		}
		return loginDetails;
	}

	public DbMsgs logoutToday(final String eid, final String password) {

		LoginDetails loginDetails = null;
		if((loginDetails = isAlreadyLoggedForToday(employee.getId())) == null) {
			return DbMsgs.LOGIN_FORGET;
		}

		long time = System.currentTimeMillis();
		if(1 == simpleJdbcTemplate.update(INSERT_LOGOUT, 
				new Time(time), loginDetails.getId())) {
			return DbMsgs.LOGOUT_SUCCESS;
		}
		return null;
	}

	public void setSimpleJdbcTemplate(SimpleJdbcTemplate simpleJdbcTemplate) {
		this.simpleJdbcTemplate = simpleJdbcTemplate;
	}
	
}