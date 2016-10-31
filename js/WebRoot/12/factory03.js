/**
 * 真正意义的工厂
 * 真正的JS工厂不是利用另外一个类来初始对象,
 * 而是使用一个子类按照相应的定义来完成类初始化任务
 * 
 * 定义:
 * 工厂是一个把成员变量初始话的任务交给子类中进行的类
 */
(function(){
	var Pet = new Interface("Dog",["eat","register","run","sing"]);
	function BasePet(){//宠物基础类
		this.register = function(){
			document.write("宠物登记...<br>")
		}
		this.eat = function(){
			document.write("吃吨饱饭...<br>")
		}		
	}
	//实现
	function Dog(){
		Dog.superclass.constructor.call(this)
		this.run = function(){
			document.write("小狗跑步...<br>")
		}
		this.sing = function(){
			document.write("小狗唱歌...<br>")
		}		
	}
	function Pig(){
		Pig.superclass.constructor.call(this)
		this.run = function(){
			document.write("小猪跑步...<br>")
		}
		this.sing = function(){
			document.write("小猪唱歌...<br>")
		}		
	}
	function Cat(){
		Cat.superclass.constructor.call(this)
		this.run = function(){
			document.write("小猫跑步...<br>")
		}
		this.sing = function(){
			document.write("小猫唱歌...<br>")
		}		
	}
	function Bird(){
		Bird.superclass.constructor.call(this)
		this.run = function(){
			document.write("小鸟跑步...<br>")
		}
		this.sing = function(){
			document.write("小鸟唱歌...<br>")
		}		
	}
	//继承
	extend(Dog,BasePet);
	extend(Pig,BasePet);
	extend(Cat,BasePet);
	extend(Bird,BasePet);
	//智能工厂工厂
	var PetFactory = {
	  sellPetShop: function(kind) {
	    var pet;
	    pet = eval("new "+kind+"()")
	    Interface.ensureImplements(pet, Pet);//验证接口关系,在工厂中验证
	    return pet;
	  }
	};	
	//1.把核心商店类编程抽象类
	var PetShop = function(){};//宠物商店
	PetShop.prototype = {
	  //利用工厂改造
	  sellPetShop: function(kind) {
	  	 var pet = this.sellPetShop(kind)//调用本类方法
	     pet.eat();//吃顿饱饭
	     pet.register();//宠物登记
	     return pet;	  	 
	  },
	  //本类不能实例化
	  sellPetShop: function(model) {
	    throw new Error('this is a abstract class.');
	  }	  
	};	
	//2.利用子类来满足需求的扩展
	var OnePetShop = function() {};
	extend(OnePetShop, PetShop);
	//复写父类方法
	OnePetShop.prototype.sellPetShop = function(kind) {
	   	var pet = null;
	   	var pets = ["Dog","Cat","Bird"]
	   	for(v in pets){
	   		if(pets[v] == kind){
			    pet = PetFactory.sellPetShop(kind);
			    Interface.ensureImplements(pet, Pet);//验证接口关系,在工厂中验证
			    pet.eat();//吃顿饱饭
			    pet.register();//宠物登记		   			
	   			break;
	   		}
	   	}
	    return pet;
	};
	//再开一个店
	var twoPetShop = function() {};
	extend(twoPetShop, PetShop);
	//复写父类方法
	twoPetShop.prototype.sellPetShop = function(kind) {
	   	var pet = null;
	   	var pets = ["Pig"]
	   	for(v in pets){
	   		if(pets[v] == kind){
			    pet = PetFactory.sellPetShop(kind);
			    Interface.ensureImplements(pet, Pet);//验证接口关系,在工厂中验证
			    pet.eat();//吃顿饱饭
			    pet.register();//宠物登记		   			
	   			break;
	   		}
	   	}
	    return pet;
	};
	//试验
	var jim = new OnePetShop();
	jim.sellPetShop("Dog");
	jim.sellPetShop("Pig");
})()









