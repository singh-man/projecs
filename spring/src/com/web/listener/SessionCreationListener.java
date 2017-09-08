package com.web.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Application Lifecycle Listener implementation class SessionCreationListener
 *
 */
public class SessionCreationListener implements HttpSessionListener {

    public SessionCreationListener() {
        // TODO Auto-generated constructor stub
    }

    public void sessionCreated(HttpSessionEvent arg0) {
        System.out.println("SessionCreationListener: " + arg0.getSession());
    }

	/**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent arg0) {
        // TODO Auto-generated method stub
    }
	
}
