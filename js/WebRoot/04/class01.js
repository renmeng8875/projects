(function(){
	//在javascript中我们利用function来定义类
	function Shape(){
		var x = 1;
		var y = 2
	}
	//然我们如何实例化一个对象呢? 通过new 关键字
	var aShape = new Shape();
	//在类的内部我们用var 定义的是私有变量 如何才能定义共有变量呢?
	function Shape2(){
		this.x = 1;
		this.y = 2;
	}
	var bShape = new Shape2();
	//测试
	//alert(bShape.x)
	//处理定义私有变量外还可以用var定义私有函数
	//private 函数
	function Shape3(){
		var draw = function(){
			//私有函数
		}
		this.draw2 = function(){
			//外界可以看到的共有函数
		}
	}
	var c = new Shape3();
	c.draw2();
	//用javascript模仿OOP编程
	function Shape4(ax,ay){
		var x = 0;
		var y = 0;
		var init = function(){
			x = ax;
			y = ay;
		}
		init();
		this.getX = function(){
			return x;
		}
	}
	var d = new Shape4(2,4);
	alert(d.getX());
	//模仿OOP编程的构造函数,下载我们来写静态属性和静态方法
	//JS中静态方法是作用到类身上的而非是对象
	function Person(){this.Name = "YUNFENGCHENG"};
	//静态变量
	Person.age = 0;
	Person.showName = function(obj){
		alert(obj.Name)
	}
	Person.showName(new Person())
//	Array.each= function(){
//	}
	//简单类建议方法
	var a = {};
	var array = [];
	a["name"] = "USPCAT.COM";
	alert(a.name)
})()






