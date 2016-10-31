<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>用户信息查看 — 微博</title>
<jsp:include page="/include/head.jsp" />
</head>
<body class="m_body">
	<jsp:include page="/include/m_header.jsp" />
	
	<div class="navTitle">
		<div class="title">个人主页</div>
	</div>
	
	<div id="user_info">
		<div class="avatar">
			<img src="<%=request.getContextPath() %>/upload/${user.logo}" />
		</div>
		
		<div class="user">
			<div>${user.nickName}&nbsp;@${user.userName}</div>
			<c:if test="${rela <2 }">
				<div id="btn_l_${user.id}" class="btn_listen" onclick="listen(${user.id});">立即收听</div>
			</c:if>
			<c:if test="${rela >=2 }">
				<div id="btn_l_${user.id}" class="btn_cancal" onclick="cancelListen(${user.id});">取消收听</div>
			</c:if>
		</div>
	</div>
	
	<div id="fans_info">
		<div>
			<span>听众</span><br />
			<span>${fansCount}</span>
		</div>
		<div>
			<span>收听</span><br />
			<span>${listenCount}</span>
		</div>
		<div>
			<span>广播</span><br /> 
			<span>0</span>
		</div>
	</div>
	
	<div id="user_detail_info">
		<span class="sex">${user.sex==0?"女":"男"}</span>
		<span class="birth">
			<c:if test="${user.birthYear>0}">${user.birthYear}年</c:if>
			<c:if test="${user.birthMonth>0}">${user.birthMonth}月</c:if>
			<c:if test="${user.birthDay>0}">${user.birthDay}日</c:if>
		</span>
		<br />
		<span>${user.intro}</span>
	</div>
	
	<div class="subTitle" style="margin-top:10px;">
		全部微博
	</div>
	
	<jsp:include page="/include/m_footer.jsp" />
	<jsp:include page="/rela/js.jsp" />
</body>
</html>