/**
 * 模仿jquery的链式调用
 */
//为对象Function扩展一个函数-->目的是为对象链式的增加函数和属性
Function.prototype.method = function(name, fn) {
	this.prototype[name] = fn;
    return this;
}; 
(function(){
	//还记得吗这是私有函数的写法
	function _$(els){}
	//准备方法
	_$.onready = function(fn){
		//按需吧对象注册到window上
	  	window.$ = function(){
	    	return new _$(arguments);
	  	};			
		fn();
	} 	
	//链式的为对象增加jquery库提供的操作函数
  	_$.method('addEvent', function(type, fn) {
  		fn();
  	}).method('getEvent', function(fn,e) {
  		fn();
  	}).method('addClass', function(className) {
		fn();
 	}).method('removeClass', function(className) {
		fn();    
    }).method('replaceClass', function(oldClass, newClass) {
    	fn();
    }).method('hasClass', function(className) {
    	fn();
    }).method('getStyle', function(prop) {
    	fn();
    }).method('setStyle', function(prop, val) {
    	fn();
    }).method('load', function(uri, method) {
		fn();
	});
	//开始使用
	_$.onready(function(){
		$("div01").addEvent("click",function(){
			alert("click Event");
			//更有意思的用法,利用this关键字
			$(this).getEvent(function(){
				alert("click Event 2");
			})
		})
	})
})()



