<?php
header("Content-Type:text/xml; charset=UTF-8");
$id = $_GET['id'];
if ($id) {
	sleep(3);
	echo "<?xml version='1.0' encoding='UTF-8'?><root>".getArea($id)."</root>";
}

function getArea($id="",$child=true) {
	$xmlDom = new DOMDocument();
	$xmlDom->load("AreaZIP.xml");
	if (!$id) {
		$xpath = "/root/a[@t='0']";//默认返回省
	} else if ($id && !$child) {//传入$id并将$child设为false时返回id=$id的节点的信息
		$node = $xmlDom->getElementById($id);
		return array(
		"name"=>$node->getAttribute("n"),
		"id"=>$id,
		"own"=>$node->getAttribute("own"),
		"type"=>$node->getAttribute("t")
		);
	} else {//否则,返回id=$id的区域的子区域
		$xpath = "/root/a[@own='$id']";
	}
	$xpathEval = new DOMXPath($xmlDom);
	$nodes= $xpathEval->query($xpath);
	$opts=array();
	foreach ($nodes as $one) {
		$opts[] = "<option value='".$one->getAttribute("id")."'>".$one->getAttribute("n")."</option>";
	}
	return join("\n",$opts);
}
?>