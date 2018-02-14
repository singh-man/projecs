<%@page import="java.util.Arrays"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.Set"%>
<%@page import="com.empMgmt.dto.ReportDto"%>
<%@page import="com.empMgmt.dto.EmployeeDto"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<meta content="text/html; charset=ISO-8859-1" http-equiv="content-type">
<title>Report Page</title>
<script type="text/javascript">
	function homePage() {
		window.document.goToHome.submit();
	}
	function logout() {
		window.document.goToHome.button.value = 'Logout';
		homePage();
	}
</script>
</head>
<body>

	<form method="post" action="admin" name="goToHome">
		<a href="javascript:homePage()">Home</a><p align="right"><a href="javascript:logout()">Sign Out</a></p>
        <input name="button" value="" type="hidden">
    </form>
	
	<h2 align="center">Report</h2>
	
	<h4>
        <%
            String msg = (String) request.getAttribute("msg");
            if (msg != null)
                out.println(msg);
        %>
    </h4>
	<table style="text-align: left; width: 100%;" border="1"
		cellpadding="2" cellspacing="2">
		<tbody>
			<tr bordercolor="red">
				<td>Employee Name</td>
				<td>Employee Id</td>
				<td>In Time</td>
				<td>Out Time</td>
			</tr>
			<%
			Map<Date, List<ReportDto>> report = (Map<Date, List<ReportDto>>)request.getAttribute("reportDateMap");
			Set<Date> dateSet = report.keySet();
			Date[] date = dateSet.toArray(new Date[dateSet.size()]);
			Arrays.sort(date);
                for(Date d : date) {
                    List<ReportDto> reportList = report.get(d);
                %>
			<tr>
				<td><h4><%=d %></h4>
				</td>
			</tr>
			<% 
                for(ReportDto rep : reportList) {
                %>
			<tr>
				<td><%=rep.getName() %></td>
				<td><%=rep.getEid() %></td>
				<td><%=rep.getInTime() %></td>
				<td><%=rep.getOutTime() %></td>
			</tr>
			<%} }%>
		</tbody>
	</table>
</body>
</html>
