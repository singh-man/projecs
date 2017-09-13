package com.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DispatchServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private UrlResolver urlResolver;
	
	private String ctxServeltPath;
	
    public DispatchServlet() {
        super();
    }
    
    @Override
	public void init(ServletConfig servletConfig) {
    	try {
			urlResolver = new UrlResolver();
			urlResolver.setServletContext(servletConfig.getServletContext());
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(ctxServeltPath == null) {
			ctxServeltPath = request.getContextPath() + "/" + request.getServletPath();
		}
		String requestUri = request.getRequestURI();
		Object o = urlResolver.processUrl(requestUri.substring(ctxServeltPath.length() - 1), request, response);
		if(o != null && o instanceof ModelAndView) {
			ModelAndView mav = (ModelAndView) o;
			request.getRequestDispatcher(mav.getView()).forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
