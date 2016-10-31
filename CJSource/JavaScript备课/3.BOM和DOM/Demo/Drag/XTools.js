if (!String.prototype.ltrim) {
	//ltrim 去除字符串左边的空格
	((String.prototype.ltrim = function () {return this.replace(arguments.callee.re,"");}).re = new RegExp()).compile("^\\s+","gi");
} else {
	throw new Error("String.prototype.ltrim方法已存在!");
}
if (!String.prototype.rtrim) {
	//rtrim 去除字符串右边的空格
	((String.prototype.rtrim = function () {return this.replace(arguments.callee.re,"");}).re = new RegExp()).compile("\\s+$","gi");
} else {
	throw new Error("String.prototype.rtrim方法已存在!");
}
if (!String.prototype.ftrim) {
	//frim 去除字符串中所有的空格
	((String.prototype.ftrim = function () {return this.replace(arguments.callee.re,"");}).re = new RegExp()).compile("\\s","gi");
} else {
	throw new Error("String.prototype.ftrim方法已存在!");
}
if (!String.prototype.trim) {
	//trim 去除字符串两边的空格
	String.prototype.trim = function () {return this.ltrim().rtrim();};
} else {
	throw new Error("String.prototype.trim方法已存在!");
}
if (!String.prototype.repeat) {
	//repeat 重复一个字符串，num参数指定重复次数
	String.prototype.repeat = function (num) {return (new Array(num+1)).join(this);};
} else {
	throw new Error("String.prototype.repeat方法已存在!");
}
if (!String.prototype.isEmail) {
	//isEmail 判断字符串是否是有效的邮箱地址，返回true表示正确
	((String.prototype.isEmail = function () {return arguments.callee.re.test(this);}).re=new RegExp()).compile("^\\w{1,15}(?:@(?!-))(?:(?:[a-z0-9-]*)(?:[a-z0-9](?!-))(?:\\.(?!-)))+[a-z]{2,4}$","i");
} else {
	throw new Error("String.prototype.isEmail方法已存在!");
}
if (!String.prototype.reverse) {
	//reverse 将字符串反转
	String.prototype.reverse = function () {return this.split("").reverse().join("");};
} else {
	throw new Error("String.prototype.reverse方法已存在!");
}
if (!String.prototype.nl2br) {
	//nl2br 将字符串中的换行符替换成<br />
	((String.prototype.nl2br = function () {return this.replace(arguments.callee.re,"<br />");}).re=new RegExp()).compile("\\r\\n|\\n\\r|\\n|\\r","g");
} else {
	throw new Error("String.prototype.nl2br方法已存在!");
}
if (!String.prototype.encodeHTML) {
	//encodeHTML 将字符串中的HTML特殊字符替换成实体引用
	String.prototype.encodeHTML = function () {return this.replace("&","&amp;").replace("<","&lt;").replace(">","&gt;").replace("\"","&quote;");};
} else {
	throw new Error("String.prototype.encodeHTML方法已存在!");
}
if (typeof window.XT != "undefined" ) {
	throw new Error("命名空间XT已存在!");
}
function XT() {//命名空间开始
	return document.getElementById(arguments[0]);
	//Event开始
	function addEvent(obj,evtype,fn,useCapture) {
		if (obj.addEventListener) {
			obj.addEventListener(evtype,fn,!!useCapture);
		} else {
			if (!fn.__EventID) {fn.__EventID = addEvent.__EventHandlesCounter++;}
			if (!obj.__EventHandles) {obj.__EventHandles={};}
			if (!obj.__EventHandles[evtype]) {
				obj.__EventHandles[evtype]=[];
				if (obj["on"+evtype]) {
					(obj.__EventHandles[evtype][0]=obj["on"+evtype]).__EventID=0;
				}
				obj["on"+evtype]=execEventHandles;
			}
			obj.__EventHandles[evtype][fn.__EventID]=fn;
		}
	}
	addEvent.__EventHandlesCounter=1;
	function execEventHandles(evt) {
		if (!this.__EventHandles) {return true;}
		evt =fixEvent(evt || window.event);
		var fns = this.__EventHandles[evt.type];
		for (var i=0;i< fns.length;i++) {
			if (fns[i] instanceof Function) {fns[i].call(this,evt);}
		}
	};
	function fixEvent(evt) {
		if (!evt.target) {
			evt.target = evt.srcElement;
			evt.preventDefault =preventDefault;
			evt.layerX = evt.offsetX;
			evt.layerY = evt.offsetY;
			if (evt.type == "mouseover") {
				evt.relatedTarget = evt.fromElement;
			} else if (evt.type=="mouseout") {
				evt.relatedTarget = evt.toElement;
			}
			evt.stopPropagation =stopPropagation;
		}
		return evt;
	}
	function preventDefault() {
		this.returnValue = false;
	};
	function stopPropagation() {
		this.cancelBubble = true;
	};
	function delEvent(obj,evtype,fn,useCapture) {
		if (obj.removeEventListener) {
			obj.removeEventListener(evtype,fn,!!useCapture);
		} else {
			if (obj.__EventHandles) {
				var fns = obj.__EventHandles[evtype];
				if (fns) {
					delete fns[fn.__EventID];
				}
			}
		}
	};
	window.XT.addEvent = addEvent;
	window.XT.delEvent = delEvent;
	//Event结束
	
	//Drag开始
	function Drag(obj,handle,cn) {
		handle.div = obj; 
		addEvent(handle,"mousedown", Drag.drag);
		handle.movingClassName = cn;
	}
	Drag.curObj = {};
	Drag.drag =  function (evt) {
		if (document.selection && document.selection.empty) {document.selection.empty();}  //IE
		else if (window.getSelection) {window.getSelection().removeAllRanges(); }//火狐
		this.div.className = this.movingClassName ;
		addEvent(document,"mouseup", Drag.drop);
		Drag.curObj = this.div;
		addEvent(document,"mousemove" ,Drag.moveDiv);
		this.div.offset = {
			x:evt.layerX,
			y:evt.layerY
		};
	};
	Drag.drop = function drop(evt) {
		delEvent(document,"mouseup",Drag.drop);
		delEvent(document,"mousemove",Drag.moveDiv);
		Drag.curObj.className = "";
	};
	Drag.moveDiv = function (evt) {
		Drag.curObj.style.left = evt.clientX-Drag.curObj.offset.x+"px";
		Drag.curObj.style.top = evt.clientY-Drag.curObj.offset.y+"px";
	};
	window.XT.Drag = Drag;
	//Drag结束
	
	
};//命名空间结束
