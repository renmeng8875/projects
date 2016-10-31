/**
 * 单例模式在JS中使用非常的频繁
 * 通过确保单例对象只存在一个实例,
 * 你就可以确信自己在所有的代码中使用的是全局资源
 */
(function(){
	//先看来一个最简单的单体
	//例如用户登录后的信息可以用一个单体存储
	var UserInfo = {
		name:"USPCAT.COM",
		code:"00101",
		deptName:'PD',
		deptCode:'PD001',
		getName : function(){
			return "YUNFENGCHNEG";
		}
	}
	alert(UserInfo.getName());
	//这就是一个最简单的单体
	//他用来换分命名空间,并且将一群相关的属性和方法组织到一起
	//我们可以用.来访问他
	var comm = {};
	comm.UserInfo = {
		name:"USPCAT.COM",
		code:"00101"		
	}
	comm.funcInfo ={
		funcName:'',
		funcCode:""
	}
	//在大型的项目下,存在这你写的代码,还有你引用外界JS类库
	//还有其他同事写的代码和类库
	//我们通过单体模式就可以很好的区分他
	//这点你只能慢慢的体会了.........
})()



