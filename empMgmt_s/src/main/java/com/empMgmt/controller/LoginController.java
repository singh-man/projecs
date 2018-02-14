package com.empMgmt.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.empMgmt.handler.LoginHandler;

/**
 * Servlet implementation class LoginController
 */
@Controller
@RequestMapping(value="/login")
public class LoginController {
	private static final long serialVersionUID = 1L;
	
	private LoginHandler loginHandler;
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView handleFirstLogin() {
		return new ModelAndView("login");
	}

	@RequestMapping(method=RequestMethod.POST)
	public ModelAndView handleLogin(@RequestParam("eid") String eid, 
			@RequestParam("password") String password, 
			@RequestParam("button") String button, HttpServletRequest request) {

		String returnMsg = null;
		ModelAndView mav = new ModelAndView();

		//LoginHandler loginHandler = new LoginHandler();

		String role = loginHandler.resovleCredentials(eid, password);

		if("admin".equals(role)) {
			request.getSession();
			/*
			 * Control being forwarded to another controller;
			 * Hence the view resolver is not triggered;
			 * this requires the full URL from root to be provided.
			 */
			mav.setViewName("forward:/jsp/admin");
		} else if("Credentials are wrong".equals(role)) {
			mav.setViewName("login");
			mav.addObject("msg", role);
		} else {

			if("Login".equals(button))
				returnMsg = loginHandler.handleLoginAction(eid, password);
			else {
				returnMsg = loginHandler.handleLogoutAction(eid, password);
				HttpSession session = request.getSession(false); 
				if(session != null)
					session.invalidate();
			}
			mav.setViewName("login");
			mav.addObject("msg", returnMsg);
		}

		return mav;
	}

	@Autowired
	public void setLoginHandler(LoginHandler loginHandler) {
		this.loginHandler = loginHandler;
	}
	
	@Autowired
	private AdminController adminController;
}
