/**
 * 装饰者模式来解决需求
 */
(function(){
	var CarShop = new Interface("CarShop",["getPrice","assemble"]);
	//这是目标对象
	var myCarShop = function(){
		this.getPrice = function(){
			return 150000;
		}
		this.assemble =function(){
			document.write("汽车组装....<br>")
		}
		Interface.ensureImplements(this,CarShop);
	}
	//装饰类
	//真皮沙发
	var M = function(carShop){
		this.getPrice = function(){
			return 1000+carShop.getPrice();
		}
		this.assemble =function(){
			document.write("M组装....<br>")
		}
		Interface.ensureImplements(this,CarShop);		
	}
	//保险杠
	var N = function(carShop){
		this.getPrice = function(){
			return 2000+carShop.getPrice();
		}
		this.assemble =function(){
			document.write("N组装....<br>")
		}
		Interface.ensureImplements(this,CarShop);		
	}
	//音响
	var K = function(carShop){
		this.getPrice = function(){
			return 3000+carShop.getPrice();
		}
		this.assemble =function(){
			document.write("K组装....<br>")
		}
		Interface.ensureImplements(this,CarShop);		
	}
	var car = new K(new N(new myCarShop))
	var car2 = new M(new K(new N(new myCarShop)));
	alert(car.getPrice());
	alert(car2.getPrice());
})()