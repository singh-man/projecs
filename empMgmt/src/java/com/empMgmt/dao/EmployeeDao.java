package com.empMgmt.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.empMgmt.db.Db;
import com.empMgmt.db.DbMsgs;
import com.empMgmt.entity.Employee;

public class EmployeeDao {

	Connection conn = Db.CONNECTION.getConnection();
	Employee employee = new Employee();
	
	final private String CHECK_EMPLOYEE = "select * from employee where eid = ?";
	final private String INSERT_EMPLOYEE = "insert into employee (emp_name, eid, pwd, role, date_of_join) values (?,?,?,?,?)";
	final private String UPDATE_EMPLOYEE = "update employee set emp_name = ?, pwd = ?, role = ? where eid = ?";
	final private String GET_ALL_EMPLOYEES = "select * from employee order by emp_name";
	final private String DELETE_EMPLOYEE = "delete from employee where eid = ?"; //cascade delete from login_details table
	
	final private String GENERATE_REPORT = "select e.emp_name, e.eid, l.the_date, l.in_time, l.out_time from employee e, login_details l where " +
			"e.id = l.emp_id and l.the_date >= ? and the_date <= ? order by l.the_date, e.emp_name";
	
	public DbMsgs addOrUpdateEmployee(String name, String eid, String pwd, String role) {
		try {
			PreparedStatement ps = conn.prepareStatement(CHECK_EMPLOYEE);
			ps.setString(1, eid);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				ps = conn.prepareStatement(UPDATE_EMPLOYEE);
				ps.setString(1, ((name != null && !name.trim().equals("")) ? name : rs.getString("emp_name")));
				ps.setString(2, ((pwd != null && !pwd.trim().equals("")) ? pwd : rs.getString("pwd")));
				ps.setString(3, role);
				ps.setString(4, eid);
				if(ps.executeUpdate() == 1)
					return DbMsgs.EMPLOYEE_UPDATED;
			}
			if(name == null || name.trim().equals("") || pwd == null || pwd.trim().equals(""))
				return DbMsgs.FAILED_TO_ADD_A_NEW_EMPLOYEE_DUE_TO_SOME_MISSING_FIELDS;
			ps = conn.prepareStatement(INSERT_EMPLOYEE);
			ps.setString(1, name);
			ps.setString(2, eid);
			ps.setString(3, pwd);
			ps.setString(4, role);
			ps.setDate(5, new Date(System.currentTimeMillis()));
			if(ps.executeUpdate() == 1) 
				return DbMsgs.EMPLOYEE_ADDED;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
		
	}

	public List<Employee> listOfEmployees() {
		try {
			PreparedStatement ps =  conn.prepareStatement(GET_ALL_EMPLOYEES);
			ResultSet rs = ps.executeQuery();
			List<Employee> employeeList = new ArrayList<Employee>();
			while(rs.next()) {
				Employee emp = new Employee();
				emp.setEid(rs.getString("eid"));
				emp.setEmpName(rs.getString("emp_name"));
				emp.setId(rs.getInt("id"));
				emp.setRole(rs.getString("role"));
				emp.setPassword("UNKNOWN");
				emp.setDateOfJoining(rs.getDate("date_of_join"));
				employeeList.add(emp);
			}
			return employeeList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public DbMsgs deleteEmployee(String eid) {
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement(DELETE_EMPLOYEE);
			ps.setString(1, eid);
			if(ps.executeUpdate() == 1)
				return DbMsgs.DELETED_SUCCESSFULLY;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Object[]> generateReport(java.util.Date sDate, java.util.Date eDate) {
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement(GENERATE_REPORT);
			ps.setDate(1, new Date(sDate.getTime()));
			ps.setDate(2, new Date(eDate.getTime()));
			ResultSet rs = ps.executeQuery();
			List<Object[]> objList = new LinkedList<Object[]>();
			while(rs.next()) {
				Object[] o = new Object[5]; // rs returns 5 columns
				o[0] = rs.getString("emp_name");
				o[1] = rs.getString("eid");
				o[2] = rs.getDate("the_date");
				o[3] = rs.getTime("in_time");
				o[4] = rs.getTime("out_time");
				objList.add(o);
			}
			return objList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
		
	}

}
