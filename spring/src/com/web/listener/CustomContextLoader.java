package com.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Application Lifecycle Listener implementation class HierarchialLoaderListner
 *
 */
@WebListener
public class CustomContextLoader extends ContextLoaderListener {

    /**
     * Default constructor. 
     */
    public CustomContextLoader() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0) {
    	super.contextInitialized(arg0);
    	System.out.println("LoginServlet: init - Spring can not inject the bean in a Servlet hence the workaround is to done");
        WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(arg0.getServletContext());
        arg0.getServletContext().setAttribute("springContext", springContext);
    }
	
}
