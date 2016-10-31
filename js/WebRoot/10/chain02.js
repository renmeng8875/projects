/**
 * 从一个实例引出立案时调用的需求
 */
(function(){
	//创建一个cat
	function Cat(name){
		this.name = name;
		this.run = function(){
			document.write(name+ " start run");
			return this;
		}
		this.stopRun = function(){
			document.write(name+ " stop run");
			return this;
		}
		this.sing = function(){
			document.write(name+ " start sing");
			return this;
		}
		this.StopSing = function(){
			document.write(name+ " stop sing");
			return this;
		}		
	}
	//测试
	var c = new Cat("USPCAT");
	c.run().stopRun().sing().StopSing();
})()