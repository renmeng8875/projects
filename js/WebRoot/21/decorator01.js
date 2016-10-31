/**
 * 装饰者可以用在类上
 * 同样也可以用在类中的函数上
 */
(function(){
	//写一个装饰者函数,函数的目的在于
	//把目标函数的返回数值变成大写
	function upperCaseDecorator(fun){
		return function(){
			return fun().toUpperCase();
		}
	}
	//被封装的函数
	function getDate(){
		return new Date().toString();
	}
	//执行装饰
	getDateCaps = upperCaseDecorator(getDate);
	document.write(getDate()+"<br>");
	document.write(getDateCaps());
	/**
	 * 如果原有的功能不是适合你的项目
	 * 你需要大连的扩充原有功能
	 * 并且不不想改变原有的接口,那你用装饰者模式就对了
	 */
})()







