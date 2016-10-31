/**
 * ExtJS 类引擎Manager
 */
(function(Class,alias,arraySlice){
	//定义Manager并且把他注册到Ext的命名空间中
	//manager他是个单例
	var Manager = Ext.ClassManager = {
		/**
		 * 所有的类将全部保存的在这
		 */
		classes : {},
		/**
		 * 定义一个类(class)
		 * className 类名字
		 * date 类数据
		 */
		create : function(className,data){
			//函数名字必须是字符串类型
			if(typeof className == "string"){
				//缓存中有救去缓存中区
				if(this.get(className)){
					return this.classes[className]; 
				}else{
					//把类的名字统一到类的数据中
					data.$className = className;
					var cal = new Class(data.$className,data);
					this.set(data.$className,cal);
					return cal;
				}
			}
		},
		get : function(name){
			var classes = this.classes;
			if(classes[name]){
				return classes[name];
			}else{
				return null;
			}
		},
		set : function(name,value){
			var self = this;
			self.classes[name] = value;
		},
		/**
		 * 实例化
		 */
		instantiate : function(){
			//定义类的名字是第一个传入的参数
			var name = arguments[0],
				//第一个返回数值的类型
				nameType = typeof name,
				//拿到第二个参数作为Data
				args = arraySlice.call(arguments,1)[0];
			//制定默认的别名
			alias = name;
			//要实例化的类
			var cls = null;
			if(nameType != 'function'){
				//如果你传递的第一个参数又不是function也不是字符串
				if(typeof name != 'string' || name.length <1){
					throw new Error("第一个参数必须符合命名规范"+
					"你必须是 一个字符串 或者 是function本体");
				}
				cls = this.get(name);
				//如果是null 那我就要自动加载
				if(!cls){
					var self = this;
					Ext.require(name);
					//再次获取
					cls = self.get(name);
				}
			}else{
				//如果你是第一个参数传递的是function
				//那么实例化的就是你
				cls = name;
			}
			if(cls){
				return this.getInstantiator()(cls,args);
			}else{
				throw new Error("不存在类导致类初始化失败....");
			}
		},
		/**
		 * 扩展辅助性函数
		 */
		getInstantiator:function(){
			//定义实例化准备对象(Function对象)
			var instantiator = null;
			//初始化Function
			instantiator = new Function("c","a","return new c(a)");
			return instantiator;
		}
	}
	
	//扩充Ext 本身的功能(Ext本身就是个实例)
	Ext.apply(Ext,{
		/**
		 * 类定义
		 */		
		define : function(className ,data){
			var mixin = data["mixin"];
			if(mixin){
				for(key in mixin){
					mixin[key] = Manager.instantiate(mixin[key]);
				}
			}
			//把data中extend转换成意识实例
			var extend = data["extend"];
			if(extend){
				Ext.require(extend)
				data["extend"] = Manager.get(extend);
			}
			return Manager.create.apply(Manager,arguments);
		},
		/**
		 * 为类创建实例
		 */
		create : alias(Manager,"instantiate") 
	})
})(Ext.Class,Ext.Function.alias,Array.prototype.slice)