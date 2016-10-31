/**
 * 工厂的目的在于判别接口最终用那类来实例化
 * 产生实例的过程不用new 关键字
 * 最终达到的效果是,多态,和类与类之间的松耦合
 */
(function(){
	var Pet = new Interface("Pet",["eat","run","sing","reginster"]);
	//宠物店	
	var PetShop = function(){}
	PetShop.prototype = {
		//出售宠物的方法
		sellPeyShop:function(kind){
			//宠物对象
			var pet;
			//kind 种类
			switch(kind){
				case 'dog':
					pet = new Dag();
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
			//验证接口
			Interface.ensureImplements(pet,Pet);
			pet.eat();
			pet.reginster();
			return pet;
		}
	}
	//宠物的基类
	function BasePet(){
		this.reginster = function(){
			document.write("宠物登记.....<br>");
		}
		this.eat = function(){
			document.write("吃顿饱饭.....<br>");
		}
	}
	//实现
	function　Dog(){
		Dog.superClass.constructor.call(this);
		this.run = function(){
			document.write("小狗跑步.....<br>");
		}		
		this.sing = function(){
			document.write("小狗唱歌.....<br>");
		}
	}
	function　Pig(){
		Pig.superClass.constructor.call(this);
		this.run = function(){
			document.write("小猪跑步.....<br>");
		}		
		this.sing = function(){
			document.write("小猪唱歌.....<br>");
		}
	}
	function　Cat(){
		Cat.superClass.constructor.call(this);
		this.run = function(){
			document.write("小猫跑步.....<br>");
		}		
		this.sing = function(){
			document.write("小猫唱歌.....<br>");
		}
	}
	function　Bird(){
		Bird.superClass.constructor.call(this);
		this.run = function(){
			document.write("小鸟跑步.....<br>");
		}		
		this.sing = function(){
			document.write("小鸟唱歌.....<br>");
		}
	}	
	//继承
	extend(Dog,BasePet);
	extend(Pig,BasePet);
	extend(Cat,BasePet);
	extend(Bird,BasePet);
	
	//Pcat 宠物店
	var pcatPetShop = new PetShop();
	var flowerPig = pcatPetShop.sellPeyShop("pig");
	flowerPig.run();
	/**
	 * 貌似很完美,但是他记不住需求的变化
	 * 比如说宠物商店又进来一些新的品种的宠物
	 * 这个时候用目前的方法必修要修改宠物商店这个类
	 * 用一个简单工程来解决 
	 */
	//静态工厂
	var PetFactoy = {
		sellPeyShop:function(kind){
			//宠物对象
			var pet;
			//kind 种类
			switch(kind){
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
			//验证接口
			Interface.ensureImplements(pet,Pet);
			return pet;
		}		
	}
	//利用工厂的新宠物商店
	var PetShop2 = function(){};
	PetShop2.prototype = {
		sellPeyShop:function(kind){
			var pet = PetFactoy.sellPeyShop(kind);
			pet.eat();
			pet.reginster();
			return pet;
		}
	}
	var PcatPetShop2 = new PetShop2();
	var flowerCar = PcatPetShop2.sellPeyShop("cat");
	flowerCar.sing();
	/**
	 * 貌似很完美
	 * 	新的需求
	 * 张三的店和李四的店,虽然全是宠物店
	 * 但是重点不一样,张三他主要卖哈士奇,
	 * 李四主要卖 各种各样的鸟
	 */
	
	
	
})()




