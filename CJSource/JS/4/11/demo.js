document.onclick=function () {
	var xhr=window.XMLHttpRequest?new XMLHttpRequest():new ActiveXObject("Microsoft.XMLDOM");
	xhr.open("get","proxy.php?url="+encodeURIComponent("http://localhost:8888/4/11/demo.php"),true);
	xhr.onreadystatechange=function () {
		if (xhr.readyState==4 && xhr.status==200) {
			alert(xhr.responseText);
		}
	};
	xhr.send();
	/*var rs=$("remoteScript");//在火狐下有问题
	rs.src="http://www.pl4cj.org/xml_call.php?param=value";*/
	/*var rs=$("remoteScript");
	var script=document.createElement("script");
	script.src="http://www.pl4cj.org/xml_call.php?param=value";
	rs.parentNode.replaceChild(script,rs);
	script.id="remoteScript";*/
	
	
};