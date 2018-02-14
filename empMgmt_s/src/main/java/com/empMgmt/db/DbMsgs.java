package com.empMgmt.db;

public enum DbMsgs {

	LOGIN_SUCCESS("Login Successfull"),
	LOGIN_FAIL("Credentials are wrong"),
	
	LOGOUT_SUCCESS("Logout Successfull"),
	ALREADY_LOGGED("Already 'Loged-In' for today"),
	
	EMPLOYEE_UPDATED("Employee details updated"),
	EMPLOYEE_UPDATION_FAILED("Failed to update Employee details"),
	EMPLOYEE_ADDED("New Employee added"),
	EMPLOYEE_ADDITION_FAILED("Failed to Add New Employee"),
	
	LOGIN_FORGET("you haven't logged-In today"), 
	
	EMPLOYEE_DELETED_SUCCESSFULLY("Employee Deleted from the system"), 
	EMPLOYEE_DELETION_FAILURE("Employee cann't be deleted from the system"), 
	FAILED_TO_ADD_A_NEW_EMPLOYEE_DUE_TO_SOME_MISSING_FIELDS("Failed to Add a new Employee due to some missing fields");
	

	
	private String msgs;
	
	private DbMsgs(String msgs) {
		this.msgs = msgs;
	}

	public String getMsgs() {
		return msgs;
	}
		
}
