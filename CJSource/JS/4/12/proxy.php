<?php

$cols=$_GET["cols"];
$start=$_GET["start"];
$table=$_GET["table"];
$pageSize=$_GET["pageSize"];
$order=$_GET["order"];

$sql="SELECT $cols FROM $table LIMIT $start,$pageSize";
if ($order) {
	$dir=strtoupper($_GET["dir"])=="DESC"?"DESC":"ASC";
	$sql.=" ORDER BY $order $dir";
}

$dsn="mysql:dbname=cms2;host=localhost";
$pdo=new PDO($dsn,"root","yangfan");
$pdo->query("SET NAMES utf8");
$std=$pdo->query("SELECT COUNT(cms_id) AS totalCount FROM $table");
$result=$std->fetchObject();
$totalCount=$result->totalCount;


$sth=$pdo->query($sql);

$result=$sth->fetchAll(PDO::FETCH_ASSOC);
$rows=json_encode($result);
echo <<<JSON
	{
		"totalCount":$totalCount,
		"rows":	$rows
	}

JSON;

?>