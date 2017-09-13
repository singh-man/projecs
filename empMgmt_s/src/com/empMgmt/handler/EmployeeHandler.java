package com.empMgmt.handler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.empMgmt.dao.EmployeeDao;
import com.empMgmt.dao.EmployeeDaoImpl;
import com.empMgmt.db.DbMsgs;
import com.empMgmt.dto.EmployeeDto;
import com.empMgmt.entity.Employee;

@Component
public class EmployeeHandler {

	private EmployeeDao employeeDao;
	//private LoginDto loginDto;
	
	@Autowired
	public EmployeeHandler(@Qualifier("employeeDao")EmployeeDao employeeDao) {
		this.employeeDao = employeeDao;
		//loginDto = new LoginDto();
	}
	
	public String addOrUpdateEmployee(String name, String eid, String pwd, String role) {
		String msg = employeeDao.addOrUpdateEmployee(name, eid, pwd, role).getMsgs();
		if(msg == null)
			return "Some error occured";
		return msg;
	}

	public List<EmployeeDto> listOfEmployees() {
		List<Employee> employeeList = employeeDao.listOfEmployees();
		List<EmployeeDto> emList = null;
		if(employeeList != null) {
			emList = new ArrayList<EmployeeDto>();
			for(Employee emp : employeeList) {
				EmployeeDto eDto = new EmployeeDto();
				eDto.setName(emp.getEmpName());
				eDto.setRole(emp.getRole());
				eDto.setEid(emp.getEid());
				eDto.setDateOfJoining(new SimpleDateFormat("dd/MM/yyyy").format(emp.getDateOfJoining()));
				emList.add(eDto);
			}
		}
		return emList;
	}

	public String deleteEmployee(String eid) {
		DbMsgs result = employeeDao.deleteEmployee(eid);
		if(result != null)
			return eid + " : " + result.getMsgs();
		return "Nothing to delete";
	}
	
}
