package com.empMgmt.handler;

import com.empMgmt.dao.LoginDao;
import com.empMgmt.db.DbMsgs;
import com.empMgmt.dto.EmployeeDto;
import com.empMgmt.entity.Employee;

public class LoginHandler {
	
	private LoginDao loginDao;
	private EmployeeDto loginDto;
	
	public LoginHandler() {
		loginDao = new LoginDao();
		loginDto = new EmployeeDto();
	}

	public String resovleCredentials(String eid, String password) {
		Employee employee = loginDao.authenticate(eid, password);
		if(employee == null)
			return DbMsgs.LOGIN_FAIL.getMsgs();
		else
			return employee.getRole();
	}

	public String handleLoginAction(String eid, String password) {
		DbMsgs dbMsg = loginDao.loginToday(eid, password);
		return dbMsg.getMsgs();
	}

	public String handleLogoutAction(String eid, String password) {
		DbMsgs dbMsg = loginDao.logoutToday(eid, password);
		return dbMsg.getMsgs();
	}
}
