<?php
sleep(2);
$start=$_GET["start"];
$len=$_GET["len"];
$end=$start+$len-1;
$xml=new SimpleXMLElement("data.xml",NULL,TRUE);
$list=$xml->xpath("//motto[position()>=$start and position()<=$end]");
$data=array();
foreach ($list as $motto) {
	$a=$motto->xpath("@man");
	$data[]=array(
	"man"=>$a[0]."",
	"text"=>$motto.""
	);
}
echo json_encode($data);
?>