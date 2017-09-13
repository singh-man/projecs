package com.empMgmt.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.empMgmt.dto.EmployeeDto;
import com.empMgmt.handler.EmployeeHandler;

/**
 * Servlet implementation class AdminController
 */
@Controller
@RequestMapping(value="/admin")
public class AdminController {
	private static final long serialVersionUID = 1L;

	private EmployeeHandler employeeHandler;

	// Always at method level not at class
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView shouldNotBeHere(HttpServletRequest request) {
		throw new RuntimeException();
		//return handleAdmin(request);
	}

	@RequestMapping(method=RequestMethod.POST)
	public ModelAndView handleAdmin() {

		ModelAndView mav =  new ModelAndView();
		List<EmployeeDto> employeeList = employeeHandler.listOfEmployees();
		mav.addObject("employeeList", employeeList);
		mav.setViewName("/admin");
		return mav;
	}

	@Autowired
	public void setEmployeeHandler(EmployeeHandler employeeHandler) {
		this.employeeHandler = employeeHandler;
	}

}
