/**
 * 工厂模式
 * 工厂的目的在于判别接口最终别那个类所实现
 * 产生实例的过程不再用new关键字
 * 最终是类与类直接实现松耦合的目的
 */
(function(){
	
	//写个例子来说明工厂-->利用这个例子引出工厂的概念和用法
	var PetShop = function(){};//宠物商店
	PetShop.prototype = {
	  //出售宠物的方法
	  sellPetShop: function(kind) {
	    var pet;
		//kind 种类
	    switch(kind) {
	      case 'dog':
	        pet = new Dog();
	        break;
	      case 'cat':
	        pet = new Cat();
	        break;
	      case 'pig':
	        pet = new Pig();
	        break;	      
	      default:
	      	//鸟
	        pet = new Bird();
	    }
	    Interface.ensureImplements(pet, Pet);//验证接口关系,在工厂中验证
	    pet.eat();//吃顿饱饭
	    pet.register();//宠物登记
	    return pet;
	  }
	};
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
	//pcat宠物店
	var PcatPetShop = new PetShop();
	var flowerPig = PcatPetShop.sellPetShop("pig");
	flowerPig.run();//跑步
	/**
	 * 貌似很完美,但是经受不起需求的冲击
	 * 比如说宠物店又进来了先进的宠物,"兔子"那我们不必要改写宠物店代码了.耦合多太高
	 * 利用简单工厂来解决问题-->把实例化类的过程叫给工厂
	 * 用一个简单工厂解决 实例类 与  商店之间的耦合
	 */
	document.write("-----------------------------------------------------<br>")
	//静态工厂
	var PetFactory = {
	  sellPetShop: function(kind) {
	    var pet;
	    /**
	     * kind 种类
	     */
	    switch(kind) {
	      case 'dog':
	        pet = new Dog();
	        break;
	      case 'cat':
	        pet = new Cat();
	        break;
	      case 'pig':
	        pet = new Pig();
	        break;	      
	      default:
	      	//鸟
	        pet = new Bird();
	    }
	    Interface.ensureImplements(pet, Pet);//验证接口关系,在工厂中验证
	    return pet;
	  }
	};	
	//利用工厂的新的商店
	var PetShop2 = function(){};//宠物商店
	PetShop2.prototype = {
	  //利用工厂改造
	  sellPetShop: function(kind) {
	  	 var pet = PetFactory.sellPetShop(kind)
	     pet.eat();//吃顿饱饭
	     pet.register();//宠物登记
	     return pet;	  	 
	  }
	};	
	var PcatPetShop2 = new PetShop2();
	var flowerCat = PcatPetShop2.sellPetShop("cat");
	flowerCat.sing();
	/**
	 * 貌似很完美
	 * 但是同样经不起需求的变化
	 * 新的需求
	 *   张三的店和李四的店 虽然全是宠物店
	 *   但是有重点是不一样的 猫猫狗狗大家全有 但是张三主要是外国哈士奇,李四重点是鹦鹉
	 *   难道我的商店类要把几家的宠物犬容纳进来?
	 *   这显然不是不对了,我们用复杂工厂来解决问题
	 */
})()






















