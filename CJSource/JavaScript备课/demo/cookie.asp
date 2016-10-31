<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Cookie</title>
<meta http-equiv="Content-Type" content="text/html; charset=gbk" />

<script type="text/javascript">
	<%
	'response.cookies("userName")="abc"
	'response.cookies("userName").expires = #2010-1-1#
	'response.cookies("userPassWord")="pwd"
	%>
	//document.cookie = "qwer"
document.write(document.cookie);
	function getCookie(cookieName) {
		var re = new RegExp("\\b"+cookieName+"=([^;]*)\\b");
		var arr =  re.exec(document.cookie);
		return arr?decodeURI(arr[1]):"";
	}
	function setCookie(name,value,expires,domain,secure) {
		var str = name+"="+encodeURI(value);//不要忘了在对应getCookie函数里面加上decodeURI方法
		if (expires) {
			str += "; expires="+expires.toGMTString();
		}
		if (domain) {
			str += "; domain="+domain;
		}
		if (secure) {
			str += "; secure";
		}
		document.cookie = str;
	}
	function delCookie(cookieName) {
		var expires = new Date();
		expires.setTime(expires.getTime()-1);//将expires设为一个过去的日期，浏览器会自动删除它
		document.cookie = "cookieName=; expires="+expires.toGMTString();
	}
</script>
<style type="text/css">
* {
	font-size:12px;
	margin:0;
	padding:0;
}
html {
}
body {
	background:white;
}
div {
	width:100px;
	height:100px;
	background:blue;
	color:red;
	position:absolute;
	left:100px;
	top:100px;
}
#div2 {
background:yellow;
}
a {
display:block;
width:200px;
height:20px;
background:pink;
}
a:hover {
background:red;
}
</style>
</head>
<body>
	<div id="oDiv">A</div>
	<div id="div2">B</div>
	<a href="###">AAA</a>
</body>
</html>