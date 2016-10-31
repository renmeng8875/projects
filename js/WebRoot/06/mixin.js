/**
 * 掺元类
 * 有的适合不需要严格的继承,我们真正需要的是一个类(几个)中的的一些函数
 * 
 */
(function(){
	//我们准备将要被聚合的函数
	var JSON = {
		toJSONString :function(){
			var outPut = [];
			for(key in this){
				outPut.push(key+" --> "+this[key])
			}
			return outPut;
		}
	};
	/**
	 * 聚合函数
	 */
	function mixin(receivingClass,givingClass){
		for(methodName in givingClass){
			if(!receivingClass.__proto__[methodName]){
				receivingClass.__proto__[methodName] = givingClass[methodName]
			}
		}
	}
	var o = {name:"YUN",age:27}
	mixin(o,JSON);
	document.write(o.toJSONString().join(","))
//	JSON.prototype = {
//		toJSONString :function(){
//			var outPut = [];
//			for(key in this){
//				outPut.push(key+" --> "+this[key])
//			}
//			return outPut;
//		}
//	}
//	//制作聚合函数
//	function mixin(receivingClass,givingClass){
//		for(methodName in givingClass.prototype){
//			//本类中没有这个函数的情况下我在聚合,否则跳过
//			if(!receivingClass.prototype[methodName]){
//				receivingClass.prototype[methodName] = givingClass.prototype[methodName]
//			}
//		}
//	}
//	//var o = {name:"YUN",age:27}
//	var o = function(){
//		this.name = "YUN";
//		this.age = 17
//	}
//	mixin(o,JSON);
//	var a = new o();
//	document.write(a.toJSONString().join(","))
})()

