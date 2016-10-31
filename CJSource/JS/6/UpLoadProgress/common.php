<?php
function formatFileSize($filePath) {
	$size=filesize($filePath);
	if ($size > 0 && $size <1024) {
		return number_format($size)."B";
	} else if ($size >=1024 && $size < 1048576) {
		return number_format($size/1024,2)."KB";
	} else {
		return number_format($size/1024/1024,2)."MB";
	}
}
?>