<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>发送微博 — 微博</title>
<jsp:include page="/include/head.jsp" />
</head>
<body class="m_body">
	<jsp:include page="/include/m_header.jsp" />
	
	<div class="navTitle">
		<div class="btn_back" onclick="returnBack();">返回</div>
		<div class="title">发微博</div>
		<div id="btnSave" onclick="addWb();" class="btn">保存</div>
	</div>
	
	<form id="frm" action="writer" method="post" enctype="multipart/form-data">
	<div style="padding:5px;">
		<textarea id="content" name="content" style="width:100%;height:100px;"></textarea>
		上传图片:<input type="file" name="img" />
	</div>
	</form>
	<jsp:include page="/include/m_footer.jsp" />
	<script>
		function returnBack(){
			location.href="../index.jsp";
		}
		function addWb(){
			var c = $("#content").val().trim();
			if(c.length == 0){
				alert("微博内容不能为空!");
				return;
			}
			if(c.length > 140){
				alert("微博内容不能超过140个字!");
				return;
			}
			
			document.getElementById("frm").submit();
		}
	</script>
</body>
</html>