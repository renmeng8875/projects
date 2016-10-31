/**
 * 模仿jquery的链式调用
 */
//为了类(Function)扩展函数,我们顶一个他的静态函数
Function.prototype.method = function(name,fn){
	this.prototype[name] = fn;
	return this;
};
(function(){
	//还记得吗他是私有变量的写法
	function _$(els){};	
	//准备方法
	_$.onready = function(obj,fn){
		if(obj){
			//按需求吧对象(_$)注册到window上
			obj.$ = function(){
				return new _$(arguments);
			}			
		}else{
			//按需求吧对象(_$)注册到window上
			window.$ = function(){
				return new _$(arguments);
			}
		}
		fn();
	}
	//链式的对象郑家jquery库提供的操作函数
	_$.method("addEvent",function(type,fn){
		fn();
	}).method("getEvent",function(fn,e){
		fn();
	}).method("addClass",function(className){
		fn();
	}).method("removeClass",function(className){
		fn();
	}).method("replaceClass",function(oldClass,newClass){
		fn();
	}).method("getStyle",function(el,fn){
		fn();
	}).method("setStyle",function(el,fn){
		fn();
	}).method("load",function(url,fn){
		fn();
	});
	//开始使用
	var com = {};
	_$.onready(com,function(){
//		$("div01").addEvent("click",function(){
//			alert("click Event");
//		})
		com.$("div01").addEvent("click",function(){
			alert("click Event");
			com.$(this).getEvent(function(){
				alert("click getEvent");
			})
		})		
	})
})()










