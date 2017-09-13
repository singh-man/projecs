package com.empMgmt.dto;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.empMgmt.db.DbMsgs;

/**
 * Shared with JSP
 * will be a prototype class in spring env.
 */
@Component("employeeDto")
@Scope("prototype")
public class EmployeeDto {
	
	private String name;
	private String eid;
	private String role;
	private String dateOfJoining;
	
	private String msg;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public void setMsg(DbMsgs msg) {
		this.msg = msg.getMsgs();
	}
	public String getEid() {
		return eid;
	}
	public void setEid(String eid) {
		this.eid = eid;
	}
	public String getDateOfJoining() {
		return dateOfJoining;
	}
	public void setDateOfJoining(String dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}

}
