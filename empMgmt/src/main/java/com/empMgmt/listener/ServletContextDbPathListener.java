package com.empMgmt.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.empMgmt.db.Db;

/**
 * Application Lifecycle Listener implementation class ServletContextDerbyListener
 *
 */
@WebListener
public class ServletContextDbPathListener implements ServletContextListener {

	/**
	 * Default constructor. 
	 */
	public ServletContextDbPathListener() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent arg0) {

		String derbyAbsPath = arg0.getServletContext().getRealPath("/WEB-INF/resources/derby/emp");
		String h2AbsPath = arg0.getServletContext().getRealPath("/WEB-INF/resources/h2/emp/emp");
		
		//Db.CONNECTION.setDerbyDbPath(derbyAbsPath);
		Db.CONNECTION.setH2DbPath(h2AbsPath);
		
		//System.out.println("Path Used: " + derbyAbsPath + " : " + h2AbsPath);
	}

	/**
	 * @see ServletContextListener#contextDestroyed(ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
	}

}
