/**
	 * 创建extend函数
	 */
	function extend(subClass, superClass) {
		//1.叫他的原型类属性等于父类的原型类属性
		var F = function() {};//创建中间过度函数
		F.prototype = superClass.prototype;
		//2.让子类继承F
		subClass.prototype = new F();//中间过度函数的构造比起构造superClass会好很多(速度和性能不用说也知道)
		subClass.prototype.constructor = subClass;
		//3.为子类增加属性superclass 叫他别赋予原型链的引用
		subClass.superclass = superClass.prototype;//目的是送耦合
		//4.加保险 就算你的原型类是超类Object那么也要把你的构造器降下来
		if(superClass.prototype.constructor == Object.prototype.constructor) {
			superClass.prototype.constructor = superClass;
		}
	}