package com.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bean.BeanUtility;
import com.bean.KVPair;
import com.bean.MessageBean;

public class AdminServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private transient String password = "admin";
	
	private final String SUCCESS_PAGE = "/admin.jsp";
	
	private String downloadPath;
	private String uploadPath;
	private ServletContext servletContext;
	private HttpSession session;
       
    public AdminServlet() {
        super();
    }
    
    @Override
	public void init(ServletConfig servletConfig) {
		servletContext = servletConfig.getServletContext();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession();
		request.setAttribute(KVPair.R_BEAN.getValue(), BeanUtility.setRequestBean(servletContext, session, new MessageBean()));
		request.getRequestDispatcher(SUCCESS_PAGE).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession();
		String msg = null;
		downloadPath = request.getParameter(KVPair.DOWNLOAD_PATH.getValue());
		uploadPath = request.getParameter(KVPair.UPLOAD_PATH.getValue());
		if(password.equals(request.getParameter(KVPair.PASSWORD.getValue()))) {
			BeanUtility.setAttributes(servletContext, session, downloadPath, uploadPath);
			msg = "Path set successfully";
		}
		else
			msg = "Wrong/Missing Password";
		request.setAttribute("msg", msg);
		request.setAttribute(KVPair.R_BEAN.getValue(), BeanUtility.setRequestBean(servletContext, session, new MessageBean()));
		request.getRequestDispatcher(SUCCESS_PAGE).forward(request, response);
	}
}
