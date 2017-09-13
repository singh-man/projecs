<%@page import="java.util.Set"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="java.io.File"%>
<%@page session="false" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>File download page</title>
<script type="text/javascript">
    function downloadFile(file) {
    	var r = confirm(file);
    	if(r == false)
    		return;
        window.document.download.getFile.value = file;
        alert('Downloading: ' + window.document.download.getFile.value);
        window.document.download.submit();
    }
    </script>
</head>
<body>
	<p>
		<a href="index.jsp">HOME</a>
	</p>
	<form name="download" action="download" method="post">
		<p>Click on the link to initiate Download</p>
		<%
		Map<String, Long> fileMap = (Map<String, Long>)request.getAttribute("files");
		Set<String> fileSet = fileMap.keySet();
			for (String s : fileSet) {
		%>
		<table>
			<tr>
				<td><a href="javascript:downloadFile('<%=s %>')"><%=s %></a></td>
				<td>
					<%out.println((fileMap.get(s)/1024 + " Kb")); %>
				</td>
			</tr>
		</table>
		<%
			}
		%>
		<input name="getFile" value="" type="hidden">
	</form>
</body>
</html>