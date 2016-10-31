/**
 * 用2个DAO来体现门面模式
 */
(function(){
	//人员类
	var PersonDao = new Interface("PersonDao",["getInfo","learn",
			"marry"]);
	var Person = function(){
		this.name = "YUNFENGCHENG";
		this.address = "BEIJING";
		this.getInfo = function(){
			return "名字: "+this.name +" 地址: "+this.address;
		}
		this.learn = function(){
			document.write("学习");
		}
		this.marry = function(){};
		//验证实现的接口
		Interface.ensureImplements(this,PersonDao);
	}
	//DOG DAO
	var DogDao = new Interface("DogDao",["call","run","getInfo"]);
	var Dog = function(){
		this.name = "DAHUANG";
		this.getInfo = function(){
			return "狗狗的名字: "+this.name;
		}
		this.run = function(){};
		this.call = function(){};
		Interface.ensureImplements(this,DogDao);
	}
	//需求是现在需要给养的够办了相应宠物领养证件  需要人和狗狗的信息可以
	//1.不用门面
	//客户端程序
	function action(person,dog){
		//当做养狗证的号码
		var r = "GG"+new Date().getDate()+Math.floor(Math.random()*11);
		var str = "办证成功 :编号 "+r
		+"<br>主人信息: "+person.getInfo()
		+"<br>狗狗的信息: "+dog.getInfo();
		document.write(str);
	}
	action(new Person(),new Dog());
	document.write("<br>..........................");
	//使用门面模式
	//负载的事交给门面来做
	function facade(person,dog){
		//当做养狗证的号码
		var r = "GG"+new Date().getDate()+Math.floor(Math.random()*11);
		this.str = "办证成功 :编号 "+r
		+"<br>主人信息: "+person.getInfo()
		+"<br>狗狗的信息: "+dog.getInfo();
	}
	facade.prototype.action = function(){
		return this.str;
	}
	//客户端程序
	function action2(person,dog){
		document.write(new facade(person,dog).action());
	}
	action2(new Person(),new Dog())
	//用了门面模式客户端代码就变的如此的简单了
})()











