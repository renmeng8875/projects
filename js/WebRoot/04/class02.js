/**
 * Map
 */
(function(){
	function jMap(){
		//私有变量
		var arr = {};
		//增加
		this.put = function(key,value){
			arr[key] = value;
		}
		//查询
		this.get = function(key){
			if(arr[key]){
				return arr[key]
			}else{
				return null;
			}
		}
		//删除
		this.remove = function(key){
			delete arr[key]
		}
		//遍历
		this.eachMap = function(fn){
			for(var key in arr){
				fn(key,arr[key])
			}
		}
	}
	var country = new jMap();
	country.put("01","ZG");
	country.put("02","HG");
	country.put("03","MG");
	country.put("04","TG");
	//alert(country.get("01"))
	country.remove("01")
	//alert(country.get("01"))
	
	country.eachMap(function(key,value){
		document.write(key+"-->"+value+"<br>");
	})
})()






