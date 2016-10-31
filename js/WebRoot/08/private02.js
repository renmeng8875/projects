
(function(){
	//用命名规范来区别私有和共有变量
	function Person(name,age,email){
		//定义私有变量
		this._name;//私有
		this._age;//私有
		this.setName(name);
		this.setAge(age);
		this.email = email;//共有
		
	}
	Person.prototype = {
		setName:function(name){
			this._name = name;
		},
		setAge :function(age){
			if(age>0 && age < 150){
				this._age = age;
			}else{
				throw new Error("年龄必须在0到150之间");
			}			
		}
	}
	var p = new Person("JIM",-1,"JIM@USPCAT.COM");
})()