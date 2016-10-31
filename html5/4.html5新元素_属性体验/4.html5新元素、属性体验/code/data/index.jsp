<%@page import="java.util.Enumeration"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>显示请求参数</title>
</head>
<body>
	<%
		Enumeration e = request.getParameterNames();
		while(e.hasMoreElements()){
			String key = e.nextElement().toString();
			String value = request.getParameter(key);
			
			out.print(key + "=" + value + "<br />");
		}
	%>
</body>
</html>