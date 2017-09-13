package com.listener;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.bean.MessageBean;

/**
 * Application Lifecycle Listener implementation class SessionCreationListener
 *
 */
public class SessionCreationListener implements HttpSessionListener {
	
	private HttpSession session;

    public SessionCreationListener() {
        // TODO Auto-generated constructor stub
    }

    public void sessionCreated(HttpSessionEvent arg0) {
    	session = arg0.getSession();
    	session.setAttribute("sBean", new MessageBean());
        System.out.println("SessionCreationListener: " + session.getId());
    }

	/**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent arg0) {
        // TODO Auto-generated method stub
    }
	
}
