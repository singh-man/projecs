package com.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Servlet Filter
 * 
 * Demo of Filter 
 * 1. request parameter manipulation
 * 2. Exception handling (Note: Try to keep RuntimeException from servlets)
 */
public class RequestFilter_1 implements Filter {

	public RequestFilter_1() {
		System.out.println("RequestFilter_1: constructor -> container calls this; all filter are initilized when container starts");
	}

	public void init(FilterConfig fConfig) throws ServletException {
		System.out.println("RequestFilter_1: init");
	}
	public void destroy() {
		System.out.println("RequestFilter_1: destroy");
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		System.out.println("RequestFilter_1: name: " + request.getParameter("name"));
		request.setAttribute("surname", "singh");
		try {
			chain.doFilter(request, response);
			System.out.println("RequestFilter_1: processing done");
			/*
			 * Response stream has been flushed; response object can't be processed here then. 
			 */
		} catch(Exception e){
			System.out.println(e.getMessage() + " may be an operation on response after calling the flush on PW");
		} finally {
			System.out.println("RequestFilter_1: processing done - finally block" );
		}
	}
}
