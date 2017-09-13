package com.empMgmt.dao;

import java.util.List;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.empMgmt.db.DbMsgs;
import com.empMgmt.entity.Employee;

public interface EmployeeDao extends Dao {
	
	public DbMsgs addOrUpdateEmployee(final String name, final String eid, final String pwd, final String role);

	public List<Employee> listOfEmployees();

	public DbMsgs deleteEmployee(final String eid);

	public List<Object[]> generateReport(final java.util.Date sDate, final java.util.Date eDate);


	public void setSimpleJdbcTemplate(SimpleJdbcTemplate simpleJdbcTemplate);
}
