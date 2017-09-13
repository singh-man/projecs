<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>File upload page</title>
</head>
<body>
	<p>
		<a href="index.jsp">HOME</a>
	</p>
	<h5>Don't leave the page while Upload is in progress</h5>
	<form action="upload" enctype="multipart/form-data" method="POST">
		<input type="file" name="file1"> <br>
		<input type="Submit" value="Upload File"><br>
		<h3>
			<%
				String msg = (String) request.getAttribute("msg");
				if (msg != null)
					out.println(msg);
			%>
		</h3>
	</form>
</body>
</html>