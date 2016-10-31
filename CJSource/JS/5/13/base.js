//jQ
var jQ=456;
(function (window,undefined) {

var jQ=function (elements) {
	return new jQElement(elements);
},
_nspool={},
push=[].push,
toS=Object.prototype.toString;
//fn返回false，停止循环，如果返回值不为undefined，那么就保存下来
//然后作为each的方法的返回
jQ.each=function (o,fn,args) {
	var i=0,ret,ary=[]
	if ("length" in o) {
		for (;i<o.length;i++) {
			ret=fn.apply(o[i],args || [i,o[i]]);
			if (ret===false) break;
			if (ret!=undefined) ary.push(ret);
		}
	} else {
		for (i in o) {
			ret=fn.apply(o[i],args || [i,o[i]]);
			if (ret===false) break;
			if (ret!=undefined) ary.push(ret);
		}
	}
	return ary;
};


function jQElement(elements) {
	push.apply(this,elements);
}
jQElement.addMagicMethod=function (methodName,fn) {
	jQElement.prototype[methodName]=function () {
		var ret=jQ.each(this,fn);
		if (ret.length===0)
			return this;
		else
			return ret[0];
	};
};
jQElement.addMagicMethod({
	attr:function (name,value) {
		this.name=value;
	}
});


jQuery(function () {
	var h=$(":header");
	/*var ret=jQ.each({name:"CJ",age:21},function () {
		return this+this;
	});
	alert(ret);*/
});






jQ.ns=function (name,value) {
	var names=name.split("."),o=window;
	for (var i=0;i<names.length-1;i++) {
		if (!o[names[i]]) {
			o[names[i]]={};
		}
		o=o[names[i]];
	}
	_nspool[name]=o[names[i]];
	o[names[i]]=value;
};
//pkg.lib.ui

//jQ.ns("jQ",jQ);


})(window);
