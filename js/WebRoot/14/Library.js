/**
 * 我们自己的类库
 */
function addEvent(obj,type,fn){
	if(obj.addEventListener){
		obj.addEventListener(type.fn,false);
	}else if(obj.attachEvent){
		obj["e"+type+fn] = fn;
		obj[type+fn] = function(){
			obj["e"+type+fn]()
		}
		obj.attachEvent("on"+type,fn)
	}
}