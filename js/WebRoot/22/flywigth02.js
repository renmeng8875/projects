/**
 * 享元模式是一个为了提高性能(空间复杂度)的设计模式
 * 他使用与程序会生产大量的相类似的对象是耗用大量的内存的问题
 */
(function(){
	//有一个城市要进行汽车的登记
	/**
	 * 制造商
	 * 型号
	 * 拥有者
	 * 车牌号码
	 * 最近一次登记日期
	 */
	var Car = function(make,model,year){
		this.make = make;
		this.model = model;
		this.year = year;
		this.getMake = function(){
			return this.make;
		}
	}
	//2.单例模式的简单工程
	var myCarInfo = function(){
		this.createCar = function(make,model,year,owner,tag,renewDate){
			var c = carInfoFactory(make,model,year);
			c["owner"] = owner;
			c["tag"] = tag;
			c["renewDate"] = renewDate;
			return c;
		}
	}
	var carInfoFactory = (function(){
		var carInfo = {};
		return function(make,model,year){
			if(carInfo[make+model+year]){
				return carInfo[make+model+year];
			}else{
				var newCar = new Car(make,model,year);
				carInfo[make+model+year] = newCar;
				return newCar;
				a
			}
		}
	})();	
	//北京车两 4150000 全部要登记
	var test = new myCarInfo();
	var startDate = new Date().getTime();
	var ca = new Array();
	for (var i = 0; i < 4150000; i++) {
		ca.push(test.createCar("东风","雪铁龙","2012-4-8",
					"云凤程","京pcat2145","2012-2-13"))
	}
	var endDate = new Date();
	alert(endDate - startDate);	
})()



