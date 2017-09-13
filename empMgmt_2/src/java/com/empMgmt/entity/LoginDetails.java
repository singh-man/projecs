package com.empMgmt.entity;

import java.sql.Date;
import java.sql.Time;

public class LoginDetails {

	private int id;
	private Date theDate;
	private Time inTime;
	private Time outTime;
	private int empId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getTheDate() {
		return theDate;
	}
	public void setTheDate(Date theDate) {
		this.theDate = theDate;
	}
	public Time getInTime() {
		return inTime;
	}
	public void setInTime(Time inTime) {
		this.inTime = inTime;
	}
	public Time getOutTime() {
		return outTime;
	}
	public void setOutTime(Time outTime) {
		this.outTime = outTime;
	}
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
}
