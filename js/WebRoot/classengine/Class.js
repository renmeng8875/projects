/**
 * 类模板
 */
(function(){
	//定义一些相关的参数
	var ExtClass ,//本类
		Base = Ext.Base //基础类
	function makeCtor(className){
		/**
		 * apply方法能持有另外一个对象的方法集成另外一个对象的属性
		 * Funtion.apply(obj,args)
		 * obj 这个对象将要替代的Function类里面的this对象
		 * args 这个他是个数组 他讲作为参数传递进去
		 */
		function constructor(){
			/**
			 * 构造本类的构造函数,使其接受新的参数数组
			 */
			return this.constructor.apply(this,arguments)
		}
		if(className){
			constructor.displayName = className;
		}
		return constructor;
	}
	//模型类
	ExtClass = function(){
		/**
		 * 类创建的函数
		 * classname 类名字
		 * data 构建类用到的数据
		 */
		this.create = function(className,data){
			//定义一个类
			Class = makeCtor(className,data);
			//1.先来判断是否用继承装饰者
			if(data["extend"]){
				this.extendDecorator(Class,data["extend"]);
			}
			/**
			 * 原型链方法实现方法和书写的继承(类层次是虚拟继承)
			 * 为什么?
			 * 本类是需要集成子其他类的
			 * 那么对BASE来讲就不用真集成了 用简易继承即可
			 */
			Class.prototype.constructor = Base.prototype.constructor;
			//2.循环赋值
			for(key in data){
				Class.prototype[key] = data[key]
			}
			//3.判断是否传入config特性的对象
			if(data["config"]){
				this.configDecorator(Class,data["config"]);
			}
			//4.静态装载
			if(data["statics"]){
				this.staticsDecorator(Class,data["statics"]);
			}
			//5.聚合
			if(data["mixin"]){
				this.mixinDecorator(Class,data["mixin"])
				//删除原型链上的属性
				delete Class.prototype["mixin"];
			}
			return Class;
		}
		this.mixinDecorator = function(Class,mixin){
			//目标类
			var receivingClass = Class;
			for(givingClassName in mixin){
				var givingClass = mixin[givingClassName];
				for(methodName in givingClass){
					if(!receivingClass.prototype[methodName]){
						if(givingClass[methodName] instanceof Function){
							receivingClass.prototype[methodName] = 
							givingClass[methodName]
						}
					}
				}
			}
		}
		this.staticsDecorator = function(Class,statics){
			if(typeof statics === "object"){
				for(key in statics){
					if(typeof statics[key] == "function"){
						Class[key] = statics[key];
					}
				}
			}
		}
		this.configDecorator = function(Class,config){
			if(typeof config === "object"){
				for(key in config){
					Class.prototype["get"+Ext.FiresttoUpperCase(key)] = function(){
						return this.config[key]
					}
					Class.prototype["set"+Ext.FiresttoUpperCase[key]] = function(value){
						this.config[key] = value;
					}
				}
			}
		}
		this.extendDecorator = function(Class,superClass){
			if(typeof superClass != "string"){
				Ext.extend(Class,superClass);
			}
		}
	}
	//创建Ext的Class
	Ext.Class = function(Class,data){
		Class = new ExtClass().create(Class,data);
		return Class;
	}
})()
