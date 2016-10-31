/**
 * 函数传参
 */
(function(){
	//1.参数传递的随意性
	function add(x,y,z){
		this.x = x||0;
		this.y = y||0;
		this.z = z||0;
		alert(this.x+this.y+this.z)
	}
	//add(12,3,5)
	//add(14,5)
	//弊端 : 无法想java等高级语言那有 有函数精确复写的特性
	//技巧 : 如果你这个类是工具类的情况下,那没你接收的参数最好是对象
	/**
	 * conf = {gridName:"",gridCode:"",gridSize:""}
	 */
	function gridUtil(conf){
		alert(conf["gridName"]+" "+conf["gridSize"]);
	}
	gridUtil({gridName:"YUNFENGCHENG",gridSize:10});
	//传值还是传址
	var i = 100;
	var s = "one";
	function add3(i,s){
		i++;
		s+="--"
	}
	//alert(i);//100 or 101
	//alert(s);//"one" or one--
	/**
	 * 证明 : 基础变量是传递数值的
	 * 自定义对的传参方式是传得"地址"
	 */
	//对象
	var o = {name:"YUNFENGCHENG"}
	function change(o){
		o["name"] = "USPCAT.COM"
	}
	change(o);
	alert(o.name)
})()







