package com.web.spring.fileUpDown;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
public class AdminController {

	private ServletContext servletContext;
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView getAdminPage(Model model) {
		model.addAttribute(new DownUpBean());
		return new ModelAndView("/admin", "bean", new DownUpBean());
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ModelAndView postAdminPage(@RequestParam("downloadPath") String downloadPath, @RequestParam("uploadPath") String uploadPath, Model mod) {
		
		mod.addAttribute(new DownUpBean());
		return new ModelAndView("/admin", "bean", mod);
	}

	@Autowired
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
		System.out.println("Demo: to access ServletContext in @Controller of Spring using @Autowired");
	}
}
