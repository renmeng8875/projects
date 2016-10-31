/**
 * 特权函数
 */
(function(){
	var p = function(){
		var add = function(x,y){
			//...进行复杂的数学操作
		}
		//这是一个信息全封闭的类
		//但是他内部进行过复杂的业务操作
		//我建立一个特选函数,是指调用起来特别方便
		this.bridge = function(){
			return {
				bridgeAdd:function(){
					//....执行前
					add(3,3)
					//....执行后
				}
			}
		}
	}
	//桥梁还可以吧多个类进行桥接(链接)
	var class1 = function(a,b){
		this.a = a;
		this.b = b;
	}
	var class2 = function(c){
		this.c = c;
	}
	var bridegClass = function(){
		this.one = new class1(1,2);
		this.two = new class2(45);
	}
	/**
	 * 有人会问 你这个理念不是门面模式吗?
	 * 他的目的是在与class1和class2能干独立的修改
	 * 儿门面模式的意义在于调用的方便
	 */
})()


