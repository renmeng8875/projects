<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
		<div class="title">修改个人资料</div>
		<div id="btnSave" class="btn">保存</div>
	</div>
	
	<form id="form" action="profile_update" method="post" enctype="multipart/form-data">
	<div class="modify">
		<p>
			<label>微博账户:</label>
			<span>${s_user.userName}</span>
		</p>
		<p>
			<label>头像:</label>
			<span>
				<c:if test="${s_user.logo != null}">
					<img src="<%=request.getContextPath() %>/upload/${s_user.logo}" width="50px" height="50px" />
				</c:if>
				<input type="file" name="logo" />
			</span>
		</p>
		<p>
			<label>昵称:</label>
			<span><input type="text" name="nickName" value="${s_user.nickName}" /></span>
		</p>
		<p>
			<label>性别:</label>
			<span>
				<input type="radio" name="sex" value="0" ${(s_user.sex==0?"checked":"")} />女&nbsp;&nbsp;
				<input type="radio" name="sex" value="1" ${(s_user.sex==1?"checked":"")} />男
			</span>
		</p>
		<p>
			<label>生日:</label>
			<span>
				<select name="birthYear">
					<c:forEach begin="1960" end="1996" var="i">
						<option value="${i}" ${(s_user.birthYear==i?"selected":"")}>${i}年</option>
					</c:forEach>
				</select>&nbsp;
				<select name="birthMonth">
					<c:forEach begin="1" end="12" var="i">
						<option value="${i}" ${(s_user.birthMonth==i?"selected":"")}>${i}月</option>
					</c:forEach>
				</select>&nbsp;
				<select name="birthDay">
					<c:forEach begin="1" end="31" var="i">
						<option value="${i}" ${(s_user.birthDay==i?"selected":"")}>${i}号</option>
					</c:forEach>
				</select>
			</span>
		</p>
		<p>
			<label>简介:</label>
			<span>
				<textarea name="intro" style="height:20px;">${s_user.intro}</textarea>
			</span>
		</p>
	</div>
	</form>
	<jsp:include page="/include/m_footer.jsp" />
	<script type="text/javascript">
		<c:if test="${ret != null}">
			<c:if test="${ret == 0}">
				tip.succ("资料修改成功!");
			</c:if>
			<c:if test="${ret == -1}">
				tip.err("资料修改失败!");
			</c:if>
		</c:if>
		
		$("#btnSave").bind("click",function(){
			document.getElementById("form").submit();
		});
	</script>
</body>
</html>