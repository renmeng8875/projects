/**
 * 真正的单体需要惰性加载
 * 庞大的系统会在会有很多单例这样系统一上来就全部加载的方式很不是好,我们要利用惰性单体来改造他
 */
(function(){
	//模拟一个Ajax
	function Ajax(){}
	Ajax.request = function(url,fn){
		if(true){//成功回调
			fn("USPCAT.COM","EXTJS4");
		}
	}	
	//仿照java来完成
	var UserInfo = (function(){
		var userInfo = "";//私有变量
		function init(){
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
		}
		return {
			getInstance:function(){
				if(userInfo){
					return userInfo;
				}else{
					userInfo = init();
					return userInfo;
				}
			}
		}
	})();
	//试验
	alert(UserInfo.getInstance().name)
})()





