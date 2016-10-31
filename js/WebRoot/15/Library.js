/**
 * 自己的类库
 * @param {} obj
 * @param {} type
 * @param {} fn
 */
function addEvent( obj, type, fn ) {
	if (obj.addEventListener) {
		obj.addEventListener( type, fn, false );
	}
	else if (obj.attachEvent) {
		obj["e"+type+fn] = fn;
		obj[type+fn] = function() { obj["e"+type+fn]( window.event ); }
		obj.attachEvent( "on"+type, obj[type+fn] );
	}
	else {
		obj["on"+type] = obj["e"+type+fn];
	}
}
	
