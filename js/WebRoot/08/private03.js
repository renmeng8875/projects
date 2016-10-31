/**
 * 闭包实现封装
 */
(function(){
	function person(name,age,email,sex){
		this.email = email;//public 变量
		//get
		this.getName = function(){
			return this.name;
		}
		this.getAge = function(){
			return this.age;
		}		
		//set
		this.setName = function(name){
			this.name = name
		}
		this.setAge = function(age){
			if(age>0 && age < 150){
				this.age = age
			}else{
				throw new Error("年龄必须在0到150之间");
			}				
		}
		var _sex = "M";//这也是私有变量的编写方式
		this.getSex = function(){
			return _sex;
		}
		this.setSex = function(){
			_sex = sex
		}
		this.init = function(){
			this.setName(name);
			this.setAge(age);
		}
		this.init();
	}
	//ceshi 
	var p = new person("JIM",-1,"www.USPCAT@126.COM")
})()




