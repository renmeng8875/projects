//jQ
var jQ=456;
(function (window,undefined) {

var jQ=function (elements) {
	return new jQElement(elements);
},
_nspool={},
push=[].push,
toS=Object.prototype.toString;

function jQElement(elements) {
	push.apply(this,elements);
}

//jQuery(document) //jQuery.fn.init的实例
//jQuery.trim();  //jQuery的静态方法



jQuery(function () {
	/*var o1={
		names:[1,2,3]
	};
	var o2={};
	jQuery.extend(true,o2,o1);
	alert(o2.names===o1.names);*/
	
	//jQuery(":header").html(123);
});
jQuery.fn.extend({
	getTagName:function () {
		return this[0].tagName;
	},
	setTitle:function(title) {
		this.attr("title",title);
	}
});

jQuery(document).ready(function () {
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
