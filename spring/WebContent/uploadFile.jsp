<%@page import="com.web.spring.fileUpDown.*"%>
<%@page import="java.util.List"%>
<%@page import="java.io.File"%>
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
	<form action="upload" enctype="multipart/form-data" method="POST">
		<input type="file" name="file1"> <br>
		<input type="Submit" value="Upload File"><br>
		<h3>
			<%
				DownUpBean downUpBean = (DownUpBean) request.getAttribute(KVPair.DOWN_UP_BEAN.getValue());
				if (downUpBean != null)
					out.println(downUpBean.getMessage());
			%>
		</h3>
	</form>
</body>
</html>