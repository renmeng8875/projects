/**
 * 函数递归
 */
(function(){
	//常见的编程题 1~100 用递归算法完成累加
	function add(start,end){
		var num = 0;
		num = num + start;
		if(start < end){
			num = num + add(start+1,end);
		}
		return num;
	}
	
	alert(add(1,100));
})()







