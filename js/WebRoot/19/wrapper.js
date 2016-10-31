/**
 * 适配器是为了解决已有接口有的类不兼容的问题
 * 他类似门面模式,但是机理是截然不同的
 * 门面模式为了简化接口使调用者更加方便
 * 适配器是为了解决接口不兼容的问题
 */
(function(){
	//例如你已经写好这样一个应用API
	//程序员是依托PcatV1 版本来写的客户端
	var PcatV1Lib = new Interface("PcatV1Lib",["add"]);
	function plib(){
		this.add = function(x,y){
			return x+y;
		}
		Interface.ensureImplements(this,PcatV1Lib);
	}
	//客户端
	var lib = new plib();
	//调用
	alert(lib.add(10,20));
	
	//现在需要更换类库,但是前台已经写好的程序我不希望有大的变化
	var PcatV2 = new Interface("PcatV1Lib",["add"]);
	function p2lib(){
		this.add=function(numberList){
			return eval(numberList.join("+"));
		}
		Interface.ensureImplements(this,PcatV1Lib);
	}
	//客户端
	var lib = new p2lib();
	//调用
	//alert(lib.add(10,20));
	//利用适配器来解决问题
	//添加适配器
	var warpper = new Interface("PcatV1Lib",["add"]);
	function warpperPcatV2Lib(){
		this.add = function(x,y){
			var arr = new Array();
			arr.push(x);
			arr.push(y);
			return new p2lib().add(arr);
		}
	}
	lib = new warpperPcatV2Lib();
	alert(lib.add(10,20));
	//利用适配器就可以完成客户端代码不变的情况下改变类库
	//但是这种情况只有客户端代码是标准的时候草ok
	//(负责直接修改客户端也是一个不错的选择)
})()


