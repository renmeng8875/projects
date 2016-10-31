<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html manifest="manifest.jsp">
<head>
<meta charset="UTF-8">
<title>离线应用</title>
<link rel="stylesheet" type="text/css" href="res/style.css" >
</head>
<body>
	<img alt="" src="res/demo.png">
	
	<div class="server">
		 服务器时间：<%=new Date() %><br /><br />
	</div>
	
	<div id="div" class="client"></div>
	
	<div id="ajax" class="ajax">
	</div>
	<button onclick="getTime();">获取服务器最新的时间</button>
	<script src="res/app_1.js"></script>
	<script src="res/jquery-1.7.2.min.js"></script>
	<script>
		function getTime(){
			$.get("time.jsp",function(data){
				document.getElementById("ajax").innerHTML = "最新的服务器时间:" + data;
			});
		}
	</script>
</body>
</html>