package com.empMgmt.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.empMgmt.dao.LoginDao;
import com.empMgmt.db.DbMsgs;
import com.empMgmt.dto.EmployeeDto;
import com.empMgmt.entity.Employee;

@Component
public class LoginHandler {
	
	private LoginDao loginDao;
	private EmployeeDto employeeDto;

	@Autowired
	private LoginHandler(@Qualifier("loginDao")LoginDao loginDao, EmployeeDto employeeDto) {
		super();
		this.loginDao = loginDao;
		this.employeeDto = employeeDto;
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
