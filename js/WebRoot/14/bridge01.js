/**
 *桥梁模式
 *作把"将抽象与实现隔离开来,以遍2者单独的变化"
 *这种模式对于javascript之呢过常见的事件驱动编程有很大的好处
 */
(function(){
	//一个页面选择宠物的例子
	//我写一写伪代码
	button.addEvent(element,"click",getPetByBame);
	function getPetByBame(e){
		var id = this.id;
		sayncRquest("GET",'pet.action?id='+id,function(pet){
			consols.log("request pet"+pet.resopnseText)
		})
	}
	/**
	 * 这种做法是你在页面有一个按钮,单击的话会触发后面请求
	 * 如果你想着一个方法进行单元测试
	 * 
	 * 1.用户登陆 2.知道到你这个页面3.单击你的按钮
	 * 如果需要进行效能层次上的单元测试,那么就祝你好运吧.
	 */
	//第二种做法(用简单的桥梁模式来解决)
	function getPetByBame(id,callBack){
		sayncRquest("GET",'pet.action?id='+id,function(pet){
			callBack(pet)
		})
	}	
	//定义一个桥梁叫抽象和实现相互联系在一起
	addEvent(element,"click",getPetByNameBridge)
	function getPetByNameBridge(){
		getPetByBame(this.id,function(pet){
			consols.log("request pet"+pet.resopnseText);
		})
	}
	
	/**
	 * 这种做法使API和展现层完全分离
	 * API和展现层可以灵活的变动
	 * 这个模式在Extjs项目开发时候非常的常用
	 */
	//桥梁模式的其他用途
	//特权函数
	//当你的接口过于复杂的时候,吧原本复杂的接口用桥梁的模式出去出一大部分函数整合起来是之客户端
	//更容易的调用
	
	
	
})()