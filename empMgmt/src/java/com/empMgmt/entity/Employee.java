package com.empMgmt.entity;

import java.sql.Date;

public class Employee {

	private int id;
	private String empName;
	private String eid;
	private transient String pwd;
	private String role;
	private Date dateOfJoining;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getPassword() {
		return pwd;
	}
	public void setPassword(String password) {
		this.pwd = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getEid() {
		return eid;
	}
	public void setEid(String eid) {
		this.eid = eid;
	}
	public Date getDateOfJoining() {
		return dateOfJoining;
	}
	public void setDateOfJoining(Date dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}
}
