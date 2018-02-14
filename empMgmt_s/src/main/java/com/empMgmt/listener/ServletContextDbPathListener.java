package com.empMgmt.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.empMgmt.dao.GenericDataSource;
import com.empMgmt.db.Db;

/**
 * Application Lifecycle Listener implementation class ServletContextDerbyListener
 *
 */
@WebListener
public class ServletContextDbPathListener implements ServletContextListener {

	private GenericDataSource genericDataSource;
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
		
		WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(arg0.getServletContext());
		genericDataSource = springContext.getBean(GenericDataSource.class);

		String derbyAbsPath = arg0.getServletContext().getRealPath("/WEB-INF/resources/derby/emp");
		String h2AbsPath = arg0.getServletContext().getRealPath("/WEB-INF/resources/h2/emp/emp");
		
		//Db.CONNECTION.setDerbyDbPath(derbyAbsPath);
		//Db.CONN.setH2DbPath(h2AbsPath);
		System.setProperty("h2DbPath", h2AbsPath);
		/*genericDataSource.setUrl(h2AbsPath);
		genericDataSource.setDriverClassName(Db.H2.getDriverClass());
		genericDataSource.setUsername(Db.H2.getUsername());
		genericDataSource.setPassword((Db.H2.getPassword()));*/
		
		//System.out.println("Path Used: " + derbyAbsPath + " : " + h2AbsPath);
	}

	/**
	 * @see ServletContextListener#contextDestroyed(ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
	}

}
