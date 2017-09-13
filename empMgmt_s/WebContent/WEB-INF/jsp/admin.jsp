<%@page import="com.empMgmt.dto.EmployeeDto"%>
<%@page import="java.util.List"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<meta content="text/html; charset=ISO-8859-1" http-equiv="content-type">
<title>ADMIN PAGE</title>
<script type="text/javascript">
	function deleteEmployee(id) {
		var r = confirm('Going to delete Employee: ' + id + ' : Are you sure!');
		if(r==false)
			return;
	    window.document.deleteEmployee.eid.value = id;
	    window.document.deleteEmployee.action.value = 'delete';
		window.document.deleteEmployee.submit();
	}
	function logout() {
		window.document.logout.submit();
	}
	function addUpdateEmployee() {
        var name = window.document.handleEmployee.name.value;
        var eid = window.document.handleEmployee.eid.value;
        var pwd = window.document.handleEmployee.pwd.value;
        if(eid == null || eid == '') {
        	alert('Please provide Employee Id at least');
        	return false;
        }
        window.document.handleEmployee.submit();
	}
	function reportByDate() {
        var startDate = window.document.generateReport.startDate.value;
        var endDate = window.document.generateReport.endDate.value;
        if(startDate == null || startDate == '') {
        	alert('Start Date is missing')
        	return false;
        } if(endDate == null || endDate == '') {
        	window.document.generateReport.endDate.value = 'Today';
        }
        window.document.generateReport.submit();
    }
</script>
</head>
<body>

    <form method="post" action="logout" name="logout">
        <p align="right"><a href="javascript:logout()">Sign Out</a></p>
        <input name="button" value="Logout" type="hidden">
    </form>
    
	<form method="post" action="generateReport" name="generateReport">
		<h3>Generate Report</h3>
		<table style="text-align: left; width: 100%;" border="0" cellpadding="2" cellspacing="2">
			<tbody>
				<tr>
					<td>From Date (DD/MM/YYYY)</td>
					<td>Till Date (DD/MM/YYYY)</td>
				</tr>
				<tr>
                    <td><input name="startDate" value="" type="text"></td>
                    <td><input name="endDate" value="Today" type="text"></td>
                    <td><input name="button" value="Generate" type="button" onclick="javascript:reportByDate()"/></td>
                </tr>
			</tbody>
		</table>
	</form>
	
	<hr color="black" width="100%" size="3">
	
	<h3>
		<%
			String msg = (String) request.getAttribute("msg");
			if (msg != null)
				out.println(msg);
		%>
	</h3>
	<form method="post" action="handleEmployee/handle" name="handleEmployee">
		<br>
		<h3>Add/Update Employee Details</h3>
		<table style="text-align: left; width: 100%;" border="0"
			cellpadding="2" cellspacing="2">
			<tbody>
				<tr>
					<td>Enter Employee Name</td>
					<td>Employee id (un-modifiable)...</td>
					<td>Password</td>
					<td>Role</td>
				</tr>
				<tr>
					<td><input name="name" value="" type="text"></td>
					<td><input name="eid" value="" type="text"></td>
					<td><input name="pwd" value="" type="text"></td>
					<td><select name="role">
							<option>user</option>
							<option>admin</option>
					</select></td>
					<td><input name="button" value="Handle Employee" type="button" onclick="javascript:addUpdateEmployee()">
					</td>
				</tr>
			</tbody>
		</table>
		<input name="button" value="Handle Employee" type="hidden">
	</form>

	<form method="post" action="handleEmployee/delete" name="deleteEmployee">
		<h3>List of Employees</h3>
		<table style="text-align: left; width: 100%;" border="1"
			cellpadding="2" cellspacing="2">
			<tbody>
				<tr>
					<td><span style="font-weight: bold;">SNo.</span>
					</td>
					<td><span style="font-weight: bold;">Employee Name</span>
					</td>
					<td><span style="font-weight: bold;">Employee Id</span>
					</td>
					<td><span style="font-weight: bold;">Role</span>
					</td>
					<td><span style="font-weight: bold;">Joined On</span>
					</td>
					<td></td>
				</tr>

				<%
					List<EmployeeDto> emDtoList = (List<EmployeeDto>) request
							.getAttribute("employeeList");

					int i = 0;
					for (EmployeeDto emDto : emDtoList) {
				%>
				<tr>
					<td><%=(i + 1) + "."%></td>
					<td><%=emDto.getName()%></td>
					<td><%=emDto.getEid()%></td>
					<td><%=emDto.getRole()%></td>
					<td><%=emDto.getDateOfJoining()%></td>
					<td><% if(!emDto.getRole().equals("admin")) {
					%><a href="javascript:deleteEmployee('<%=emDto.getEid()%>')">Delete</a></td>
					<%} %>
				</tr>
				<%
					i++;
					}
				%>
			</tbody>
		</table>
		<br>
		<input name="eid" value="" type="hidden">
		<input name="action" value="" type="hidden">
	</form>
</body>
</html>
