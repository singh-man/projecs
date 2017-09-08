package com.web.processor;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;

/**
 * Servlet implementation class ProcessorServlet
 */
public class ProcessorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private SessionProcessorBean sessionProcessorBean;

	@Override
    public void init(ServletConfig servletConfig) {
        WebApplicationContext springContext = (WebApplicationContext) servletConfig.getServletContext().getAttribute("springContext");
		sessionProcessorBean = (SessionProcessorBean)springContext.getBean("sessionProcessorBean");
    }
	
	private void processRequest(HttpServletRequest request, HttpServletResponse response) {
		System.out.println(sessionProcessorBean.getProvidedBean().getDesc());
		sessionProcessorBean.getProvidedBean().setDesc("Hell good");
		System.out.println(sessionProcessorBean.getProvidedBean().getDesc());
		System.out.println(sessionProcessorBean.getProvidedBeanSingleton().getDesc());
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}



	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

}
