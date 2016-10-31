<?php
/*


{
	suffix:{
		msg:"未被注册",
		code:1
	}
}
code 
1 未注册
2 已注册
3 超时
*/
header("Content-Type:text/plain");
sleep(2);
$domain=$_GET['domain'];
$suffix=$_GET['suffix'];
$data =array();
foreach ($suffix as $k) {
	$data[$k]=ge_msg();
}
echo json_encode($data);

function ge_msg() {
	$unreg=array(
		"msg"=>"未注册",
		"code"=>1
	);
	$reg=array(
		"msg"=>"已注册",
		"code"=>2
	);
	$timeout=array(
		"msg"=>"查询超时",
		"code"=>3
	);
	$ary=array($unreg,$reg,$timeout);
	return $ary[rand(0,2)];
}
?>

