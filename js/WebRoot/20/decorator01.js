/**
 * 通过需求引出装饰者模式
 */
(function(){
	/**
	 * 汽车店的接口
	 */
	var CarShop = new Interface("CarShop",["getPrice","assemble"]);
	var myCarShop = function(){
		this.getPrice = function(){
			document.write(150000+"<br>");
		}
		this.assemble = function(){
			document.write("汽车组装.....<br>");
		}
		Interface.ensureImplements(this,CarShop);
	}
	var JIMCarShop = new myCarShop();
	JIMCarShop.getPrice();
	JIMCarShop.assemble();
	document.write("..................<br>")
	/**
	 * 新需求
	 * 汽车还会有附属的产品 音响(K) ,真皮沙发(M),保险杠(N)
	 * 没一个附属的茶品全会影响到到汽车的组装和其价格
	 * 你能想到什么办法呢?
	 */
	//改写接口
	var CarShop2 = new Interface("CarShop2",
		["getPrice","assemble","addK","addM","addN"]);
	var myCarShop2 = function(){
		var price = 150000;
		this.getPrice = function(){
			document.write(price+"<br>");
		}
		this.assemble = function(){
			document.write("汽车组装.....<br>");
		}
		this.addK = function(){
			price += 1000;
		}
		this.addM = function(){
			price += 2000;
		}
		this.addN = function(){
			price += 3000;
		}		
		Interface.ensureImplements(this,CarShop2);
	}
	var JIMCarShop2 = new myCarShop2();
	JIMCarShop2.addK();
	JIMCarShop2.addM();
	JIMCarShop2.addN();
	JIMCarShop2.getPrice();
	JIMCarShop2.assemble();	
	document.write("..................<br>")
	/**
	 * 好像能成功,但是新的问题来了
	 * 你把接口全改了可是我集成本接口的是类不一定全要有音响,M,N
	 * 难道我要修改所有市县本接口的实现类吗?
	 * 显然是不对的
	 */
	//2.如果不改变接口那我就增加子类
	var CarShop = new Interface("CarShop",["getPrice","assemble"]);
	var myCarShop = function(){
		this.getPrice = function(){
			document.write(150000+"<br>");
		}
		this.assemble = function(){
			document.write("汽车组装.....<br>");
		}
		Interface.ensureImplements(this,CarShop);		
	}
	var myCarShopM = function(){
		this.getPrice = function(){
			document.write(150100+"<br>");
		}
		this.assemble = function(){
			document.write("汽车组装.....<br>");
		}
		Interface.ensureImplements(this,CarShop);			
	}
	//var MyCarShopN
	//mycarShopK
	//mycarShopMNK mycarShopMK ......
	/**
	 * 这样会写吐的
	 * 
	 */
	/**
	 * 看看装饰的的概念和用法
	 * 装饰者可以为对象添加新的特性
	 * 透明的把对象包装在具有相同接口的新对象中
	 */
})()







