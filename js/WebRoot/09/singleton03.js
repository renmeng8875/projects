/**
 * 单例模式在JS中使用非常的频繁
 * 通过确保单例对象只存在一个实例,
 * 你就可以确信自己在所有的代码中使用的是全局资源
 */
(function(){
	//模拟一个Ajax操作
	function Ajax(){}
	Ajax.request = function(url,fn){
		if(true){
			fn("USPCAT.COM","EXTJS4");
		}
	}
	//我们同闭包的原理解决在01例子中出现的问题
	var UserInfo = (function(){
		var userInfo = "";//私有变量
		function init(){
			//利用闭包是单体有自己的私有局部变量
			var name = "";
			var code = "";
			//利用Ajax访问数据库来取得数据
			Ajax.request("WWW.USPCAT.COM",function(n,c){
				name = n;
				code = c;
			})
			return {
				name:name,
				code:code
			}
		}
		return {
			getInstance : function(){
				if(userInfo){
					return userInfo;
				}else{
					userInfo = init();
					return userInfo;	
				}
			}
		}
	})()
	alert(UserInfo.getInstance().name)
})()



