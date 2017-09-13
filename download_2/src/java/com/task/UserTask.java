package com.task;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import com.servlet.ForUrl;
import com.servlet.ForUrl.ParamScope;
import com.servlet.ModelAndView;

public class UserTask {

	private final String HOME_PAGE = "/index.jsp";
	private final String SUCCESS_PAGE = "/downloadFile.jsp";

	@ForUrl(url="/upload")
	public void upload() {

	}

	@ForUrl(url="/download")
	public void download() {

	}

	@ForUrl(url="/list")
	public ModelAndView<Object> listFiles(@ForUrl(param="downloadPath") String downloadPath) {
		try {
			File[] files = new File(downloadPath).listFiles();
			Map<String, Long> fileSizeMap = new TreeMap<String, Long>();

			for(int i = 0; i < files.length; i++) {
				if(files[i].isFile())
					fileSizeMap.put(files[i].getName(), files[i].length());
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("files", fileSizeMap);
			return new ModelAndView<Object>(SUCCESS_PAGE, map, ParamScope.REQUEST);
		} catch(Exception e) {
			String msg = "Sorry! path missing. Please contact your Admin";
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("msg", msg);
			return new ModelAndView<Object>(HOME_PAGE, map, ParamScope.REQUEST);
		}
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
}
