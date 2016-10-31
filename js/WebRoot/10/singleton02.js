/**
 * 来个复杂点单体吧,他能解决更复杂的需求
 * 这个可以用到实战了
 * 但体中需要局部变量和局部方法
 */
(function(){
	//模拟一个Ajax
	function Ajax(){}
	Ajax.request = function(url,fn){
		if(true){//成功回调
			fn("USPCAT.COM","EXTJS4");
		}
	}	
	//这样在用以前的方式不实行了
	//我们通过闭包的原理可以解决他
	var UserInfo = (function(){
		//利用Ajax来完成操作
		var name = "";//局部变量
		var code = "";//局部变量
		//ajax
		Ajax.request("www.uspcat.com",function(n,c){
			name = n;
			code = c;
		})
		//这才是真正的单体对象
		return {
			name:name,
			code:code
		}
	})();
	//试验
	alert(UserInfo.name)
})()





