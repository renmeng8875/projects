<?php
ob_start();
header('Pragma: no-cache') ;
header('Cache-Control: no-store, no-cache, must-revalidate');
$apc_id =  $_SERVER['HTTP_AJAXREQUEST_UPLOAD_PROGRESS'];
if  ($apc_id) {
	$progress = apc_fetch("upload_".$apc_id);
	echo json_encode($progress);
} else {
	if ($_FILES['file']['error']===0) {
		$fileName= mt_rand(1,100000000).basename($_FILES['file']['name']);
		$filePath='File/'.$fileName;
		if (move_uploaded_file($_FILES['file']['tmp_name'],$filePath)) {
			$fileDate=date("YÄêmÔÂdÈÕ H:i:s",filemtime($filePath));
			header("Content-Type:text/html; charset=GBK");
			include 'common.php';
			$fileSize=formatFileSize($filePath);
			echo <<<JS
			<script type="text/javascript">
			var table = parent.document.getElementById("uploadedFile");
			var tr = table.insertRow(table.rows.length);
			tr.insertCell(0).innerHTML = "<a href='$filePath' target='_blank'>$fileName</a>";
			tr.insertCell(1).innerHTML = "$fileSize";
			tr.insertCell(2).innerHTML = "$fileDate";
			parent.document.getElementById("progressDiv").style.display="none";
			parent.document.getElementById("upFileForm").send.disabled=false;
			setTimeout(function () {
			location.replace("about:blank");
			},1000);
			</script>
JS;
		}
		
	}
}

?>