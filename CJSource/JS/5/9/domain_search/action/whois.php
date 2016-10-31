<?php
ob_start(create_function('$buffer','return gzencode($buffer,9);'));
header("Content-Encoding:gzip");
header("Content-Type:text/plain; charset=UTF-8");

$domain=$_GET['domain'];
$suffix=$_GET['suffix'];
whois($domain,$suffix);

function whois($domain,$suffix) {
	$jsonReport=create_function('$arg1=null,$arg2=null','
		exit(json_encode(array(
		"error"=>$arg1,
		"code"=>$arg2
		)));
	');
	if (preg_match('/[^a-z0-9-]/i',$domain) || preg_match('/^-|-$/',$domain) || strlen($domain)<1 || strlen($domain)>63) {
		$jsonReport('不合法的域名！域名只能包含数字、字母及减号，且不能以减号开头或结尾！长度允许范围为1-63位！',0);
	}

	if (!preg_match('/^(\.[a-z]+)+$/i',$suffix)) {
		$jsonReport('不合法的域名后缀！域名后缀只能是字母！',1);
	} else {
		$whois="http://www.whois-search.com/whois/www.$domain$suffix";
		$f=file_get_contents($whois);
		if ($f===false) {
			$jsonReport('查询超时!',2);
		}
		preg_match('/<pre>([\s\S]*)<\/pre>/i',$f,$matches);
		$result=trim($matches[1]);
		if (strcasecmp($result,'NOT FOUND')===0) {
			$jsonReport('没有找到!',3);
		}
		if (strcasecmp($result,'no matching record')===0) {
			$jsonReport('暂无记录!',4);
		}
		$re ='/Domain\s*Name\:\s*$domain'.str_replace('.','\.',$suffix).'/i';
		if (!preg_match($re,$f)) {
			$jsonReport('未知信息!',5);
		} else {
			$jsonReport('',6);
		}
	}
}


ob_end_flush();
?>