/**
 * 回调函数
 */
(function(){
	//接收回调函数的函数
	function add(x,y,fn){
		this.x = x||1;
		this.y = y||1;
		if(fn){
			fn(this.x+this.y);
		}
	}
	add(1,2,function(v){
		if(v>0){
			alert("re > 0")
		}else{
			alert("re <= 0")
		}
	})
})()