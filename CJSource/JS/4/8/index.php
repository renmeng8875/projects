<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>省,市,县 三级联动菜单XML版</title>
<script type="text/javascript" src="../../lib/base.js"></script>
<script type="text/javascript" src="area.js"></script>
<style type="text/css">
#citySpan,#townSpan,#waitPic {
display:none;
}
</style>
</head>
<body>
<form action="select.php" method="post">
	<fieldset>
		<legend>省,市,县 三级联动菜单XML版</legend>
		地址:
		<select id="province" name="province">
			<option selected="selected" value="">--请选择--</option>
			<?php
			$xml=new SimpleXMLElement("AreaZIP.xml",NULL,TRUE);
			$provinces=$xml->xpath("/root/a[@t=0]");
			foreach($provinces as $province) {
				$attrs=$province->attributes();
				echo "<option value=\"".$attrs["id"]."\">".$attrs["n"]."</option>";
			}
			?>
		</select> 省
		<span id="citySpan">
			<select id="city" name="city">
				<option selected="selected" value="">--请选择--</option>
			</select> 市
		</span>
		<span id="townSpan">
			<select id="town" name="town">
				<option selected="selected" value="">--请选择--</option>
			</select> 县
		</span>
		<img alt="正在请求....." id="waitPic" src="images/wait.gif" />
		<input type="submit" value="确定" />
	</fieldset>
</form>
</body>
</html>