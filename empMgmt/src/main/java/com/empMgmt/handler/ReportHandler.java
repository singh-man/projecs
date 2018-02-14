package com.empMgmt.handler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.empMgmt.dao.EmployeeDao;
import com.empMgmt.dto.ReportDto;

public class ReportHandler {

	private EmployeeDao employeeDao;
	//private ReportDto reportDto;
	
	public ReportHandler() {
		employeeDao = new EmployeeDao();
		//reportDto = new ReportDto();
	}
	
	public Map<Date, List<ReportDto>> generateReportArrangeByDate(Date sDate, Date eDate) {
		List<Object[]> objList = employeeDao.generateReport(sDate, eDate);
		if(objList != null) {
			
			ReportDto[] reportDto = new ReportDto[objList.size()];
			
			Map<Date, List<ReportDto>> dateReportMap = new HashMap<Date, List<ReportDto>>();
			
			
			SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
			for(int i = 0; i< objList.size(); i++) {
				Object[] o = objList.get(i);
				reportDto[i] = new ReportDto();
				reportDto[i].setName(o[0].toString());
				reportDto[i].setEid(o[1].toString());
				reportDto[i].setDate(sf.format(o[2]));
				reportDto[i].setInTime(o[3].toString());
				reportDto[i].setOutTime(o[4] ==null ? "" : o[4].toString());
				
				if(dateReportMap.containsKey(o[2])) {
					List<ReportDto> rep = dateReportMap.get(o[2]);
					rep.add(reportDto[i]);

				} else {
					List<ReportDto> reportList = new LinkedList<ReportDto>();
					reportList.add(reportDto[i]);
					dateReportMap.put((Date) o[2], reportList);
				}
			}
			return dateReportMap;
		}
		return null;
	}
}
