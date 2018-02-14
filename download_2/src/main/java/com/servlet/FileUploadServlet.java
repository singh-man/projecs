package com.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.bean.BeanUtility;

@MultipartConfig
public class FileUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final String PAGE = "/uploadFile.jsp";
	private ServletContext servletContext;
	private HttpSession session;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		servletContext = config.getServletContext();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession();
		String msg = null;
		PrintWriter out = response.getWriter();
		try {
			// get access to file that is uploaded from client
			long t1 = System.currentTimeMillis();

			//with the following line the file is downloaded and stored to some temp folder in the server.
			Part p1 = request.getPart("file1");

			//this is to get the InputStream of the downloaded file.
			InputStream is = p1.getInputStream();

			String fileName = getFileName(p1);
			FileOutputStream os = new FileOutputStream(BeanUtility.getUploadPath(servletContext, session) + (fileName!=null?fileName:p1.getName()));

			// write bytes taken from uploaded file to target file
			//copyFileFromTmpToDestination_v1(is, os);
			copyFileFromTmpToDestination_v2(is, os);
			msg = "File Uploaded --" + fileName + "-- Successfully in: " + ((int)(System.currentTimeMillis() - t1)/1000) + " s";
		}
		catch(Exception ex) {
			msg = "Failure to upload: - " + ex.getMessage();
		}
		finally { 
			request.setAttribute("msg", msg);
			request.getRequestDispatcher(PAGE).forward(request, response);
		}
	}

	/**
	 * Avoid: takes a lot of time as it copies byte by byte
	 */
	@Deprecated
	private void copyFileFromTmpToDestination_v1(InputStream is, OutputStream os) throws IOException {
		int ch = is.read();
		while(ch != -1) {
			os.write(ch);
			ch = is.read();
		}
		os.close();
	}

	/**
	 * Efficient: Uses a 4 KB buffer for file copying
	 */
	private void copyFileFromTmpToDestination_v2(InputStream is, OutputStream os) throws IOException {
		int len = 0; 
		byte[] buff = new byte[4 * 1024];
		while((len = is.read(buff)) != -1) {
			os.write(buff,0,len);// write from buff starting from 0 till the len(length of byte read)
		}
		os.close();
	}

	/**
	 * Gets the name of the uploaded file
	 */
	private String getFileName(Part part) {
		String partHeader = part.getHeader("content-disposition");
		String[] params = partHeader.split(";");
		for(String s : params) {
			if(s.trim().startsWith("filename")) {
				int i = s.lastIndexOf(File.separator);
				if(i < 0) {
					//Strange issue: from Firefox only filename is available not the absolute path of the file
					int  j = s.lastIndexOf('=');
					return "/" + s.substring(j+2, s.length()-1);
				}
				return s.substring(i,s.length()-1);
			}
		}
		return null;
	}
}
