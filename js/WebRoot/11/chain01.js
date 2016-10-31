/**
 * 从一个实例引出链式调用的需求
 */
(function(){
	//穿件一个cat类
	function Cat(name){
		this.name = name;
		this.run = function(){
			alert(name +"start run");
		}
		this.stopRun = function(){
			alert(name +"stop run");
		}
		this.sing = function(){
			alert(name +"start sing");
		}
		this.stopSing = function(){
			alert(name +"stop sing");
		}		
	}
	//测试
	var c = new Cat("USPCAT");
	c.run();
	c.sing();
	c.stopSing();
	c.stopRun();
	//代码很多很麻烦不方便
	//看看链式调用如何解决这个问题
})()