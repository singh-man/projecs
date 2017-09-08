<%@page import="java.util.List"%>
<%@page import="java.io.File"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>File download page</title>
</head>
<body>
	<p>
      <a href="index.jsp">HOME</a>
    </p>
	<form action="download" method="post">
		<p>Click on the button to Download</p>
		<%
			List<String> fileList = (List<String>) request.getAttribute("files");
			for (String s : fileList) {
		%>
		<table>
			<tr>
			<input type="submit" name="getFile" value="<%=s %>" />
			</tr>
		</table>
		<%
			}
		%>

	</form>

</body>
</html>