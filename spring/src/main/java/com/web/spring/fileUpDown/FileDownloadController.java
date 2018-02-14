package com.web.spring.fileUpDown;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
//@RequestMapping("/file")
public class FileDownloadController {
	
	String dirPath = "C:/manish/mov";

	private void downloadFile(FileInputStream fis, OutputStream out) throws IOException {
		DataInputStream dis = new DataInputStream(fis); //FileInputStream can also be used but its better here
		int len = 0;
		byte[] buff = new byte[2048];
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
	private void setHeader(HttpServletResponse response, String fileName){
		response.setContentType("application/octet-stream" );
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"" );
	}
	
	/**
	 * Note: HttpServletResponse or even Request can be injected into here; method name can be any
	 *  
	 * @param fileName
	 * @param response
	 * @throws FileNotFoundException
	 */
	@RequestMapping(value="/file",method=RequestMethod.POST)
	public void downloadFile(@RequestParam("file") String fileName, HttpServletResponse response) throws FileNotFoundException{
		
		setHeader(response, fileName);
		
		FileInputStream fis = new FileInputStream(new File(dirPath+ "/" + fileName));
		
		try {
			downloadFile(fis, response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println(fileName);
	}
	
	@RequestMapping(value="/download",method=RequestMethod.GET)
	public ModelAndView getFilesList(Model model) {
		File[] files = new File(dirPath).listFiles();
		List<String> fileNameList = new ArrayList<String>();
		for(int i = 0; i < files.length; i++) {
			if(files[i].isFile())
				fileNameList.add(files[i].getName());
		}
		model.addAttribute("files",fileNameList);
		ModelAndView mav = new ModelAndView();
		mav.addObject(model);
		mav.setViewName("downloadFile");
		return mav;
	
	}	

	public void setDirPath(String dirPath) {
		this.dirPath = dirPath;
	}
	
}
