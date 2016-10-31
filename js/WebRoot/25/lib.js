Function.prototype.method = function(name,fn){
	this.prototype[name] = fn;
	return this;
}
if(!Array.prototype.filter){
	Array.method("filter",function(fn,thisObj){
		var scope = thisObj || window;
		var a = [];
		for (var i = 0; i < this.length; i++) {
			if(!fn.call(scope,this[i],i,this)){
				continue;
			}
			a.push(this[i])
		}
		//返回过滤好数据
		return a;
	})
}