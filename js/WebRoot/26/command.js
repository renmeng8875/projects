/**
 * 用于消除调用者和接收这直接的耦合的模式
 * 并且可以对(调用这个过程进行留痕操作)
 * 真的不要乱用这个模式,以为他适你简单调用写法变得非常的复杂和有些那一理解
 * (重点)
 * 当你的业务出现了 (回退操作)(重做操作)的需求的时候你就要考虑使用这个模式了
 */
//(function(){
	/**
	 * 需求
	 * 1.有一个"添加流程的按钮"单击的时候 就会添加一个新的文本当做流程的描述
	 * 2.有"返回","重做" 2个按钮来完成相应的任务
	 */
	//主应用程序
	function manager(){
		this.addFlow = function(id,text){
			//1.得到目标节点
			var div = document.getElementById("div01");
			var newFlow = document.createElement("div");
			newFlow.setAttribute("id",id);
			newFlow.innerHTML = text;
			div.appendChild(newFlow);
		}
	}
	//为对象建立命令访问库
	manager.prototype.extcute = (function(){
		/**
		 * command 命令对象
		 */
		return function(command){
			return this[command.method](command.id,command.value)
		}
	})();
	//初始化主类
	var ma = new manager();
	//用于存储"调用对象命令的"集合
	var commands = new Array();
	//集合的游标
	var index = commands.length;
	//客户端
	
	var API = function(){
		this.addFlow = function(){
			//把调用封装起来
			var command = {
				method:"addFlow",
				id:new UUID().createUUID(),
				value:document.getElementById("flow").value
			}
			//把调用对象保存起来,用于回退和重做只用
			commands.push(command);
			//重新定位游标
			index = commands.length;
			//调用
			ma.extcute(command);
		}
		/**
		 * 返回
		 */
		this.re = function(){
			if(index-1 <0){
				alert("已经到了最后一步了...");
			}else{
				all = document.getElementById("div01").childNodes;
				document.getElementById("div01").
					removeChild(all[all.length-1]);
				index = index -1;
			}
		}
		/**
		 * 重做
		 */
		this.again = function(){
			if(index >= commands.length){
				alert("已经到了最前面一步了,不能进行重做...");
			}else{
				var command = commands[index];
				ma.extcute(command);
				index = index+1;
			}
		}
	}
	
	//实例化API
	API = new API();
	//添加 支持ctrl+z
	key("ctrl+z",function(){
		API.re();
	})
	//重做
	key("ctrl+shift+z",function(){
		API.again();
	})
//})()






