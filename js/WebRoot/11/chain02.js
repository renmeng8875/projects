/**
 * 实现简单的链式调用
 */
(function(){
	//穿件一个cat类
	function Cat(name){
		this.name = name;
		this.run = function(){
			alert(name +"start run");
			return this;
		}
		this.stopRun = function(){
			alert(name +"stop run");
			return this;
		}
		this.sing = function(){
			alert(name +"start sing");
			return this;
		}
		this.stopSing = function(){
			alert(name +"stop sing");
			return this;
		}		
	}
	//测试
	var c = new Cat("USPCAT");
	c.run().sing().stopSing().stopRun();
	//代码简洁方便多了吧
})()