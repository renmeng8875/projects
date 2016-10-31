<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Ajax文件上传进度条</title>
<script type="text/javascript" src="../../lib/jquery-1.4.2.js"></script>
<script type="text/javascript" src="demo.js"></script>

</head>
<body>
<h1>Ajax文件上传进度条</h1>
<form action="upload.php" id="fm1" target="uploadFrame" method="post" enctype="multipart/form-data">
	<input type="file" name="fileUpload" />
	<input type="submit" value="上传" />
	<input type="hidden" name="MAX_FILE_SIZE" value="100000000000000" />
	<input type="hidden" name="APC_UPLOAD_PROGRESS" value="<?php echo mt_rand(100000,1000000) ?>" />
</form>



<iframe id="uploadFrame" name="uploadFrame"></iframe>
<p>当前上传进度：<strong id="progressContainer">0%</strong></p>
</body>
</html>

