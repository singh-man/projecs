package com.empMgmt.controller;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.empMgmt.handler.EmployeeHandler;

/**
 * Servlet implementation class HandleEmployeeController
 */
@WebServlet("/handleEmployee")
public class HandleEmployeeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		throw new RuntimeException("shouldn't be called");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String name = request.getParameter("name");
		String eid = request.getParameter("eid");
		String pwd = request.getParameter("pwd");
		String role = request.getParameter("role");
		String button = request.getParameter("button");
		String action = request.getParameter("action");
		
		/*if(eid == null || eid.trim().equals("")) {
			request.setAttribute("msg", "Please provide a valid Employee id");
			request.getRequestDispatcher("/admin").forward(request, response);
			return;
		}*/
		
		EmployeeHandler employeeHandler = new EmployeeHandler();
		
		String returnMessage = null;
		if("Handle Employee".equals(button)) {
			returnMessage = employeeHandler.addOrUpdateEmployee(name, eid, pwd, role);
			request.setAttribute("msg", returnMessage);
			request.getRequestDispatcher("/admin").forward(request, response);
			return;
		}
			
		if("delete".equals(action)) {
			request.setAttribute("msg", employeeHandler.deleteEmployee(eid));
			request.getRequestDispatcher("/admin").forward(request, response);
		}
		
	}

}
