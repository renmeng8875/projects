/**
 * 组合模式
 */
(function(){
	//统一接口
	var composite = new Interface("composite",["getChildByName","add"]);
	var student= new Interface("composite",["goToClass","finishClass"]);
	//定义组合类
	var compositeObj = function(name){
		this.name = name;
		this.type = "com";//默认是组合类
		var childs = new Array();
		//得到相关的所有孩子节点
		this.getChildByName = function(name){
			//涉及到递归
			var toChilds = new Array();
			if(!name){
				for (var i = 0; i < childs.length; i++) {
					if(childs[i].type == "com"){
						toChilds = toChilds.concat(childs[i].getChildByName());
					}else{
						toChilds.push(childs[i]);
					}
				}
			}else{
				for (var i = 0; i < childs.length; i++) {
					if(childs[i].name == name){
						if(childs[i].type == "com"){
							toChilds = 	toChilds.concat(childs[i].getChildByName());
							break;
						}else{
							toChilds.push(childs[i]);
							break;
						}
					}else{
						if(childs[i].type == "com"){
							toChilds = 	toChilds.concat(childs[i].getChildByName(name)); 
						}
					}
				}
			}
			return toChilds; 
		}
		//增加子节点
		this.add = function(child){
			childs.push(child);
			return this;
		}
		//去上课
		this.goToClass = function(name){
			var toChilds = this.getChildByName(name);
			for (var i = 0; i < toChilds.length; i++) {
				toChilds[i].goToClass();
			}
		}
		//下课
		this.finishClass = function(name){
			var toChilds = this.getChildByName(name);
			for (var i = 0; i < toChilds.length; i++) {
				toChilds[i].finishClass();
			}			
		}
		Interface.ensureImplements(this,composite,student)
	}
	//定义叶子类
	var studentObj = function(name){
		this.name = name;
		this.type = "stu";//默认是叶子	
		//得到相关的所有孩子节点
		this.getChildByName = function(name){
			if(this.name == name){
				return this;
			}else{
				return null;
			}
		}
		//增加子节点
		this.add = function(child){
			throw new Error("add 不成被初始化(在叶子了中)");
		}
		//去上课
		this.goToClass = function(name){
			document.write(this.name +" 去上课<br>");
		}
		//下课
		this.finishClass = function(name){
			document.write(this.name +" 下课<br>");
		}	
		Interface.ensureImplements(this,composite,student)		
	}
	//测试
	var a = new studentObj("a");
	var b = new studentObj("b");
	var c = new studentObj("c");
	var d = new studentObj("d");
	var e = new studentObj("e");
	var f = new studentObj("f");
	var g = new studentObj("g");
	var h = new studentObj("h");
	var i = new studentObj("i");
	var one = new compositeObj("一班");
		var oneOne = new compositeObj("一班一组");
			oneOne.add(a).add(b);
		var oneTwo = new compositeObj("一班二组");
			oneTwo.add(c).add(d);
		
		one.add(oneOne).add(oneTwo);
	var two = new compositeObj("二班"); 
		var twoOne = new compositeObj("二班一组"); 
			twoOne.add(e).add(f);
		var twoTwo = new compositeObj("二班二组");
			twoTwo.add(g).add(h).add(i)
		two.add(twoOne).add(twoTwo);
	var usPcat = new compositeObj("波斯卡特计算机培训学校");
	usPcat.add(one).add(two);
	//客户端调用API
	usPcat.goToClass();
	document.write("-------------------------<br>");
	usPcat.goToClass("一班");
	document.write("-------------------------<br>");
	usPcat.goToClass("二班一组");
	document.write("-------------------------<br>");
	usPcat.goToClass("a");	
})()











