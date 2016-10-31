<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>登录 — 微博</title>
<jsp:include page="/include/head.jsp" />
</head>
<body class="r_body">
	<jsp:include page="/include/r_header.jsp" />
	
	<section id="form" class="r_container">
		<p>
			<input type="text" id="userName" name="userName" placeholder="输入用户名" />
		</p>
		<p>
			<input type="password" id="pwd" name="pwd" placeholder="输入密码" />
		</p>
		<p>
			<input type="button" value="登录" onclick="login()" class="r_btn" />
		</p>
		<p style="text-align:right;padding-right:10px;">
			<a href="register.jsp">注册微博</a>
		</p>
	</section>
	
	<jsp:include page="/include/footer.jsp" />
	<script>
	
		$("#form").initValid({
			required : ['userName','pwd']
		});
		
		function login(){
			if(!$("#form").valid())
				return;
			
			$.getJSON("login_do",{
				"userName":$("#userName").val(),
				"pwd":$("#pwd").val()
			},function(data){
				switch(data.ret){
					case -1:
						tip.err("用户名或密码错误!");
						break;
					case 0:
						// 登录成功
						location.href="../index";		
						break;
					default:
						tip.err("登录失败,请稍后再试!");
						break;
				}
			});
		}
	</script>
</body>
</html>