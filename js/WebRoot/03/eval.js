(function(){
	//eval 他是把一个字符串解析成一个方法并且调用
	var str = "var show = function(){alert(100)}()";
	//eval(str)
	//数据库会返回一个字符串(长得像javaScrpit数组)
	var a = "[1,2]";
	var array = eval(a);
	for (var i = 0; i < array.length; i++) {
		alert(array[i])
	}
})()