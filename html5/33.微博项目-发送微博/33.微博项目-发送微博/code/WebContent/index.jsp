<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>个人资料修改 — 微博</title>
<jsp:include page="/include/head.jsp" />
</head>
<body class="m_body">
	<jsp:include page="/include/m_header.jsp">
		<jsp:param value="index" name="type"/>
	</jsp:include>
	
	<div id="home_title">
		<img class="avatar" src="<%=request.getContextPath()%>/upload/${s_user.logo}" />
		<div class="input" onclick="location.href='wb/writer.jsp';">
			<img src="images/w_1.png" height="19px" width="19px" />&nbsp;
			<img src="images/w_2.png" height="19px" width="178px"/>
		</div>
	</div>
</body>
</html>