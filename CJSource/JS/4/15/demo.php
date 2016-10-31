<?php
$js=file_get_contents($_GET["js"]);
require "class.JavaScriptPacker.php";
$packer=new JavaScriptPacker($js);
$jsc=$packer->pack();
header("Content-Type:text/javascript");
header("Content-Encoding:gzip");
echo gzencode($jsc);
?>