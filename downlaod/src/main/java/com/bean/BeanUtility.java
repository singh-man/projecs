package com.bean;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

public class BeanUtility {

	/**
	 * Sets the request bean for the client to use
	 */
	public static MessageBean setRequestBean(ServletContext ctx, HttpSession session, MessageBean rBean) {

		MessageBean cBean = (MessageBean) ctx.getAttribute(KVPair.C_BEAN.getValue());
		MessageBean sBean = (MessageBean) session.getAttribute(KVPair.S_BEAN.getValue());

		if(sBean.getUploadPath() != null)
			rBean.setUploadPath(sBean.getUploadPath());
		else
			rBean.setUploadPath(cBean.getUploadPath());

		if(sBean.getDownloadPath() != null)
			rBean.setDownloadPath(sBean.getDownloadPath());
		else
			rBean.setDownloadPath(cBean.getDownloadPath());

		return rBean;
	}
	
	/**
	 * Sets Bean to servletContext and session accordingly
	 */
	public static void setAttributes(ServletContext ctx, HttpSession session, String downloadPath, String uploadPath) {

		MessageBean cBean = (MessageBean) ctx.getAttribute(KVPair.C_BEAN.getValue());
		MessageBean sBean = (MessageBean) session.getAttribute(KVPair.S_BEAN.getValue());
		
		if(cBean.getDownloadPath() == null)
			cBean.setDownloadPath(downloadPath);
		else
			sBean.setDownloadPath(downloadPath);
		if(cBean.getUploadPath() == null)
			cBean.setUploadPath(uploadPath);
		else
			sBean.setUploadPath(uploadPath);	
	}

	/**
	 * Resolves download path to be used; if session present use session else context
	 */
	public static String getDownloadPath(ServletContext ctx, HttpSession session) {

		String downloadPath;
		MessageBean cBean = (MessageBean) ctx.getAttribute(KVPair.C_BEAN.getValue());
		MessageBean sBean = (MessageBean) session.getAttribute(KVPair.S_BEAN.getValue());

		if(sBean.getDownloadPath() != null)
			downloadPath = sBean.getDownloadPath();
		else
			downloadPath = cBean.getDownloadPath();

		return downloadPath;
	}

	/**
	 * Resolves upload path to be used; if session present use session else context
	 */
	public static String getUploadPath(ServletContext ctx, HttpSession session) {

		String uploadPath;
		MessageBean cBean = (MessageBean) ctx.getAttribute(KVPair.C_BEAN.getValue());
		MessageBean sBean = (MessageBean) session.getAttribute(KVPair.S_BEAN.getValue());

		if(sBean.getUploadPath() != null)
			uploadPath = sBean.getUploadPath();
		else
			uploadPath = cBean.getUploadPath();

		return uploadPath;
	}	
}
