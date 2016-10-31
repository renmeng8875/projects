	
	//定义接口类Interface
	var Interface = function(name,methods){
		if(arguments.length != 2){
			alert("Interface must have two parameters...");
		}
		this.name = name;//把传入的nane作为接口的名称
		this.methods = [];//定义一个空数组为了装载接口中函数的名字
		for (var i = 0; i < methods.length; i++) {
			if(typeof methods[i] !== "string"){
				alert("method name mush is string...");
			}else{
				this.methods.push(methods[i]);
			}
		}
	}
	//定义接口的一个静态方法来实现接口与实现类直接的检验
	//静态方法不要写成Interface.prototype.* 这样是写到原型连上
	//静态函数是在类层次上操作,而不是在对象层次上操作(类也是一个对象) 我们下面章节在聊具体的事
	//(object,...)
	Interface.ensureImplements = function(object) {
	    if(arguments.length < 2) {
	    	alert("method was introduced into at least two parameters...")
	    }
	    //遍历分析每一个接口对象
		for(var i = 1 ; i < arguments.length; i++) {
	   		var inter = arguments[i];
	   		//如果你是接口就必须是Interface的实例
	        if(inter.constructor !== Interface) {
	            throw new Error("If is interface classes must Interface");
	        }
	        //遍历函数结合并分析
	        for(var j = 0; j<inter.methods.length;j++) {
	            var method = inter.methods[j];
	            if(!object[method] || typeof object[method] !== 'function') {
	                alert("object " 
	                  + "does not implement the " + inter.name 
	                  + " interface. Method " + method + " was not found.");
	            }
	        }
	    } 
	};