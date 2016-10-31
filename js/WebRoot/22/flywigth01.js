/**
 * 享元模式是一个为了提高性能(空间复杂度)的设计模式
 * 他使用与程序会生产大量的相类似的对象是耗用大量的内存的问题
 */
(function(){
	//有一个城市要进行汽车的登记
	/**
	 * 制造商
	 * 型号
	 * 拥有者
	 * 车牌号码
	 * 最近一次登记日期
	 */
	var Car = function(make,model,year,owner,tag,renewDate){
		this.make = make;
		this.model = model;
		this.year = year;
		this.owner = owner;
		this.tag = tag;
		this.renewDate = renewDate;
		this.getMake = function(){
			return this.make;
		}
	}
	//装饰着 .. 计算函数的耗时
	var simpleProfiler2 = function(componet){
		this.componet = componet;
		this.action = function(methodName){
			var self = this;
			var method = componet[methodName];
			//如果是函数那就进行装饰
			if(typeof method == "function"){
				var startDate = new Date().getTime();
				method.apply(self.componet,arguments);
				var endDate = new Date();
				alert(endDate - startDate);				
			}
		}
	}	
	//北京车两 4150000 全部要登记
	var ca = new Array();
	function addCar(){
		this.begin = function(){
			for (var i = 0; i < 4150000; i++) {
				ca.push(new Car("东风","雪铁龙","2012-4-8",
					"云凤程","京pcat2145","2012-2-13"));
			}
		}
	}
	new simpleProfiler2(new addCar()).action("begin")
	/**
	 * extjs的开发下拉框,单选框
	 * 等小组件如果用享元模式 会非常的节省性能的开支
	 */	
})()



