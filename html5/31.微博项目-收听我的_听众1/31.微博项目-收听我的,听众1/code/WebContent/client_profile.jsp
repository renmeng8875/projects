<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>个人资料修改 — 微博</title>
<jsp:include page="/include/head.jsp" />
</head>
<body class="m_body">
	<jsp:include page="/include/m_header.jsp" />
	
	<div class="navTitle">
		<div class="title">个人主页</div>
	</div>
	
	<div id="user_info">
		<div class="avatar">
			<img src="http://localhost:8080/h5_weibo/upload/7_1346576564479.jpg" />
		</div>
		<div class="user">
			nickName&nbsp;@userName<br />
			<div class="btn_cancal">取消收听</div>
		</div>
	</div>
	
	<div id="fans_info">
		<div>
			<span>听众</span><br />
			<span>111</span>
		</div>
		<div>
			<span>收听</span><br />
			<span>111</span>
		</div>
		<div>
			<span>广播</span><br />
			<span>0</span>
		</div>
	</div>
	
	<div id="user_detail_info">
		<span class="sex">男</span><span class="birth">1990年10月1号</span><br />
		<span class="intro">详细的描述内容</span>
	</div>
	
	<div class="subTitle">
		全部微博
	</div>
	
	
	<jsp:include page="/include/m_footer.jsp" />
</body>
</html>