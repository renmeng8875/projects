/**
 * 装饰者完成对函数性能的测试任务
 */
(function(){
	var ListBuilder = function(el,listSize){
		this.el = document.getElementById(el);
		this.size = listSize;
		//创建类表
		this.buildeList = function(){
			var root = document.createElement("ol");
			//把ol追加到DIV上
			this.el.appendChild(root);
			for (var i = 0; i < this.size; i++) {
				var li = document.createElement("li");
				root.appendChild(li);
			}
		}
	}
	var list = new ListBuilder("div01",1000);
	//list.buildeList();
	//利用装饰者来坚持函数的执行时间
	var simpleProfiler = function(componet){
		this.componet = componet;
		this.ListBuilder = function(){
			var startDate = new Date().getTime();
			this.componet.buildeList();
			var endDate = new Date();
			alert(endDate - startDate);
		}
	}
	//new simpleProfiler(list).ListBuilder();
	//改造装饰着使其可以完成所有函数的效率测试工作
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
	
	new simpleProfiler2(list).action("buildeList");
})()







