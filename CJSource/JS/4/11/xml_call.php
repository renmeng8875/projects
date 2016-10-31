<?php

header('Content-Type:text/javascript; charset=UTF-8');
header('Cache-Control:no-cache,must-revalidate');
header('Pragma:no-cache');
/*
date_default_timezone_set('Asia/Shanghai');
$xml=new SimpleXMLElement("Motto.xml",NULL,TRUE);
list($motto)= $xml->xpath('/mottos/motto[@date="'.date('Y-m-d').'"]');
if (!$motto) {
	$motto=$xml->motto[rand(0,count($xml->motto)-1)];
}

$motto=trim($motto);
echo <<<JS
	document.title='PL4CJ.ORG - $motto';
JS;*/


echo <<<JS
	window.ShareData=12321;
JS;
?>