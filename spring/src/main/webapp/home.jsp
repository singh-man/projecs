<%-- 
    Document   : index
    Created on : May 16, 2011, 10:52:29 AM
    Author     : emmhssh
--%>
<%@page import="com.web.spring.fileUpDown.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <p>1
        <a href="fileUpDown/download">Click here to download files -- From Server</a>
        </p>
        <p>2
        <a href="uploadFile.jsp">Click here to upload files -- To Server</a>
        </p>
        <p>3
        <a href="fileUpDown/admin">Click here: If you are -- Admin</a>
        </p>
        <h3>
			<%
				DownUpBean downUpBean = (DownUpBean) request.getAttribute(KVPair.DOWN_UP_BEAN.getValue());
				if (downUpBean != null)
					out.println(downUpBean.getMessage());
			%>
		</h3>
    </body>
</html>
