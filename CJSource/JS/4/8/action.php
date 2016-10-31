<?php
header("Content-Type:text/xml");
sleep(3);
echo "<?xml version='1.0' encoding='UTF-8'?><root>";
$aid=$_GET["aid"];
$xml=new SimpleXMLElement("AreaZIP.xml",NULL,TRUE);
$nodes=$xml->xpath("/root/a[@own=\"".$aid."\"]");
foreach ($nodes as $n) {
	$attrs=$n->attributes();
	echo '<a id="'.$attrs["id"].'" n="'.$attrs["n"].'" />';
} 
echo "</root>";
?>