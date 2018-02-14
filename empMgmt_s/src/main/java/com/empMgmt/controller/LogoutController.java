package com.empMgmt.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="/logout")
public class LogoutController {

	@RequestMapping(method=RequestMethod.POST)
	public ModelAndView handleLogout(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		request.getSession(false).invalidate();
		mav.setViewName("/login");
		return mav;
	}

}
