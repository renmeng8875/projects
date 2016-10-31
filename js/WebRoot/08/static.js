/**
 * 普通的属性和函数是作用早对象上的
 * 而静态的函数是定义到类上面的
 */
(function(){
	function Person(name,age){
		this.name = name;
		this.showName = function(){
			alert(this.name)
		}
	}
	//第一种静态函数的写法
	Person.add = function(x,y){
		return x+y;
	}
	//alert(Person.add(10,20))
	//第二种方式
	//用类中类的方式完成没一个对象全拥有相同的属性和阐述
	var cat = (function(){
		//私有静态属性
		var AGE = 10;
		//私有函数
		function add(x,y){
			return x+y;
		}
		return function(){
			this.AGE = AGE;
			this.add = function(x,y){
				return add(x,y)
			}
		}
	})()
	alert(new cat().add(1,2)+"  "+new cat().AGE);
	alert(new cat().AGE);
	/**
	 * 1.保护内部数据完整性是封装一大用处
	 * 2.对象的重构变得很轻松,(如果没有封装你感动正在用这的代码吗?)
	 * 3.弱化模块直接的耦合
	 * 弊端
	 * 私有的方法他会变得很难进行单元测试
	 * 使用封装就会意味着与复杂的代码打交道
	 * 最大问题是封装在javascript中是很难实现的
	 */
})()


