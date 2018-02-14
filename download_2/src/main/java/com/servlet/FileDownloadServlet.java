package com.servlet;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bean.BeanUtility;
import com.bean.KVPair;

public class FileDownloadServlet extends HttpServlet {

	private final String HOME_PAGE = "/index.jsp";
	private final String SUCCESS_PAGE = "/downloadFile.jsp";
	
	private String downloadPath;
	private String fileName;
	private ServletContext servletContext;
	private HttpSession session;

	@Override
	public void init(ServletConfig servletConfig) {
		servletContext = servletConfig.getServletContext();
	}

	/**
	 * Slow/hangs for huge files; don't use
	 */
	@Deprecated
	private void parseResponse(OutputStream out, FileInputStream fis) throws IOException {
		FileChannel channel = fis.getChannel();
		int len = 0;
		ByteBuffer buff = ByteBuffer.allocate(2048);
		while((len = channel.read(buff)) > -1) {
			out.write(buff.array(), 0, len);
		}
		out.flush();
	}
	
	private void downloadFile(OutputStream out, FileInputStream fis) throws IOException {
		DataInputStream dis = new DataInputStream(fis); //FileInputStream can also be used but its better here
		int len = 0;
		byte[] buff = new byte[2 * 1048];
		while((len = dis.read(buff)) > -1) {
			out.write(buff, 0, len);
		}
		out.flush();
	}

	/**
	 * Sets header for file download as attachment.
	 * Works with any kind of file
	 * @param response
	 */
	private void setHeader(HttpServletResponse response){
		response.setContentType("application/octet-stream" );
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"" );
	}

	/**
	 * Display the list of files in the given directory
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession();
		String msg;
		downloadPath = BeanUtility.getDownloadPath(servletContext, session);
		try {
			File[] files = new File(downloadPath).listFiles();
			Map<String, Long> fileSizeMap = new TreeMap<String, Long>();

			for(int i = 0; i < files.length; i++) {
				if(files[i].isFile())
					fileSizeMap.put(files[i].getName(), files[i].length());
			}
			request.setAttribute("files",fileSizeMap);
			request.getRequestDispatcher(SUCCESS_PAGE).forward(request, response);
		} catch(Exception e) {
			msg = "Sorry! path missing. Please contact your Admin";
			request.setAttribute("msg",msg);
			request.getRequestDispatcher(HOME_PAGE).forward(request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession();
		if(request.getParameter(KVPair.GET_FILE.getValue()) != null)
			fileName = request.getParameter(KVPair.GET_FILE.getValue());

		FileInputStream fis = new FileInputStream(new File(BeanUtility.getDownloadPath(servletContext, session) + "/" + fileName));
	
		OutputStream out = response.getOutputStream();
		
		setHeader(response);
		//parseResponse(out, fis);

		downloadFile(out, fis);
	}
}
