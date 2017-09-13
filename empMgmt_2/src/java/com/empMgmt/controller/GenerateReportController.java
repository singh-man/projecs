package com.empMgmt.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.empMgmt.dto.ReportDto;
import com.empMgmt.handler.ReportHandler;

/**
 * Servlet implementation class GenerateReportController
 */
@WebServlet("/generateReport")
public class GenerateReportController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		throw new RuntimeException("this Shouldn't have been called");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		
		ReportHandler reportHandler = new ReportHandler();
		SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
		Date sDate = null;
		Date eDate = null;
		
		try {
			sDate = sf.parse(startDate);
			eDate = "Today".equals(endDate) ? new Date() : sf.parse(endDate);
			Map<Date, List<ReportDto>> reportDateMap = reportHandler.generateReportArrangeByDate(sDate, eDate);

			if(reportDateMap == null || reportDateMap.size() == 0) {
				request.setAttribute("msg", "Nothing To Generate");
			}
			request.setAttribute("reportDateMap", reportDateMap);
			Date[] date = reportDateMap.keySet().toArray(new Date[reportDateMap.keySet().size()]);
			for(Date d : date) {
				
			}
			request.getRequestDispatcher("/report.jsp").forward(request, response);
			
		} catch (ParseException e) {
			request.setAttribute("msg", "Given Date format is wrong; Please follow dd/mm/yyyy");
			request.getRequestDispatcher("/admin").forward(request, response);
		}
	}

}
