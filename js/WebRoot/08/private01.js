/**
 * 信息的隐藏是最终的目的,封装只不过是隐藏的一种方法 
 */
(function(){
	/**
	 * 1.门户大开类型
	 * 2.用命名规范区别私有和共有的方式
	 * 3.闭包
	 */
	//门户打开型
	function Person(age,name){
		this.name = name;
		if(!this.checkAge(age)){
			throw new Error("年龄必须在0到150之间");
		}
		this.age = age;
	}
	//var p = new Person(-10,"JIM");
	//alert(p.age)
	//解决上述问题
	Person.prototype = {
		checkAge:function(age){
			if(age>0 && age < 150){
				return true;
			}else{
				return false;
			}
		}
	}
	Person.prototype["getName"] = function(){
		return this.name || "USPCAT.COM";	
	}
	//var p = new Person(-10,"JIM");
	var p = new Person(27,"JIM");
	var p2 = new Person(27);
	alert(p2.getName());
	
	
	
	
	
	
	
	
	
	
	
	
})()