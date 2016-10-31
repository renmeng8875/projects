<?php
header('Cache-Control: no-store, no-cache, must-revalidate');
//$apc_id =  $_SERVER['HTTP_AJAXREQUEST_UPLOAD_PROGRESS'];
$apc_id=$_GET['apc_id'];
if  ($apc_id) {
	$progress = apc_fetch("upload_".$apc_id);
	echo json_encode($progress);
}
?>