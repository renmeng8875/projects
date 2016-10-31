/**
 * 3.鸭式变形法
 * 这个方法来源于一个国外的老头他有一个名言(jim)
 * "像鸭子一样走路并且会嘎嘎叫的东西就是鸭子"
 * 换言之
 * 如果对象具有与接口定义的方法名字的同命所有方法 那么我就认为你就是实现本接口
 */
(function(){
	//定义一个接口类
	var Interface = function(name,methods){
		if(arguments.length != 2){
			alert("interface must have two paramters...");
		}
		this.name = name;//这个是接口的名字
		this.methods = [];//定义个空数组来转载函数名
		for (var i = 0; i < methods.length; i++) {
			if(typeof methods[i] != "string"){
				alert("method name must is String ...")
			}else{
				this.methods.push(methods[i])
			}
		}
	}
	//定义接口的一个静态方法来实现接口与实现类的直接检验
	//静态方法不要写成Interface.prototype.* 因为这是写到接口原型连上的
	//我们要把静态的函数直接写到类层次上
	Interface.ensureImplements = function(object){
		if(arguments.length<2){
			alert("必须最少是2个参数");
			return false;
		}
		//遍历
		for (var i = 1; i < arguments.length; i++) {
			var inter = arguments[i];
			//如果你是接口就必须是Interface类型的
			if(inter.constructor != Interface){
				throw new Error("if is interface class must is Interface type");
			}
			//遍历函数集合并分析
			for (var j = 0; j < inter.methods.length; j++) {
				var method = inter.methods[j];
				//实现类中必须有方法名字 和 接口中所有的方法名项目
				if(!object[method] || typeof object[method] != "function"){
					throw new Error("实现类并且没有完全实现接口中的所有方法...");
				}
			}
		}
	}
	//应用
	//定义自己的接口
	var GridMananger = new Interface("GridMananger",["add","remove","list"]);
	var FormMananger = new Interface("FormMananger",["save"])
	
	function commManager(){
		//先实现方法
		this.add = function(){
			alert("ok")
		}
		this.remove = function(){}
		this.list = function(){}
		this.save = function(){}
		//检验
		Interface.ensureImplements(this,GridMananger,FormMananger)
	}
	var c = new commManager();
	c.add();
	/**
	 * 接口的重要性
	 * 1.大型项目提高代码的灵活度
	 * 2.松耦合
	 * 3.在团队开发的时候,有写时候你在真正编码之前就可以写API(自己的类库)
	 * 那这类库就可以时候在进行实现
	 * 开始的时候我们就可以对整个项目是否可行,通过接口就可模拟出来
	 */
	
})()



