<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>AreaSelect</title>
</head>
<body>
<form action="select.php" method="post">
	<fieldset>
		<legend>省,市,县 三级联动菜单</legend>
		地址:
		<select id="province" name="province">
			<option selected="selected" value="">--请选择--</option>
			<?php
				include 'select.php';
				echo getArea();
			?>
		</select> 省
		<input type="text" id="city" name="city" /> 市
		<input type="text" id="town" name="town" /> 县
		<img alt="正在请求....." id="waitPic" src="wait.gif" />
		<input type="submit" value="确定" />
	</fieldset>
</form>
<script type="text/javascript" src="select.js"></script>
</body>
</html>