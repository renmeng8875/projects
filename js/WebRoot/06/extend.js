/**
 * 继承
 */
(function(){
	//创建一个人员类
	function Person(name){
		this.name = name;
	}
	//创建教师类
	function Teacher(name,books){
		//call方法可以将一个函数的对象上下文从初始化变成有this来决定
		//调用Person的构造函数,因为Person没用new 所以他是个空对象
		//相当于java中的super函数
		Person.call(this,name);
		this.books = books;
	}
	//使老师类继承人员类
	Teacher.prototype = new Person();
	Teacher.prototype.constructor = Teacher;
	Teacher.prototype.getBook = function(){
		return this.name +" "+ this.books;
	}
	//测试
	var jim = new Teacher("JIM","EXTJS4");
	//alert(jim.getBook());
	
	/**
	 * 创建Extend函数为了程序中石所有的集成操作
	 */
	function extend(subClass,superClass){
		//1.叫子类原型类属性等于父类的原型属性
		//初始化一个中间空对象,为了转换主父类关系
		var F = function(){};
		F.prototype = superClass.prototype;
		//2.让子类集成F
		subClass.prototype = new F();
		subClass.prototype.constructor = subClass;
		//3.为子类增加属性superClass
		subClass.superClass = superClass.prototype;
		//4.增加一个保险,就算你是的原型类是超类(Object) 那么也要把你的构造函数级别讲下来
		if(superClass.prototype.constructor == Object.prototype.constructor){
			superClass.prototype.constructor = superClass;
		}
	}
	//测试
	function Author(name,books){
		Author.superClass.constructor.call(this,name);
		//Person.call(this,name);
		this.books = books;
		this.getBook = function(){
			return this.name +" "+ this.books;
		}
	}
	//继承
	extend(Author,Person);
	//彼德
	var peter = new Author("YUNFENGCHENG","JAVASCIPT");
	alert(peter.getBook())
})()












