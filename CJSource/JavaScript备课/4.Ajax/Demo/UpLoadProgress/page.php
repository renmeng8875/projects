<?include 'common.php'; ?><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>File UpLoad Progress</title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<style type="text/css">
* {
margin:0;
padding:0;
}
body {
	color:#444;
	padding:10px;
}
#progressDiv {
width:500px;
height:120px;
border:2px solid teal;
position:absolute;
left:30%;
top:30%;
z-index:12;
background:white;
display:none;
}
#progressDiv.moving {
	opacity:0.6;
	filter:alpha(opacity=60);
}
#progressDiv p {
width:94%;
margin:2px auto;
font-size:12px;
letter-spacing:1px;
text-align:center;
line-height:200%;
}
#progressDiv p span {
color:#F30;
}
#progressDiv h6 {
width:100%;
height:32px;
font-size:14px;
cursor:move;
background:teal;
color:white;
text-align:center;
line-height:32px;
}
#progressBar {
display:block;
width:94%;
height:26px;
margin:2px auto;
border:1px solid lime;
background:#EFE;
position:relative;
}
#progressBar img {
width:0%;
height:26px;
}
#progressBar #progressPercent {
display:block;
width:60px;
height:26px;
line-height:26px;
position:absolute;
left:50%;
top:0;
font-size:18px;
margin-left:-30px;
text-align:center;
color:#F30;
}
#closeDiv {
display:block;
width:60px;
height:24px;
line-height:24px;
font-size:13px;
color:white;
background:teal;
border:2px solid #EEE;
border-right-color:#999;
border-bottom-color:#999;
margin:4px 30px 0 auto;
}
</style>
</head>
<body>
<!--
target设成upLoadFrame以实现文件的上传
MAX_FILE_SIZE用来指定上传文件体积上限,浏览器不一定检查这个值,但PHP端会检查这个值
APC_UPLOAD_PROGRESS会使APC对上传进度自动进行监视,其值必须是一个唯一的值,一般用随机数
在PHP端要获取该文件的上传进度,只需要使用apc_fetch("upload_".APC_UPLOAD_PROGRESS表单域的值)即可 
 -->
	<form id="upFileForm" target="upLoadFrame" action="action.php" enctype="multipart/form-data" method="post">
		<input type="hidden" name="MAX_FILE_SIZE" value="10000000000000" />
		<input type="hidden" name="APC_UPLOAD_PROGRESS" value="<?php echo mt_rand(100000,1000000) ?>" />
		<input type="file" name="file" />
		<input type="submit" name="send" value="提交" />
	</form>
	<div id="progressDiv">
		<h6 id="titleBar">正在上传文件</h6>
		<p>
			文件总大小:<span id="totalBytes">0M</span>
			已上传大小:<span id="currentBytes">0M</span>
			上传速度:<span id="rate">0KB</span>/S
		</p>
		<span id="progressBar"><span id="progressPercent">0%</span><img id="progressPic" src="progress.jpg" alt="完成进度" /></span>
		<input type="button" id="closeDiv" value="关闭" />
	</div>
	<h4>已上传文件</h4>
	<table border="1" id="uploadedFile">
		<tr>
			<th>文件名</th>
			<th>文件大小</th>
			<th>上传日期</th>
		</tr>
<?php
//遍历文件夹,显示出所有已经上传的文件,上传一个文件后,自动给表格增一行显示新文件的数据
$dirPath = "File";
$uploadDir = dir($dirPath);
while ($fileName=$uploadDir->read()) {
	$filePath= $dirPath."/".$fileName;
	if (is_dir($filePath)) {continue;}
	$fileSize = formatFileSize($filePath);
	$fileDate = date("Y年m月d日  H:i:s",filemtime($filePath));
	echo <<<FILE
	<tr>
		<td><a href="$filePath" target="_blank">$fileName</a></td>
		<td>$fileSize</td>
		<td>$fileDate</td>
	</tr>
FILE;
}
$uploadDir->close();
?>
	</table>
<script type="text/javascript" src="progress.js"></script>
</body>
</html>