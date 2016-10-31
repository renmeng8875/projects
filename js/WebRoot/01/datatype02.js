/**
 * 数据类型和类型转换
 */
(function(){
	/**
	  * 2复合类型
	  * (1)数组-->有序的集合(array):下标(index) 是从0开始的	 
	 */
	//属性
	//constructor 返回对创建次对象的数组的函数引用
	//index 
	//input
	//*length
	//方法
//	*concat 合并数组
//	*join 把数组按照一定的各式进行串联
//	*push 数组的追加
//	*pop 删除数组返回的最后一个元素
	//sort toString shift 删除并且返回数组的第一个元素
	var arr = new Array();
	arr.push(1);
	arr.push(55);
	arr.push(5);
	arr.push(3);
	arr.push(9);
	//alert(arr.length)
	var arr2 = [1,2,3,45,6,7,8];
	//alert(arr2.join(":"));
	//alert(arr.concat(arr2).toString())
	for (var i = 0; i < arr2.length; i++) {
		document.write(arr2[i]+"<br>");
	}
	//扩展array的方法
	Array.each = function(array,fn){
		for (var i = 0; i < array.length; i++) {
			fn(array[i])
		}
	}
	Array.each(arr2,function(v){
		document.write(v+"<br>");
	})
})()






