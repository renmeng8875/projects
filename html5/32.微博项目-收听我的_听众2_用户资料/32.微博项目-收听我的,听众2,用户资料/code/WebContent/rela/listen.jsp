<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>我收听的  — 微博</title>
<jsp:include page="/include/head.jsp" />
</head>
<body class="m_body">
	<jsp:include page="/include/m_header.jsp" />
	
	<div id="search_box">
		<input type="text" id="q" placeholder="搜索你想看的人" />
		<div id="btn_search" class="btn">搜索</div>
	</div>
	
	<div class="subTitle">
		我收听了总共<span id="spn_count">0</span>人
	</div>
	
	<div>
		<ul id="ulFans" class="ulFans">
		</ul>
	</div>
	
	<jsp:include page="/include/m_footer.jsp" />
	<jsp:include page="js.jsp" />
	<script type="text/javascript">	
		$.getJSON("listen_do",function(data){
			showList(data);
		});
	</script>
</body>
</html>