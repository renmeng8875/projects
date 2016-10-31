<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Ajax分页</title>
<link type="text/css" rel="stylesheet" href="base.css"/>
<script type="text/javascript" src="../../lib/base.js"></script>
<script type="text/javascript" src="AjaxPager.js"></script>
</head>
<body>
<h1>Ajax分页</h1>
<div id="content">
	<ul id="newsList">
		<li><em>200-09-10</em><a href="#">韩拒绝交人质，美方陷入尴尬境地</a></li>
		<li><em>200-09-10</em><a href="#">韩拒绝交人质，美方陷入尴尬境地</a></li>
	</ul>
	<p>
		<a href="#prev" id="prevPage">上一页</a> |
		<a href="#next" id="nextPage">下一页</a>
		第<input type="text" id="currentPage" value="1" />页
		每页显示<input type="text" id="pageSize" value="5" />条信息
		总共<span id="totalCount">100</span>条信息
	</p>
</div>
</body>
</html>