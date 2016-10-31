<?php
$src = 'base.js';
require 'class.JavaScriptPacker.php';
$script = file_get_contents($src);
$packer = new JavaScriptPacker($script);
header("Content-Type:text/javascript");
echo $packer->pack();

?>