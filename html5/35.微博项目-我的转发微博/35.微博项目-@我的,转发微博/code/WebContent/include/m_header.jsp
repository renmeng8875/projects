<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<header class="m_header">
	<img src="<%=request.getContextPath() %>/images/logo_1.png" />
</header>
<nav>
	<a class="${param.type=='index'?'current':''}" href="<%=request.getContextPath()%>/index">主页</a>
	<a class="${param.type=='at'?'current':''}" href="<%=request.getContextPath()%>/at">@我的</a>
	<a class="${param.type=='fans'?'current':''}" href="<%=request.getContextPath()%>/rela/fans.jsp">听众</a>
	<a class="${param.type=='listen'?'current':''}" href="<%=request.getContextPath()%>/rela/listen.jsp">收听</a>
</nav>