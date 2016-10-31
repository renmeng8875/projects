<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>

<!DOCTYPE html>
<html manifest="time_manifest.jsp">
<head>
<meta charset="UTF-8">
<title>new title</title>
</head>
<body>
	服务器时间：<%=new Date() %><br /><br />
	
	<div id="div"></div>
	<script src="app.js"></script>
</body>
</html>