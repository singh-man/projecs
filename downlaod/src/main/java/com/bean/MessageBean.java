package com.bean;

import java.util.List;
/**
 * Associated to context, session and request scope
 * @author emmhssh
 *
 */
public class MessageBean {

	private volatile transient String password = "admin";
	
	private String uploadPath;
	private String downloadPath;
	
	public String getUploadPath() {
		return uploadPath;
	}

	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}

	public String getDownloadPath() {
		return downloadPath;
	}

	public void setDownloadPath(String downloadPath) {
		this.downloadPath = downloadPath;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
