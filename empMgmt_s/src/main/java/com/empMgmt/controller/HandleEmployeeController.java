package com.empMgmt.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.empMgmt.handler.EmployeeHandler;

@Controller
@RequestMapping(value="/handleEmployee/*")
public class HandleEmployeeController {

	private EmployeeHandler employeeHandler;
	private AdminController adminController;


	// This should always be at method level not at class
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView shouldNotBeHere() {
		throw new RuntimeException();
	}

	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public ModelAndView deleteEmployee(@RequestParam("eid") String eid) {
		String returnMessage = employeeHandler.deleteEmployee(eid);
		ModelAndView mav = adminController.handleAdmin();
		mav.addObject("msg", returnMessage);
		return mav;
	}

	@RequestMapping(value="/handle",method=RequestMethod.POST)
	public ModelAndView addOrUpdateEmployee(@RequestParam("name") String name, 
			@RequestParam("eid") String eid,
			@RequestParam("pwd") String pwd,
			@RequestParam("role") String role,
			HttpServletRequest request) {

		String returnMessage = null;
		returnMessage = employeeHandler.addOrUpdateEmployee(name, eid, pwd, role);

		ModelAndView mav = adminController.handleAdmin();
		mav.addObject("msg", returnMessage);
		return mav;
	}

	@Autowired
	public void setEmployeeHandler(EmployeeHandler employeeHandler) {
		this.employeeHandler = employeeHandler;
	}

	@Autowired
	public void setAdminController(AdminController adminController) {
		this.adminController = adminController;
	}

}

/*
 * Forwarding to another controller from one Spring controller can be done in two ways.
 * 1. mav.setViewName("forward:<mapping of the other controller>");
 * 2. <other controller instance>.<method to be called(parameters)>
 * 
 * both eventually does the same task
 * 
 * Diff:-
 * 1. creates a new request and response thread.
 * 2. A simple method call under the existing thread
 * 
 * I am inclined for the second one; although the first one has its own advantages.
 * 
 * Demarcation between "forward:<>" and "redirect:<>"; the later triggers the GET method.
 */