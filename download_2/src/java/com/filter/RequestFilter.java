package com.filter;

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
public class RequestFilter implements Filter {

	public void init(FilterConfig fConfig) throws ServletException {
	}
	
	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		try {
			chain.doFilter(request, response);
			//System.out.println("RequestFilter_1: processing done");
			/*
			 * Response stream has been flushed; response object can't be processed here then. 
			 */
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println(" may be an operation on response after calling the flush on PW");
		}
	}
}
