/**
 * 函数
 */
(function(){
//	function 函数名字(val1,val12,val3,valn){
//		//代码
//		//return
//	}
//	var fn = function(){
//		//代码
//	}
	//2中函数声明的区别
	//add(1,1);
	function add(x,y){
		alert(x+y)
	}
	//add(1,2);
	//add2(12,3)
	var add2 = function(x,y){
		alert(x+y)
	}
	add2(12,3)
})()