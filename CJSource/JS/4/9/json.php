<?php
$a=array(
"name"=>"成吉",
"age"=>range(0,20)
);
/*
$json=array();
echo "{";
foreach ($a as $key=>$value) {
	array_push($json,$key.":".'"'.$value.'"');
}
echo join($json,",");
echo "}";
*/
echo json_encode($a);
?>