<%-- 
    Document   : index
    Created on : May 16, 2011, 10:52:29 AM
    Author     : emmhssh
--%>
<%@page import="com.bean.KVPair"%>
<%@page import="com.bean.MessageBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Select Option</title>
    </head>
    <body>
        <p>1
        <a href="download">Click here to download files -- From Server</a>
        </p>
        <p>2
        <a href="uploadFile.jsp">Click here to upload files -- To Server</a>
        </p>
        <p>3
        <a href="admin">Click here: If you are -- Admin</a>
        </p>
        <h3>
        <%
        String s = (String)request.getAttribute("msg");
        if(s != null)
        	out.println(s);
        %>
        </h3>
    </body>
</html>
