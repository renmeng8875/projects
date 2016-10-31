/**
 *  XHR工厂
 */
(function(){
	//Ajax操作接口
	var AjaxHandler = new Interface("AjaxHandler",["request","createXhrObject"]);
	//Ajax简单实现类
	var SimpleHandler = function(){};
	SimpleHandler.prototype = {
		/**
		 * method  get/post
		 * url 请求地址
		 * callback 回调函数
		 * postVars 传入参数
		 */
		request : function(method,url,callback,postVars){
			//1得到xhr 对象
			var xhr = this.createXhrObject();
			xhr.onreadystatechange = function(){
				//4代表的意思是交互完成
				if(xhr.readyState != 4)return;
				//200指的是正常交互完成
				//404文件未找到
				//500内部程序出现错误
				(xhr.status == 200)?callback.success(xhr.responseText,xhr.responseXML):
				callback.failure(xhr.status);
			}
			//打开链接
			xhr.open(method,url,true);
			//设置参数
			if(method != "POST"){
				postVars = null;
			}
			xhr.send(postVars);
			
		},
		createXhrObject:function(){
			var methods = [
				function(){return new XMLHttpRequest();},
				function(){return new ActiveObject('Msxml2.XMLHTTP');},
				function(){return new ActiveObject('Micrsoft.XMLHTTP');}
			]
			//利用try catch 制作一个只能循环体
			for (var i = 0; i < methods.length; i++) {
				try{
					methods[i]();
				}
				catch(e){
					continue;
				}
				//这句话非常的重要,有这样才能确保我不用每次请求全循环数组
				this.createXhrObject = methods[i];
				return methods[i]();
			}
			//如果全不对的话我就显示的报错
			throw new Error("error");
		}
	}
	//实验
	var myHandler = new SimpleHandler();
	var calback = {
		success:function(responseText){
			alert("OK")
		},
		failure:function(status){
			alert(status+"failure")
		}
	}
	myHandler.request("GET","",calback);
	myHandler.request("GET","",calback);
})()




