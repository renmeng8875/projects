<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>注册 — 微博</title>
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
			<input type="password" id="pwd1" name="pwd1" placeholder="确认密码" />
		</p>
		<p>
			<input type="button" value="注册" onclick="register()" class="r_btn" />
		</p>
		<p style="text-align:right;padding-right:10px;">
			<a href="login.jsp">登录微博</a>
		</p>
	</section>
	
	<jsp:include page="/include/footer.jsp" />
	<script>
	
		$("#form").initValid({
			required : ['userName','pwd'],
			pwd_equals : [
			    {id:"pwd1",params:"pwd"}          
			]
		});
		
		function register(){
			if(!$("#form").valid())
				return;
			
			$.getJSON("register_do",{
				"userName":$("#userName").val(),
				"pwd":$("#pwd").val(),
				"pwd1":$("#pwd1").val()
			},function(data){
				switch(data.ret){
					case -1:
						tip.err("该用户名已被注册!");
						break;
					case -3:	
						tip.err("用户名和密码不能为空!");
						break;
					case -4:	
						tip.err("二次密码不一致!");
						break;	
					case 0:
						location.href="../index.jsp";		
						break;
					default:
						tip.err("注册失败,请稍后再试!");
						break;
				}
			});
		}
	</script>
</body>
</html>