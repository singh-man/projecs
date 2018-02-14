<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login Page</title>
<script type="text/javascript">
	function validateForm(isLogout) {
		var eid = document.login.eid.value;
		var pwd = document.login.password.value;
		if (eid == null || eid == "") {
			alert('Employee Id can not be left blank');
			return false;
		} if (pwd == null || pwd == "") {
			alert('Password can not be left blank');
			return false;
		} if(isLogout)
			document.login.button.value = 'Logout';
		else
			document.login.button.value = 'Login';
		window.document.login.submit();
	}
</script>
</head>
<body>

	<form method="post" action="login" name="login">
		<p align="right">
			<input type="button" name="logout" value="Logout" onclick="javascript:validateForm(true)" />
		</p>
		<p align="center">
			Employee Id: <input type="text" name="eid" value="" align="bottom">
		<p align="center">
			Password: <input name="password" type="password" /> 
		<p align="center">
			<input type="button" name="login" value="Login" onclick="javascript:validateForm(false)" />
		<h3 align="center">
			<%
				String msg = (String) request.getAttribute("msg");
				if (msg != null)
					out.println(msg);
				session.invalidate();
			%>
		</h3>
		<input name="button" value="" type="hidden">
	</form>

</body>
</html>