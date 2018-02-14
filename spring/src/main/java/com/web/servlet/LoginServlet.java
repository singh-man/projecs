/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.web.spring.fileUpDown.SpringContextAware;

/**
 * 
 * Test with:
 * http://localhost:8080/zzz_spring/loginServlet?name=manish&exception=throw
 * 
 * passed parameters
 * name = manish
 * exception = throw
 *
 * @author emmhssh
 */
public class LoginServlet extends HttpServlet {

	private static int static_count;
    private int count;
    private SingletonBean singletonBean;
    private RequestBean requestBean;
    private SessionBean sessionBean;
    private PrototypeBean prototypeBean;
    
    public LoginServlet() {
		super();
		System.out.println("LoginServlet: constructor - container calls this only once");
	}
    
    @Override
    public void init(ServletConfig servletConfig) {
        /*System.out.println("LoginServlet: init - Spring can not inject the bean in a Servlet hence the workaround is to done");
        WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(servletConfig.getServletContext());
        singletonBean = (SingletonBean)springContext.getBean("singletonBean");
        requestBean = (RequestBean)springContext.getBean("requestBean");
        sessionBean = (SessionBean)springContext.getBean("sessionBean");*/
    	singletonBean = SpringContextAware.getBean(SingletonBean.class);
        requestBean = SpringContextAware.getBean(RequestBean.class);
        sessionBean = SpringContextAware.getBean(SessionBean.class);
        prototypeBean = SpringContextAware.getBean(PrototypeBean.class);
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("LoginServlet: hit count: static/instance: " + ++static_count + "/" + ++count);
        System.out.println("LoginServlet surname: " + request.getAttribute("surname"));
        
        HttpSession session = request.getSession();
        
        int singleInt = singletonBean.getSingletonBean();
        int requestInt = requestBean.getRequestBean();
        int sessionInt = sessionBean.getSessionBean();
        int prototypeInt = prototypeBean.getPrototypeBean();
        System.out.println("singletonBean: " + singleInt);singletonBean.setSingletonBean(++singleInt);
        System.out.println("requestBean: " + requestInt);requestBean.setRequestBean(++requestInt);
        System.out.println("sessionBean: " + sessionInt);sessionBean.setSessionBean(++sessionInt);
        System.out.println("prototypeBean: " + prototypeInt);prototypeBean.setPrototypeBean(++prototypeInt);
        
        if(request.getParameter("exception").equals("throw")) {
        	throw new RuntimeException("Servlet throwing RuntimeException");
        }
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
/**
 * Output of the test: Demo of bean scope = singleton/request and session
 * 
 * hits with Firefox -> Eclipse -> (again) Firefox

Spring can not inject the bean in a Servlet hence the workaorund is to used 

//via Firefox
Servlet hit count: static/instance: 1/1
singletonBean: 10
requestBean: 100
sessionBean: 1000
Servlet hit count: static/instance: 2/2
singletonBean: 11
requestBean: 100			// New instance for each request; hence never increases
sessionBean: 1001
Servlet hit count: static/instance: 3/3
singletonBean: 12
requestBean: 100
sessionBean: 1002			// New instance for each session; check via Eclipse and after that again via Firefox
Servlet hit count: static/instance: 4/4
singletonBean: 13
requestBean: 100
sessionBean: 1003			// this session is continued after Eclipse

// via Eclipse
Servlet hit count: static/instance: 5/5
singletonBean: 14
requestBean: 100			// Still new instance for each request
sessionBean: 1000			// new instance for new session via eclipse
Servlet hit count: static/instance: 6/6
singletonBean: 15
requestBean: 100
sessionBean: 1001
Servlet hit count: static/instance: 7/7
singletonBean: 16
requestBean: 100
sessionBean: 1002
.
.
.
Servlet hit count: static/instance: 17/17
singletonBean: 26
requestBean: 100
sessionBean: 1012

//via firefox
singletonBean: 27
requestBean: 100
sessionBean: 1004			// continuing the firefox session
Servlet hit count: static/instance: 19/19
singletonBean: 28
requestBean: 100
sessionBean: 1005
Servlet hit count: static/instance: 20/20
singletonBean: 29
requestBean: 100
sessionBean: 1006

 * 
*/