<?php
sleep(2);
$xml=new SimpleXMLElement("../data.xml",NULL,TRUE);
$nodes=$xml->xpath("//motto[@man='{$_GET['man']}']");
$data=array();
foreach ($nodes as $key=>$v) {
	$data[]=$v."";
}
echo json_encode($data);
?>