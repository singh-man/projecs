package com.empMgmt.controller;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.empMgmt.handler.LoginHandler;

/**
 * Servlet implementation class LoginController
 */
@WebServlet("/login")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		//servletContext = config.getServletContext();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		throw new RuntimeException("This should not be called");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String eid = request.getParameter("eid");
		String password = request.getParameter("password");
		String button = request.getParameter("button");

		String returnMsg = null;
		
		/*****   START  ******/
		/* Handled by javascript now can be deleted */
		/*if(password == null || password.trim().equals("")) {
			returnMsg = "Please provide Password";
		} if(eid == null || eid.trim().equals("")) {
			returnMsg = "Please provide Employee Id";
		}if(returnMsg != null) {
			request.setAttribute("msg", returnMsg);
			request.getRequestDispatcher("/login.jsp").forward(request, response);
			return;
		}*/
		/****   STOP   ****/	
		
		LoginHandler loginHandler = new LoginHandler();

		String role = loginHandler.resovleCredentials(eid, password);
		if("admin".equals(role)) {
			request.getSession();
			request.getRequestDispatcher("/admin").forward(request, response);
		} else if("Credentials are wrong".equals(role)) {
			request.setAttribute("msg", role);
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		} else {
			
			if("Login".equals(button))
				returnMsg = loginHandler.handleLoginAction(eid, password);
			else {
				returnMsg = loginHandler.handleLogoutAction(eid, password);
				HttpSession session = request.getSession(false); 
				if(session != null)
					session.invalidate();
			}
			request.setAttribute("msg", returnMsg);
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}

	}
}
