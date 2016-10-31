<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String type=request.getParameter("type");
	if(type.equals("home")){
%>
	<article>
		<p style="color:red;">这是首页</p>
		<img alt="" src="img/1.jpg" width="800" height="600">
	</article>	
<%
	}else if(type.equals("center")){
%>
	<article>
		<p style="color:blue;">这是个人中心</p>
		<img alt="" src="img/2.jpg" width="800" height="600">
	</article>
<%
	}
%>