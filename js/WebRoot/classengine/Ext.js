/**
 * 定义Ext的命名空间
 */
var Ext = Ext || {};
(function(){
	Ext.apply = function(object,config,defaults){
		//有默认的先用默认数值进行填充
		if(defaults){
			Ext.apply(object,defaults);
		}
		//判断目标对象是true并且他是object类型
		if(object && config && typeof config == "object"){
			var i,j,k;
			//添加扩展属性
			for(i in config){
				object[i] = config[i];
			}
		}
		//返回新的对象
		return object;
	}
	/**
	 * 动态执行js脚本添加
	 */
	Ext.require = function(expressions,fn){
		if(typeof expressions == "object"){
			yepnope({
				load:expressions,
				complete :fn
			})
		}else if(typeof expressions == "string"){
			HermesJS.require(expressions,{
				async:false,
				onLoadQueue:fn
			})
		}
	}
	/**
	 * 继承
	 */
	Ext.extend = function(subClass,superClass){
		//1.叫他的原型类属性等于父类的原型类属性
		var F = function() {};
		//创建中间过度函数
		F.prototype = superClass.prototype;
		//2.让子类继承F
		subClass.prototype = new F();
		subClass.prototype.constructor = subClass;
		//3.为子类增加属性superclass 叫他别赋予原型链的引用
		subClass.superclass = superClass.prototype;
		//4.加保险 就算你的原型类是超类Object那么也要把你的构造器降下来
		if(superClass.__proto__.constructor == Object.prototype.constructor) {
			superClass.__proto__.constructor = superClass;
		}	
	}
	
	
	
})(yepnope,HermesJS)