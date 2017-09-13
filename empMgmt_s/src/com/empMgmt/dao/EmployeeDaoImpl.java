package com.empMgmt.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.empMgmt.db.DbMsgs;
import com.empMgmt.entity.Employee;

public class EmployeeDaoImpl implements EmployeeDao {

	final private String CHECK_EMPLOYEE = "select * from employee where eid = ?";
	final private String INSERT_EMPLOYEE = "insert into employee (emp_name, eid, pwd, role, date_of_join) values (?,?,?,?,?)";
	final private String UPDATE_EMPLOYEE = "update employee set emp_name = ?, pwd = ?, role = ? where eid = ?";
	final private String GET_ALL_EMPLOYEES = "select * from employee order by emp_name";
	final private String DELETE_EMPLOYEE = "delete from employee where eid = ?"; //cascade delete from login_details table

	final private String GENERATE_REPORT = "select e.emp_name, e.eid, l.the_date, l.in_time, l.out_time from employee e, login_details l where " +
			"e.id = l.emp_id and l.the_date >= ? and the_date <= ? order by l.the_date, e.emp_name";

	private SimpleJdbcTemplate simpleJdbcTemplate;
	

	private Employee employee;
	

	/*public EmployeeDao(DaoHandlerSupport daoHandlerSupport) {
		this.employee = new Employee();
		this.daoHandlerSupport = daoHandlerSupport;
	}*/

	public DbMsgs addOrUpdateEmployee(final String name, final String eid, final String pwd, final String role) {
		
		try {
			Employee employee = simpleJdbcTemplate.queryForObject(CHECK_EMPLOYEE, new RowMapper<Employee>() {
				@Override
				public Employee mapRow(ResultSet rs, int arg1)
						throws SQLException {
					Employee emp = new Employee();
					emp.setEid(rs.getString("eid"));
					emp.setId(rs.getInt("id"));
					emp.setEmpName(rs.getString("emp_name"));
					emp.setRole(rs.getString("role"));
					return emp;
				}
			}, eid);
			int rowsAffected = simpleJdbcTemplate.update(UPDATE_EMPLOYEE, 
					((name != null && !name.trim().equals("")) ? name : employee.getEmpName()),
					((pwd != null && !pwd.trim().equals("")) ? pwd : employee.getPassword()),
					role,
					eid);
			return rowsAffected ==1 ? DbMsgs.EMPLOYEE_UPDATED : DbMsgs.EMPLOYEE_UPDATION_FAILED;
		} catch(EmptyResultDataAccessException ex) {
			if(name == null || name.trim().equals("") || pwd == null || pwd.trim().equals(""))
				return DbMsgs.FAILED_TO_ADD_A_NEW_EMPLOYEE_DUE_TO_SOME_MISSING_FIELDS;
			int rowsAffected = simpleJdbcTemplate.update(INSERT_EMPLOYEE, name, eid, pwd, role, new Date(System.currentTimeMillis()));
			return rowsAffected == 1 ? DbMsgs.EMPLOYEE_ADDED : DbMsgs.EMPLOYEE_ADDITION_FAILED;
		}
	}

	public List<Employee> listOfEmployees() {
		
		//List<Employee> employeeList = simpleJdbcTemplate.query(GET_ALL_EMPLOYEES, ParameterizedBeanPropertyRowMapper.newInstance(Employee.class));
		List<Employee> employeeList = simpleJdbcTemplate.query(GET_ALL_EMPLOYEES, new RowMapper<Employee>() {
			@Override
			public Employee mapRow(ResultSet rs, int arg1) throws SQLException {
				Employee emp = new Employee();
				emp.setEid(rs.getString("eid"));
				emp.setEmpName(rs.getString("emp_name"));
				emp.setId(rs.getInt("id"));
				emp.setRole(rs.getString("role"));
				emp.setPassword("UNKNOWN");
				emp.setDateOfJoining(rs.getDate("date_of_join"));
				return emp;
			}
		});
		return employeeList;
	}

	public DbMsgs deleteEmployee(final String eid) {
		
		if(simpleJdbcTemplate.update(DELETE_EMPLOYEE, eid) == 1)
			return DbMsgs.EMPLOYEE_DELETED_SUCCESSFULLY;
		else
			return DbMsgs.EMPLOYEE_DELETION_FAILURE;
	}

	public List<Object[]> generateReport(final java.util.Date sDate, final java.util.Date eDate) {return null;
	}

	
	public void setSimpleJdbcTemplate(SimpleJdbcTemplate simpleJdbcTemplate) {
		this.simpleJdbcTemplate = simpleJdbcTemplate;
	}
}
