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
 * For Filter sequence demo
 */
public class RequestFilter_2 implements Filter {

    public RequestFilter_2() {
    	System.out.println("RequestFilter_2: constructor -> container calls this; all filter are initilized when container starts");
    }

    public void init(FilterConfig fConfig) throws ServletException {
		System.out.println("RequestFilter_2: init; parameter: " + fConfig.getInitParameter("filter"));
	}
	public void destroy() {
		System.out.println("RequestFilter_2: destroy");
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		System.out.println("RequestFilter_2: filters called in sequence");
		chain.doFilter(request, response);
		System.out.println("RequestFilter_2: processing done");
	}

}
