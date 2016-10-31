/**
 * js的其他零散知识
 */
(function(){
	//1异常捕获
//	try{
//		
//	}catch(e){
//		
//	}
	try{
		//alert(2/0)	
	}catch(e){
		//throw new Error(e)
	}
})()
	/**
	 * 定时器
	 * setTimeout
	 * 未来某个时间执行一段代码
	 */
	function timedMsg(){
		//一秒钟以后出发的函数
		var t = setTimeout("alert('1 miao zhong ')",1000);
	}
	
	var c = 0;
	var t ;
	function timedCount(){
		document.getElementById("txt").value = c;
		c = c+1;
		t = setTimeout("timedCount()",1000)
	}
	
	function stop(){
		clearTimeout(t);
	}
	
	
	
	
	
	