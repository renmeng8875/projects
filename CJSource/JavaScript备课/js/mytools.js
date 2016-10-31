/*
 *Powered By CJ.
 *作者:成吉
 *2009-4-22
*/
String.prototype.trim = function (param) {
	switch (param) {
		case "L":
			return this.replace(/^\s+/g,"");
		case "R":
			return this.replace(/\s+$/g,"");
		case "F":
			return this.replace(/\s+/g,"");
		default:
			return this.replace(/^\s*(\S*)\s*/g,"$1");
	}
};
String.prototype.parseToDOM = function () {
	var container = document.create("div");
	container.innerHTML = this.toString();
	container = _get(container);
	container.clearWhiteSpace();
	if (container.childNodes.length == 1) {
		return container.firstChild;
	} else if (container.childNodes.length > 1) {
		var frag = document.createDocumentFragment();
		for (var i=0;i< container.childNodes.length;i++) {
			frag.appendChild(container.childNodes[i].cloneNode(true));
		}
		container = null;
		return frag;
	}
};
String.prototype.htmlEncode = function () {
	var t = document.create("div");
	t.appendChild(document.createTextNode(this.toString()));
	return t.innerHTML.replace(/\'/g,"&#39;").replace(/\"/g,"&quote;").replace(/\%/g,"&#37;").replace(/\?/g,"&#63;").replace(/\s+/g,"&nbsp;");
};
String.prototype.safeCode = function (param) {
	var str = this.toString();
	if (!param) return encodeURIComponent(encodeURIComponent(str));
	else return decodeURIComponent(decodeURIComponent(str));
};
String.prototype.addslashes = function () {
	return this.toString().replace(/\\/g,"\\\\").replace(/\"/g,"\\\"").replace(/\'/,"\\'");
};
String.prototype.nl2br = function () {
	return this.toString().replace(/\n\r/g,"<br />").replace(/\r\n/g,"<br />").replace(/\n/g,"<br />").replace(/\r/g,"<br />");
};
Array.prototype.each = function(fn,reArr) { //看上去不太有用的函数(但在JavaScript 1.6中有类似的新增方法-- forEach ,every,some还有filter,它们的功能都可以用这个函数实现,并且这个函数将结果作为返回值返回,我认为这更灵活,只是没有原生的速度快)
	if (!reArr) reArr = [];
	for (var i  in this) {
		if (!this.hasOwnProperty(i)) continue;
		var result = fn(this[i],i);
		if (result === false) break;
		if (typeof result == "undefined") continue;
		reArr.push(result);
	}
	return reArr;
};
Number.prototype.between = function (a,b,contain) {
	if (!contain) return (a < this.valueOf() && b > this.valueOf()) || (a > this.valueOf() && b < this.valueOf());
	else return (a <= this.valueOf() && b >= this.valueOf()) || (a >= this.valueOf() && b <= this.valueOf());
};
//将对象简单转换成JSON字符串
Object.prototype.toJSON = function (obj,met) {
	obj = obj || this;
	var reStrArr = [];
	for (var i in obj) {
		if (!obj.hasOwnProperty(i))  continue;
		if (typeof obj[i] != "object") {
			reStrArr.push( "\""+i+"\":" +((typeof obj[i] != "string")?obj[i]:("\""+obj[i].toString().addslashes()+"\"")));
		} else {
			reStrArr.push("\""+i+"\":"+Object.toJSON(obj[i],1)); //因为有些对象不具有原型，所以不一定有toJSON方法
		}
	}
	var reStr = reStrArr.join(",");
	reStrArr = null;
	if (!met) reStr = "(function () { return {"+reStr+"}; })();";
	else reStr = "{"+reStr+"}";
	return reStr;
};
//简单测试一下两个对象是否相等，只要属性相等，就认为两个对象是值相等的
Object.prototype.equals = function (obj) {
	for (var i in this) {
		if (this.hasOwnProperty(i) != obj.hasOwnProperty(i)) return false;
		if (!this.hasOwnProperty(i)) continue;
		if (this[i].equals && this[i].equals(obj[i])) continue;
		if ( this[i] != obj[i] )  return false;
	}
	for (var i in obj) {
		if (!obj.hasOwnProperty(i)) continue;
		if (!this[i]) return false;
	}
	return true;
};


/*添加和删除DOM事件*/
function addEvent(obj,evtype,fn,bubble) {
	if (typeof bubble != "undefined") { //假如提供事件是否冒泡的标识,就说明是想要注册事件监听,并且可能需要捕获,而不是古老的那种方法.事实上利用Dean Edwards所写的那个函数,已经非常好,除了它不支持捕获(应该在不需要捕获时一直使用这个函数)
		if (obj.addEventListener) obj.addEventListener(evtype,fn,!!bubble);
		else if (obj.attachEvent) obj.attachEvent("on"+evtype,function () {
			fn(addEvent.fixEvent(window.event));
		});
	} else { //以下代码(以及delEvent,fixEvent.....里相关的部分)为Dean Edwards 发明的,我在这里借用了一下^0^
		if (!fn.$$eventID) fn.$$eventID = addEvent.eventsCounter++;
		if (!obj.events) obj.events={};
		if (!obj.events[evtype]) {
			obj.events[evtype] = [];
			if (obj["on"+evtype] && obj["on"+evtype] != addEvent.handleEvent) obj.events[evtype][0]=obj["on"+evtype];
		}
		obj.events[evtype][fn.$$eventID]=fn;
		obj["on"+evtype]=addEvent.handleEvent;
	}
}
addEvent.eventsCounter =1;
addEvent.fixEvent = function (evt) {
	evt.preventDefault =addEvent.fixEvent.preventDefault;
	evt.stopPropagation = addEvent.fixEvent.stopPropagation;
	evt.target = evt.srcElement;
	evt.pageX = evt.clientX+(document.documentElement.scrollLeft || document.body.scrollLeft);
	evt.pageY = evt.clientY+(document.documentElement.scrollTop || document.body.scrollTop);
	if (evt.target)	evt.layerX = evt.offsetX+evt.target.clientLeft; //在这里我把	Opera 给放弃了
	if (evt.target)	evt.layerY = evt.offsetY+evt.target.clientTop;
	return evt;
};
addEvent.fixEvent.preventDefault = function () {
	this.returnValue = false;
};
addEvent.fixEvent.stopPropagation = function () {
	this.cancelBubble = true;
};

addEvent.handleEvent = function (evt) {
	evt = evt || addEvent.fixEvent(window.event);
	var reVal  = false;
	var obj = this;
	if (!obj.events || !obj.events[evt.type]) return false;
	var fns = obj.events[evt.type];
	fns.each(function (one,keyName) {
		//if (keyName.toString().indexOf("$$")==0) return; //为以$$开头的属性保留,因为它们要用来做其它的标识
		if (one.call(obj,evt) === true) reVal = true;
	});
	return reVal;
};
function delEvent(obj,evtype,fn,bubble) {
	if (typeof bubble != "undefined") {
		if (obj.removeEventListener) obj.removeEventListener(evtype,fn,bubble || false);
		else if (obj.detachEvent) obj.detachEvent("on"+evtype,fn);
	} else {
		if (!obj.events || !obj.events[evtype]) return false;
		if (obj.events[evtype][fn.$$eventID]==fn) delete obj.events[evtype][fn.$$eventID];
	}
}

//假如是使用Dean Edwards所写的那个事件注册函数,那么下面两个函数就几乎用不到了
function stopBubble(evt) {  //终止事件冒泡
	if (evt.stopPropagation) evt.stopPropagation();
	else if (evt.srcElement) window.event.cancelBubble = true;
	else return false; 
}
function stopDefault(evt) {  //取消浏览器默认行为
	if (evt.preventDefault) evt.preventDefault();
	else if (evt.srcElement) window.event.returnValue = false;
	else return false;
}
/*End EventFunctions*/




if (!HTMLElement) {
	var HTMLElement = function () {};
	HTMLElement.prototype = new Object();
}
HTMLElement.prototype._get = _get;
HTMLElement.prototype.trans =  trans;

//其实getPos和moveTo方法只适用于采用定位的元素,因为不采用定位的元素设置margin为auto时在火狐和Opera中将没有办法得到它的值
HTMLElement.prototype.moveTo = function (finish,timeout,offParent) {
	//if (!this.offsetParent) return false;
	var de = document.documentElement;
	offParent = offParent || de;
	if (typeof finish.left == "string") {
		var parentWidth = (offParent==window)?(de.clientWidth || window.innerWidth || document.body.clientWidth):offParent.offsetWidth;
		var objWidth = this.offsetWidth;
		if (finish.left == "left") finish.left=0;
		else if (finish.left == "right") finish.left =  parentWidth  - objWidth;
		else if (finish.left == "center")finish.left = Math.round(( parentWidth  - objWidth)/2);
		else if (finish.left.indexOf("%")!=-1) finish.left = parseInt(( parentWidth  - objWidth)*parseInt(finish.left)/100);
	}
	if (typeof finish.top == "string" ) {
		var parentHeight = (offParent==window)?(de.clientHeight || window.innerHeight || document.body.clientHeight):offParent.offsetHeight;
		var objHeight = this.offsetHeight; 
		if (finish.top == "top") finish.top = 0;
		else if (finish.top == "bottom")finish.top = parentHeight - objHeight;
		else if (finish.top == "center") finish.top = Math.round(( parentHeight - objHeight)/2);
		else if (finish.top.indexOf("%")!=-1) finish.top = parseInt((parentHeight - objHeight)*parseInt(finish.top)/100);
	}
	var pos = this.getPos(offParent);
	if (pos.left == finish.left && pos.top == finish.top) return; //假如目前的位置和终点相等,则不执行
	var start = {
		left:parseInt(this.getStyle("left")) || 0 ,
		top:parseInt(this.getStyle("top")) || 0 
	};
	finish.left = finish.left-pos.left+start.left +"px";
	finish.top = finish.top - pos.top+start.top + "px";
	start.left += "px";
	start.top += "px";
	if (Number(timeout)) {
		this.trans(start,finish,timeout);
	} else {
		this.style.left =finish.left;
		this.style.top = finish.top;
	}
};
HTMLElement.prototype.getPos = function (offParent) {
	var obj = this;
	var reObj = {
		left:0,
		top:0
	};
	while (obj) {
		reObj.left += obj.offsetLeft;
		reObj.top += obj.offsetTop;
		obj = obj.offsetParent;
	}
	var margin = {
		left:parseInt(this.getStyle("marginLeft")) || 0,
		top:parseInt(this.getStyle("marginTop"))  || 0
	};
	if (margin.left) reObj.left += margin.left;
	if (margin.top) reObj.top += margin.top;
	if (offParent) {
		if (offParent == window) {
			reObj.left -= document.documentElement.scrollLeft || document.body.scrollLeft;
			reObj.top -= document.documentElement.scrollTop || document.body.scrollTop;
		} else if (offParent != document.documentElement) {
			var offPos = _get(offParent).getPos();
			reObj.left -= offPos.left;
			reObj.top -= offPos.top;
		}
	}
	return reObj;
};//End of getPos

//获取鼠标事件发生时鼠标在目标对象上的偏移(实际上这个函数只在Opera中有用,因为EVENT已用fixEvent标准化了)
HTMLElement.prototype.getEvtOffset = (document.attachEvent)?function (evt) {
	return  {
		left:evt.offsetX+parseInt(this.clientLeft),
		top:evt.offsetY+parseInt(this.clientTop)
	};
}:function (evt) {
	return  {
		left:(evt.offsetX || evt.layerX),  //因为IE和Opera都用offsetX,但IE将对象边框忽略,而Opera不忽略,所以这一步还要过滤
		top:(evt.offsetY || evt.layerY)
	};
};


//有关className的一些简单方法
HTMLElement.prototype.hasClass  = function (cName) {
	return new RegExp("(\\b|^)"+cName+"(\\b|$)").test(this.className);
};
HTMLElement.prototype.addClass  = function (cName) {
	this.className += " "+cName;
	return this.className;
};
HTMLElement.prototype.delClass = function (cName) {
	var re = new RegExp("(\\b|^)"+cName+"(\\b|$)","g");
	this.className =  this.className.replace(re,"");
	return this.className;
};

/*一些在DOM行进的快捷方法*/
HTMLElement.prototype.clearWhiteSpace = function (obj) {	//清除空文本节点
	var obj = obj || this;
	if (!obj.tagName) return false;
	if (obj.noWhiteSpace || !obj.childNodes.length) return true;
	for (var i=0;i< obj.childNodes.length;i++) {
		var tmp = obj.childNodes[i];
		if (tmp.nodeType == 3 && (/^(\s|　)+$/).test(tmp.nodeValue)) tmp.parentNode.removeChild(tmp);
		else if (tmp.nodeType == 1) arguments.callee(tmp);
	}
	this.noWhiteSpace = true;
	return true;
};
HTMLElement.prototype.next = function (filter) { //filter为所需的节点类型值 ,元素为1,文本节点为3
	this.clearWhiteSpace(this.parentNode);
	var nextObj = this.nextSibling;
	if (!filter) filter =1; //默认只选择元素节点
	if (typeof filter=="string")  var prop = "tagName";
	else var  prop = "nodeType";
	while (nextObj && nextObj[prop] != filter) {
		nextObj = nextObj.nextSibling;
	}
	if (!nextObj) return null;
	return (nextObj.nodeType ==1)?_get(nextObj):nextObj;
};
HTMLElement.prototype.prev = function (filter) {
	this.clearWhiteSpace(this.parentNode);
	if (!filter) filter =1;
	if (typeof filter=="string") var prop = "tagName";
	else var prop = "nodeType";
	var prevObj =this.previousSibling;
	while (prevObj && prevObj[prop] != filter) {
		prevObj = prevObj.previousSibling;
	}
	if (!prevObj) return null;
	return (prevObj.nodeType == 1)?_get(prevObj):prevObj;
};
HTMLElement.prototype.seek = function (num) { //在元素内部行进,如要获得firstChild,则用obj.seek(0)
	if (this.childNodes.length) {
		this.clearWhiteSpace();
		if (Number(num) >= 0) return _get(this.childNodes[num]);
		else if (num == "last") return _get(this.childNodes[this.childNodes.length-1]);
	} else return null;
};
HTMLElement.prototype.before = function (nodeObj) {
	this.parentNode.insertBefore(nodeObj,this);
};
HTMLElement.prototype.after = function (nodeObj) {
	var n = this.nextSibling;
	if (n) this.parentNode.insertBefore(nodeObj,n);
	else this.parentNode.appendChild(nodeObj);
};
HTMLElement.prototype.del = function (obj) {
	obj = obj || this;
	return obj.parentNode.removeChild(obj);
};
HTMLElement.prototype.attr = function (name,value,obj) {
	obj = obj || this;
	name = {"class":"className","for":"htmlFor","float":"cssFloat","text":"cssText"}[name] || name;
	if (typeof value == "undefined") {
		if (obj.getAttribute) return obj.getAttribute(name);
		else return obj[name] || "";
	} else {
		if (obj.setAttribute) return obj.setAttribute(name,value);
		else return obj[name]=value;
	}
};
//获取元素默认样式
HTMLElement.prototype.getStyle = function (name,obj) {  //对于火狐和Opera,元素的一些值为auto时,将没有办法取出来,都是报告为0
	obj = obj || this;
	if (obj[name]) return obj[name];
	else if (document.defaultView && document.defaultView.getComputedStyle) {
		name = name.replace(/([A-Z])/g,"-$1").toLowerCase();
		var s = document.defaultView.getComputedStyle(obj,"");
		return s && s.getPropertyValue(name);
	}
	else return obj.currentStyle && obj.currentStyle[name];
};

function trans(start,finish,timeout,obj) { //用以处理针对style属性的CSS整数值渐变(匀速)效果,但不支持透明等具有兼容性问题的属性(如滤镜);
	var obj = (this.tagName)?this:obj;
	var props = [];
	for (var i in start) {
		if (!finish.hasOwnProperty(i) ||  !start.hasOwnProperty(i)) continue;
		props.push({
			start:parseInt(start[i]),
			finish:parseInt(finish[i]),
			name:i.toString(),
			unit:getUnit(start[i])
		});
	}
	props.each(exe);
	function exe(one) {
		var add = (one.finish - one.start)/timeout*50;
		for (var i=0;i< parseInt(timeout/50);i++) {
			(function () {
				var val =  one.start+add*i;
				setTimeout(function () {
					obj.style[one.name] = Math.round(val)+one.unit;
				},i*50);
			})();
		}
		//确保最终值为finish
		setTimeout(function () {
			obj.style[one.name] = one.finish+one.unit;
		},i*50);
	}
	function getUnit(str) {
		var re = /-?\d+\.?\d*/;
		return str.replace(re,"");
	}
}//End of trans



//设置元素透明度和透明渐变的函数
HTMLElement.prototype.opacityTrans = function (start,finish,timeout) {
	var obj = this;
	var add = (finish-start)/timeout*50;
	for (var i=0;i< parseInt(timeout / 50);i++) {
		(function () {
			var num = Math.round(start + add*i);
			setTimeout(function () {obj.setOpacity(num)},i*50);
		})();
	}
	setTimeout(function () {obj.setOpacity(finish)},i*50);
};
HTMLElement.prototype.setOpacity = (document.all)?function(num) { this.style.filter = "alpha(opacity="+num+")" }:function(num) { this.style.opacity = num/100 };

document.create = (document.createElementNS)?function (strTag) { return document.createElementNS("http://www.w3.org/1999/xhtml",strTag)}:function (strTag) {return document.createElement(strTag)};

/*End HTMLElement.prototype Var*/
function _get(strTag,met) {
	var obj;
	var root=(this.tagName)?this:document;
	if (typeof strTag == "string") {
		met = (!_get.getMethod[met])?"id":met;
		obj = _get.getMethod[met](strTag,root);
	}
	if (!obj && typeof strTag == "object") obj = strTag;
	if (!obj || !obj.tagName) return obj;
	if (HTMLElement.constructor == Function && obj.prototype != HTMLElement.prototype) {
		if (obj.length && !obj.tagName) {
			for (var i=0;i< obj.length;i++) {
				if (obj[i] && obj[i].tagName) inherit(obj[i],HTMLElement);
			}
		} else inherit(obj,HTMLElement.prototype);
		obj.prototype = HTMLElement.prototype;
	}
	return obj;
	function inherit(child,parent) {		//最简单、低级的方法继承函数(其实只是方法的克隆而已)
		for (var i in parent) {
			if (parent.hasOwnProperty(i) && parent[i]	&& parent[i].constructor == Function) {
				child[i]=parent[i];
			}
		}
		if (parent.prototype) inherit(child,parent.prototype);
		return child;
	}
}
_get.getMethod = {
	"id":function (strTag) {return _get(document.getElementById(strTag))},
	"t":function (strTag,root) {
		var partObj =[];
		var objs = root.getElementsByTagName(strTag);
		for (var i=0;i< objs.length;i++) {
			if (objs[i]) partObj.push(_get(objs[i]));
		}
		objs = null;
		return partObj;
	},
	"n":function (strTag) {
		var partObj =[];
		var objs = document.getElementsByName(strTag);
		for (var i=0;i< objs.length;i++) {
			if (objs[i]) partObj.push(_get(objs[i]));
		}
		objs =null;
		return partObj;
	},
	"c":(document.getElementsByClassName)?function (strTag,root) {
			var partObj=[];
			var objs;
			if (root.getElementsByClassName) objs= root.getElementsByClassName(strTag);
			if (!objs) return null;
			for (var i=0;i< objs.length;i++) {
				if (objs[i]) partObj.push(objs[i]);
			}
			objs=null;
			return partObj;
		}:function (strTag,root) {
			var partObj = [];
			var tmp = root.getElementsByTagName("*");
			var re = new RegExp("(^|\\b)"+strTag+"($|\\b)");
			for (var i=0;i< tmp.length;i++) {
				if (tmp[i] && tmp[i].className && re.test(tmp[i].className)) {
					partObj.push(_get(tmp[i]));
				}
			}
			tmp = null;
			return partObj;
		}
};
//End of _get

/*Start of DOMover*/
function DOMover(handle,obj) {
	obj = obj || handle;
	this.handle = (!!handle.prototype)?handle:_get(handle);
	this.handle.owner = this;
	this.obj =(!!obj.prototype)?obj:_get(obj);
	addEvent(this.handle,"mousedown",DOMover.regFn);
}
DOMover.prototype.setLimit = function (limitObj) {
	var l=t=-Infinity;
	var r=b=Infinity;
	if (limitObj.tagName || limitObj == window) {
		this.limitArea = function () {
			var de = document.documentElement;
			var bd = document.body;
			if (limitObj.tagName) {
				limitObj = _get(limitObj);
				var pos= limitObj.getPos();
			}
			var mover = DOMover.currentMover;
			var handle = mover.handle;
			var obj =mover.obj;
			var wid = Math.max(obj.offsetWidth+((handle.objOffset)?handle.objOffset.left:0),handle.offsetWidth);
			var hig = Math.max(obj.offsetHeight+((handle.objOffset)?handle.objOffset.top:0),handle.offsetHeight);
			return (limitObj.tagName)?{
				left:pos.left,
				right: pos.left+limitObj.clientWidth-wid,
				top: pos.top,
				bottom:pos.top+limitObj.clientHeight-hig
			}:{
				left: de.scrollLeft || bd.scrollLeft,
				right : (de.scrollLeft || bd.scrollLeft) + (window.innerWidth || de.clientWidth || bd.clientWidth)-wid,
				top : de.scrollTop || bd.scrollTop,
				bottom : (de.scrollTop || bd.scrollTop)+ (window.innerHeight || de.clientHeight || bd.clientHeight)-hig
			};
		};
	} else {
		l =limitObj.left;
		r = limitObj.right;
		t= limitObj.top;
		b = limitObj.bottom;
		this.limitArea = function () {
			return {
				left:l,
				right:r,
				top:t,
				bottom:b
			};
		};
	}
};
DOMover.prototype.setMovingClass = function (hc,oc) {
	this.handle.movingClass = hc || "";
	this.obj.movingClass = oc || hc || "";
};
DOMover.prototype.limitArea = function () {
	return  {
		left :  -Infinity,
		top :  -Infinity,
		right:Infinity,
		bottom:Infinity
	}
};
DOMover.prototype.free = function () {
	delEvent(this.handle,"mousedown",DOMover.regFn);
	delEvent(this.handle,"mousemove",DOMover.move);
	delEvent(this.handle,"mouseup",DOMover.clear);
	this.handle.evtOffset = null;
	this.handle.objOffset = null;
	this.handle.movingClass = null;
	this.obj.movingClass = null;
	for (var i in this) {
		delete this[i];
	}
};
DOMover.regFn = function (evt) {
	var handle = evt.target;
	if (!handle.tagName ||  !handle.owner || handle.owner.constructor != DOMover) return false;
	evt.preventDefault();
	DOMover.currentMover = handle.owner;
	if (handle.setCapture)	{
		handle.setCapture();//不知道为什么,在IE中设了setCapture,内存占用率就降下来了
		addEvent(handle,"losecapture",DOMover.clear);
	}
	//else if (window.captureEvents) {
		//window.captureEvents(handle);  //火狐中设了captureEvents,内存泄露也解决了
		//addEvent(window,"blur",DOMover.clear);
	//}
	handle.evtOffset = handle.getEvtOffset(evt);
	if (handle.movingClass) handle.addClass(handle.movingClass);
	var obj = handle.owner.obj;
	if (obj!= handle) {
		var handlePos = handle.getPos();
		var objPos = obj.getPos();
		var objOffset = {
			left : objPos.left-handlePos.left,
			top : objPos.top - handlePos.top
		};
		handle.objOffset = objOffset;
		if (obj.movingClass ) obj.addClass(obj.movingClass);
	}
	addEvent(document,"mousemove",DOMover.move);
	addEvent(document,"mouseup",DOMover.clear);
	//当鼠标按下时取消选中内容
	if (document.selection && document.selection.empty) document.selection.empty();  //IE
	else if (window.getSelection) window.getSelection().removeAllRanges(); //火狐
	return false;
};//End of DOMover.regFn
DOMover.clear = function (evt) {
	evt.preventDefault();
	delEvent(document,"mousemove",DOMover.move);
	delEvent(document,"mouseup",DOMover.clear);
	var handle = DOMover.currentMover.handle;
	var obj = handle.owner.obj;
	if (handle.movingClass) handle.delClass(handle.movingClass);
	if (obj.movingClass) obj.delClass(obj.movingClass);
	if (handle.releaseCapture) {
		delEvent(handle,"losecapture",DOMover.clear);
		handle.releaseCapture();
	} else if (window.releaseEvents) {
		delEvent(window,"blur",DOMover.clear);
		//window.releaseEvents(handle);
	}
	DOMover.currentMover = null;
	return false;
};//End of DOMover.clear
DOMover.move = function (evt) {
	var mover = DOMover.currentMover;
	if (!mover) return false;
	var handle = mover.handle;
	var obj = mover.obj;
	var des = {
		left :evt.pageX-handle.evtOffset.left,
		top:evt.pageY-handle.evtOffset.top
	};
	var limit = mover.limitArea();
	if (obj != handle) {
		var objDes = {
			left:des.left+handle.objOffset.left,
			top:des.top+handle.objOffset.top
		};
		if (des.left.between(limit.left,limit.right) && objDes.left.between(limit.left,limit.right)) {
			obj.style.left=objDes.left+"px";
			handle.style.left=des.left+"px";
		}
		if (des.top.between(limit.top,limit.bottom) && objDes.top.between(limit.top,limit.bottom)) {
			obj.style.top=objDes.top+"px";
			handle.style.top=des.top+"px";
		}
	} else {
		if (des.left.between(limit.left,limit.right)) handle.style.left=des.left+"px";
		if (des.top.between(limit.top,limit.bottom)) handle.style.top=des.top+"px";
	}
	return false;
};//End of DOMover.move
/*End of DOMover*/


function getCookie(name) {	//对于ASP中的cookie字典可以用相同的方式获取 -> getCookie("name1")("name2")  --但是只能有一两层,超过两层就不解析了,反正也没那个必要
	function temp() {return false}
	temp.toString = function () {return ""};
	if (document.cookie == "") return temp;
	var cookies = document.cookie.split("; "); //这里是一个分号加一个空格!
	for (var i=0;i< cookies.length;i++) {
		var content =cookies[i].split("=");
		if (content[0] != name) continue;
		if (content.length == 2) {
			temp.toString = function () { return content[1].toString().safeCode(true) };
			return temp;
		}
		if (content.length > 2) {
			content = cookies[i].substr(cookies[i].indexOf("=")+1).toString().safeCode(true);
			var returnFn = function (childName) {
				var children = content.split("&");
				for (var i=0;i< children.length;i++) {
					var child = children[i].split("=");
					if (child[0]==childName) return child[1];
				}
				return false;
			};
			returnFn.toString = function() {
				return content;
			};
			return returnFn;
		}
	}
	return temp;
}
function setCookie(name,value,expires) {  //这个也是可以设置ASP中的字典cookie的,只是方法不一样----->setCookie("parent;childName","childValue")--对应取的方法就是getCookie("parent")("childName")--->因为cookie中一般用不到分号(也不允许直接用),所以用它来做分隔符
	if (typeof value=="undefined") value="";
	value = value.toString().safeCode();
	if (name.indexOf(";") != -1) {
		name = name.split(";");
		if (name.length >2) return false;
		var cookies = document.cookie.split("; ");
		for (var i=0;i< cookies.length;i++) {
			var content = cookies[i].split("=");
			if (content.length > 2 && content[0] == name[0]) {
				content = cookies[i].substr(cookies[i].indexOf("=")+1);
				content = content.split("&");
				var tempstr=[];
				for (var j=0;j< content.length;j++) {
					var child = content[j].split("=");
					if (child[0] == name[1]) child[1]=value;
					tempstr.push(child.join("="));
				}
				tempstr = tempstr.join("&");
				if (tempstr.indexOf(name[1]+"=")== -1) tempstr += "&"+name[1]+"="+value;
				value = tempstr;
				name=name[0];
				break;
			}
		}
		if (name.constructor == Array) { // 在火狐浏览器中(以及所有Gecko,KHTML核心的浏览器,包括Chrome)有个很奇怪的地方,令人郁闷的类型转换:当对字符串使用数组下标时,火狐浏览器会自动将其转换一个包含元字符串每个字符的数组;如var str = "abcdef",str[0]为a,str[1]为b
			value = name[1]+"="+value;
			name=name[0];
		}
	}
	if (typeof expires =="undefined")  document.cookie = name+"="+value;
	else if (parse(expires)) document.cookie = name+"="+value+";expires="+parse(expires).toUTCString();
	else {
		expires = new Date();
		expires.setMonth(expires.getMonth()+6);
		document.cookie = name+"="+value+";expires="+expires.toUTCString();
	}
	return false;
}


function myAjax(url,callBack,method) {
	var newXHR;
	try { newXHR = new XMLHttpRequest; }
	catch(e) {
		try { newXHR = new ActiveXObject("Msxml3.XMLHTTP"); }
		catch(e) {
			try { newXHR = new ActiveXObject("Msxml2.XMLHTTP"); }
			catch(e) {
				try { newXHR = new ActiveXObject("Microsoft.XMLHTTP"); }
				catch(e) {
					alert("Error : "+e.number+"\n"+e.description);
					//showMyStatus( '很抱歉,您的浏览器不支持本网站的一些高级功能(XMLHttpRequest),这些功能可能对您很有帮助,您可以点击<a href="http://www.google.cn/search?ie=utf-8&oe=UTF-8&hl=zh-CN&q='+encodeURI("最新版火狐浏览器")+'">这里</a>下载支持该功能的现代浏览器!');
					return false;
				}
			}
		}
	}
	if (!method) method="get";
	else if (method !="post") method="put";
	newXHR.open(method,url,true);
	//newXHR.isTimeOut = false; //在IE中不允许对ActiveXObject 添加自定义属性
	//setTimeout(function () {
	//	if (newXHR) newXHR.isTimeOut = true;
	//},5000);
	if (method=="post") newXHR.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	newXHR.onreadystatechange = function() {
		if (newXHR.readyState == 4 ) {
			var win = window.open("","win","width=400,height=400,scrollbars=yes");
			win.document.write(newXHR.responseText);
		}
		if (newXHR.readyState == 4 && newXHR.status==200) {
			callBack(newXHR);
		} else if (location.protocol == "file:" && newXHR.readyState == 4) {
			//showMyStatus("本地请求成功!");
			callBack(newXHR);
		}/* else if (newXHR.status == 304) {
			//showMyStatus("文档未修改!");
		} else if (newXHR.status > 200 && newXHR.status <= 300) {
			//showMyStatus("A little Error ! HTTP "+newXHR.status+" 错误!");
			callBack(newXHR);
		} else if (newXHR.isTimeOut) {
			//showMyStatus("请求超时!一分钟后重试!");
		} else if (newXHR.onerror && newXHR.onerror.constructor == Function) {
			newXHR.onerror(newXHR);
		}  else {
			//showMyStatus("未知错误!请求失败!");
		}*/
	};
	if (method=="get") newXHR.send(null);
	return newXHR;
}


function addFav(address,name) {
	if (window.sidebar) return window.sidebar.addPanel(name,address,""); //判断顺序不能错,因为火狐下window也有external属性,但没有addFavorite方法,而在IE中判断external的addFavorite方法时会出错
	else if (window.external) return window.external.addFavorite(address,name);
	return false;
}

function setHomePage(obj,url){
	try{
		obj.style.behavior='url(#default#homepage)';
		obj.setHomePage(url);
	}
	catch(e){
		if(window.netscape) {
			try { netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect"); }  
			catch (e) { alert("抱歉！您的浏览器不支持直接设为首页。您须要自己设置!"); }
			var prefs = Components.classes['@mozilla.org/preferences-service;1'].getService(Components.interfaces.nsIPrefBranch);
			prefs.setCharPref('browser.startup.homepage',url);
		}
	}
}