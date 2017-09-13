package com.task;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.servlet.ForUrl;
import com.servlet.ModelAndView;
import com.servlet.ForUrl.ParamScope;

public class AdminTask {

	private transient String password = "admin";
	
	private final String SUCCESS_PAGE = "/admin.jsp";
	
	public void authenticate() {
		
	}
	
	@ForUrl(url="/admin")
	public ModelAndView<Object> setExposedPaths(@ForUrl(param="upPath") String upPath, @ForUrl(param="downPath") String downPath) {
		System.out.println(upPath + " : " +downPath);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("downloadPath", downPath);
		map.put("uploadPath", upPath);
		return new ModelAndView<Object>(SUCCESS_PAGE, map, ParamScope.APPLICATION);
	}
	
	@ForUrl(url="/rename")
	public void renameFile() {
		
	}
	
	@ForUrl(url="/delete")
	public void deleteFile() {
		
	}
	
	
}
