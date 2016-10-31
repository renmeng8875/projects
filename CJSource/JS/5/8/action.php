<?php
sleep(2);
$man=$_GET["man"];
$xml=new SimpleXMLElement("data.xml",NULL,TRUE);
$nodes=$xml->xpath("//motto[@man='$man']");
foreach ($nodes as $n) {
	echo $n."<br />";
}
?>