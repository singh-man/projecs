package com.empMgmt.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.empMgmt.dto.EmployeeDto;
import com.empMgmt.handler.EmployeeHandler;

/**
 * Servlet implementation class AdminController
 */
@WebServlet("/admin")
public class AdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//request.setAttribute("msg", role);
		//request.getRequestDispatcher("/admin.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String button = request.getParameter("button");
		if(button !=null  && button.equals("Logout")) {
			request.getSession(false).invalidate();
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		} else {
			EmployeeHandler employeeHandler = new EmployeeHandler();

			List<EmployeeDto> employeeList = employeeHandler.listOfEmployees();
			request.setAttribute("employeeList", employeeList);
			request.getRequestDispatcher("/admin.jsp").forward(request, response);
		}
	}

}
