<?php

ob_start(create_function('$buffer','return gzencode($buffer,9);'));
header("Content-Encoding:gzip");
header("Content-Type:text/plain; charset=UTF-8");
header("Pragma:no-cache");
header("Expires:Mon,26 Jul 1997 05:00:00 GMT");
header("Cache-Control:no-store,no-cache,must-revalidate");


$domain=$_GET['domain'];
$suffix=$_GET['suffix'];
whois_search($domain,$suffix);
function whois_search($domain,$suffix) {
	$report=create_function('$arg1=null,$arg2=null','
		exit(json_encode(array(
		"error"=>$arg1,
		"code"=>$arg2
		)));
	');
	sleep(1);
	$report("",-1);
	if (preg_match('/[^a-z0-9-]/i',$domain) || preg_match('/^-|-$/',$domain) || strlen($domain)<1 || strlen($domain)>63) {
		$report('不合法的域名！域名只能包含数字、字母及减号，且不能以减号开头或结尾！长度允许范围为1-63位！',false);
	}

	if (!preg_match('/^(\.[a-z]+)+$/i',$suffix)) {
		$report('不合法的域名后缀！域名后缀只能是字母！',false);
	} else {
		$whois="http://www.whois-search.com/whois/www.$domain$suffix";
		

		
		$f=file_get_contents($whois);
		
		
		
		
		if ($f===false) {
			sleep(3);
			$report('查询超时',-1);
		}
		preg_match('/<pre>([\s\S]*)<\/pre>/i',$f,$matches);
		$result=trim($matches[1]);
		if ( strcasecmp($result,'the domain you want to register is reserved')===0 || (stripos($f,'Domain Name')!==false && stripos($f,'Name Server')!==false && stripos($f,$domain.$suffix)!==false)) {
			$report('已被注册',0);
		}
		if (!$result || stripos($f,'can now be registered')!==false || stripos($f,'NOT FOUND')!==false || stripos($f,'no match')!==false) {
			$report('未被注册',1);
		}
		$report('未知结果',-1);
	}
}



ob_end_flush();
?>