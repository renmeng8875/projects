<?php
$xml=new SimpleXMLElement('area.xml',NULL,TRUE);
$array=array();
foreach($xml->a as $area) {
	$attrs= $area->attributes();
	$arr=array(strval($attrs['n']));
	if (strval($attrs['t'])) {
		$arr[]=strval($attrs['t']);
		$arr[]=strval($attrs['o']);
	}
	$array[strval($attrs['id'])]=$arr;

}
var_dump($array["A1"]);
file_put_contents('address_db.js',json_encode($array));
?>