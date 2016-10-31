/**
 * 循环
 */
(function(){
	/**
		for(变量 = 开始数值;变量<=结束值;变量=变量+步长){
		}
	 */
	var arr = [1,2,3,45,6,5];
	for (var i = 0; i < arr.length; i++) {
		document.write(arr[i]+"<br>")
	}
	/**
		while(变量<=结束值){
		}
	 */
	var i = arr.length-1;
	while(i>=0){
		document.write(i+"-->"+arr[i]+"<br>");
		i--;
	}
	/**
	 * for(变量 in 对象){
	 * }
	 */
	var o = {name:"USPCAT",age:1};
	for(k in o){
		document.write(k+"-->"+o[k]+"<br>");
	}
})()
































