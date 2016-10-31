/**
 * 门面模式
 *  作用
 *   简化类的接口
 *   消除类和使用本类的客户端的代码耦合
 *   同春构建一个简单的门面代码叫负载的系统变得更简单
 */
(function(){
	/**
	 * 各种浏览器对于DOM事件注册是不一样的(那么每一个浏览器我们全被看出一个子系统)
	 * 程序员如果天天和这些问题打交道的话那重点就偏离了原本的业务
	 * 我们先看看ppt
	 */
	//门面
	function addEvebtFacade(el,type,fn){
		if(window.addEventListener){
			//使用与火狐浏览器
			el.addEventListener(type,fn,false);
		}else if(window.attachEvent){
			//适用于IE的
			el.attachEvent("on"+type,fn);
		}else{
			el["on"+type] = fn;
		}
	}
	document.write("<a id='but1' href='#'>click</a>");
	var el = document.getElementById("but1");
	addEvebtFacade(el,"click",function(){
		alert("ok")
	})
})()