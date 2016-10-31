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
