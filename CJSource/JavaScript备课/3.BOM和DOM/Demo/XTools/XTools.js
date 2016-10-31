/*
XTools Library v1.0 2009-7-1
命名空间:XT
*/


{/*
扩展String类的一些方法
*/
//ltrim清除字符串左边的空白字符
((String.prototype.ltrim = function () {return this.replace(arguments.callee.re,"")}).re = new RegExp()).compile("^\\s+","gi");

//rtrim清除字符串右边的空白字符
((String.prototype.rtrim = function () {return this.replace(arguments.callee.re,"")}).re = new RegExp()).compile("\\s+$","gi");

//ftrim清除字符串中所有空白字符
((String.prototype.ftrim = function () {return this.replace(arguments.callee.re,"")}).re = new RegExp()).compile("\\s","gi");

//trim清除字符串两边的空白字符
String.prototype.trim = function () {return this.ltrim().rtrim()};

//repeat用于重复一个字符串,传入一个数字表示重复次数
String.prototype.repeat = function (num) {
	return (new Array(num+1)).join(this);
};

//新的replace可以批量替换,在替换前先保存以前方法的引用
String.prototype.__Replace = String.prototype.replace;
String.prototype.replace = function (a,b) {
	var reStr=this;
	if (b instanceof Array && a instanceof Array) {
		//使用批量替换时需传入两个数组
		for (var i=0;i< a.length;i++) {
			reStr = reStr.__Replace(a[i],b[i]?b[i]:"");
		}
	} else {
		reStr = reStr.__Replace(a,b);
	}
	return reStr;
};
//encodeHTML将字符串中HTML特殊字符转换成实体引用
String.prototype.encodeHTML = function () {
	var specailChars=[/&/g,/</g,/>/g,/\"/g,/\'/,/\%/g,/\?/g];
	var entityRefs=["&amp;","&lt;","&gt;","&quote;","&#39;","&#37;","&#63;"];
	return this.replace(specailChars,entityRefs);
};
}//结束对ECMAScript内置对象String原型的扩展


if (typeof window.XT!="undefined") {//查看是否已经存在全局变量XT
	throw new Error("命名空间XT已经被使用");
}
//声明命名空间XT
var XT = function () {
	//将XT创建为一个函数,作为DOM选择器入口,而在这在里面,是XT.$处理了所有的事情
	return arguments.callee.$.apply(this,arguments);
};
//XT声明结束

(function (NS) {
/*
命名空间开始,NS(NameSpace)表示命名空间对象的引用(window.XT)
将命名空间对象 window.XT作为参数传入进去
这样,即使要修改命名空间名称,内部代码仍不受影响
*/

/*
将undefined,window作为局部变量
这样,当函数执行时,查找undefined,window这些内置的对象时不用跑出当前函数作用域
在全局作用域里window是不能覆盖的,但在局部作用域里可以,注意这里的this也是指的全局window
var undefined只声明而没有赋值,它的值就是undefined
*/
var undefined,window=this;

//cloneProperty用于从from对象上克隆属性到desc上面
function cloneProperty(desc,from) {
	if (desc.length && typeof desc=="object") {//当desc为集合时可以批量克隆
		for (var i=0,len=desc.length;i<len;i++) {
			cloneProperty(desc[i],from);
		}
	} else {
		for (var i in from) {
			if (from.hasOwnProperty(i)) {
				desc[i]=from[i];
			}
		}
	}
	return desc;
}
NS.cloneProperty = cloneProperty;

//浏览器检测
var Browser = {
	//IE与Opera都具有attachEvent方法
	IE:!!(navigator.userAgent.indexOf('MSIE') > -1 && window.attachEvent && !window.opera),
	Opera:!!window.opera,//但Opera具有opera全局对象
	KHTML:navigator.userAgent.indexOf('KHTML') > -1,
	//使用KHTML引擎的浏览器会在userAgent字符串中包含字符串"like Cecko"
	Gecko:navigator.userAgent.indexOf('Gecko') > -1 && navigator.userAgent.indexOf('KHTML') == -1
};
NS.Browser = Browser;



/*
将方法添加到HTMLElement的原型上面可以使所有DOM元素继承该方法(虽然这种做法不够严谨,但确实很方便)
IE不支持HTMLElement原型,当不存在时创建,在完整支持HTML DOM的浏览器,HTMLElement.prototype上的方法会自动被DOM元素继承
但在IE中需要手动从HTMLElement.prototype上克隆属性
*/
if (typeof HTMLElement=="undefined") {
	window.HTMLElement =function () {};
	//IE下修改内置的获取DOM元素的方法,在修改前先保存原来方法的引用
	document.__getElementById = document.getElementById;
	document.__getElementsByTagName = document.getElementsByTagName;
	document.__getElementsByName = document.getElementsByName;
	document.getElementById = function (id) {
		return $.getObj(document.__getElementById(id));
	};
	document.getElementsByTagName = function (tag) {
		return $.getObj(document.__getElementsByTagName(tag));
	};
	document.getElementsByName = function (name) {
		return $.getObj(document.__getElementsByName(name));
	};
	document.getElementsByClassName = function () {//为IE创建一个可通过类名获取元素的方法
		return $C.apply(this,arguments);
	};
}
var HEP = HTMLElement.prototype;//为HTMLElement.prototype创建短的引用



{//$是一个DOM元素选择器
function $() {
	var args = arguments;
	if (!args.length) {
		return document;//没有任何参数时返回document
	}
	if (args.length==1) {
		//只有一个字符串参数时应该是传入元素的ID,然后将这个元素返回
		if (typeof args[0]=="string") {
			return $.getObj(document.getElementById(args[0]));
		}
		//当是一个对象时交给$.getObj去处理
		if (typeof args[0]=="object") {
			return $.getObj(args[0]);
		}
	}
	//当有多个参数时,则是返回tagName与className都符合要求的元素
	var tag = args[0];//第一个参数是tagName
	var cn = args[1];//第二个参数是className
	var context = args[2] || this.tagName?this:document;
	var allTags = context.getElementsByTagName(tag);
	var re = new RegExp();
	var reArr=[];
	re.compile("\\b"+cn+"\\b");
	var len = allTags.length;
	for (var i=0;i<len;i++) {
		if (re.test(allTags[i].className)) {
			reArr.push($.getObj(allTags[i]));
		}
	}
	return reArr;
}
//$.getObj作为当$的参数只有一个并且是对象时,判断是否需要从HTMLElement.prototype上克隆属性
$.getObj = (HTMLElement.constructor == Function)?function (obj) {
	if (!obj) {return null;}
	if (obj.length && !obj.nodeType) {//obj为集合时实现批量克隆
		for (var i=0,len=obj.length;i<len;i++) {
			$.getObj(obj[i]);
		}
		return obj;
	}
	if (obj.nodeType != 1) {return obj;}//假如是元素节点,则给其扩展一些方法,文本节点不添加
	//在不支持HTMLElement的浏览器中,HTMLElement是手动创建的,在IE中时$.getObj就会使用此函数
	if (obj.constructor == HTMLElement) {
		return obj;//复制过属性了,就不用再复制了
	}
	obj.constructor = HTMLElement;//设置一个标记表明已经克隆过属性了
	return cloneProperty(obj,HEP);
}:function (obj) {
	//在支持HTMLElement的浏览器中会自动继承HTMLElement.prototype上的属性,所以只要将其返回即可
	return obj;
};
NS.$=HEP.$= $;

//通过className来获取元素的方法
var $C = (HTMLElement.constructor != Function)?function (cn) {//火狐具有getElementsByClassName方法,可以直接使用
	return this.getElementsByClassName(cn);
}:function (cn) {
	var allTags = this.getElementsByTagName("*");
	var reArr = [];
	var re = new RegExp();
	re.compile("\\b"+cn+"\\b");
	for (var i=0,len=allTags.length;i<len;i++) {
		if (re.test(allTags[i].className)) {
			reArr.push(allTags[i]);
		}
	}
	return reArr;
};
NS.$C=HEP.$C= $C;//这样可以在每个DOM对象上调用该方法,并且相应的context也会变成该DOM对象

//$T是通过HTML的tagName来获取元素
function $T(tag) {
	return $.getObj(this.getElementsByTagName(String(tag).toUpperCase()));
}
NS.$T=HEP.$T = $T;
//$N方法根据name来选择元素
function $N(name) {
	return this.getElementsByName(name);
}
NS.$N=HEP.$N = $N;
//$A根据属性名及属性值选择元素
function $A(attrName,attrVal) {
	var allTags = this.$T("*");
	var reArr=[];
	for (var i=0,len=allTags.length;i<len;i++) {
		if (allTags[i] && allTags[i].attr(attrName)==attrVal) {
			reArr.push(allTags[i]);
		}
	}
	return reArr;
}
NS.$A = HEP.$A = $A;
}//选择器结束

{//Event Start
function addEvent(evtype,fn,useCapture) {
	var obj = this;//如果不是给当前对象添加事件处理,可以使用addEvent.call(...)
	if (typeof useCapture!="undefined" && obj.addEventListener) {
		obj.addEventListener(evtype,fn,!!useCapture);
	} else {
		if (!fn.__EventID) {//使用一个自动增长的标识来标志每个函数
			fn.__EventID =addEvent.__EventCounter++;//这个ID是唯一的
		}
		if (!obj.__EventHandles) {
			//__EventHandles属性用来保存所有事件处理函数的引用
			obj.__EventHandles={};
		}
		//将函数按事件类型分类存放在obj.__EventHandles里面
		if (!obj.__EventHandles[evtype]) {
			obj.__EventHandles[evtype]=[];
			if (obj["on"+evtype]) {//将之前的注册的函数添加到0位
				obj.__EventHandles[evtype][0]=obj["on"+evtype];
			}
			obj["on"+evtype]=addEvent.__ExecFns;
		}
		obj.__EventHandles[evtype][fn.__EventID]=fn;
	}
	return obj;
}
addEvent.__EventCounter = 1;//给事件处理函数进行自动编号,0位另作它用
//addEvent.__ExecFns 将在事件发生时执行对象__EventHandles下面对应集合中的函数,并把this与事件对象传进去
addEvent.__ExecFns = function (evt) {
	evt = addEvent.__FixEvent(evt || window.event);//对事件对象标准化
	if (!this.__EventHandles || !this.__EventHandles[evt.type]) {return false;}
	var fns = this.__EventHandles[evt.type];
	var len=fns.length;
	for (var i=0;i< len;i++) {
		if (fns[i]) {
			fns[i].call(this,evt);//在函数内部的this将会指向注册到的对象上,而evt也是第个参数
		}
	}
};
//addEvent.__FixEvent用于将事件对象标准化,以使在IE与FF中使用同一种方式访问事件对象的属性
addEvent.__FixEvent = function (evt) {
	if (!evt.target) {
		evt.target = evt.srcElement;
		evt.preventDefault = addEvent.__FixEvent.preventDefault;
		evt.stopPropagation = addEvent.__FixEvent.stopPropagation;
		if (evt.type == "mouseover") {
			evt.relatedTarget = evt.fromElement;
		} else if (evt.type =="mouseout") {
			evt.relatedTarget = evt.toElement;
		}
		evt.charCode =  (evt.type=="keypress")?evt.keyCode:0;
		evt.eventPhase = 2;//IE仅工作在冒泡阶段
		evt.timeStamp = (new Date()).getTime();//仅将其设为当前时间
		evt.pageX = evt.clientX+document.documentElement.scrollLeft;
		evt.pageY = evt.clientY+document.documentElement.scrollTop;
	}
	if (evt.offsetX) {//IE与Opera都会有offsetX属性,与其它浏览器不同的是,它们的offset属性不包含边框
		evt.layerX = evt.offsetX+evt.target.clientLeft;
		evt.layerY = evt.offsetY+evt.target.clientTop;
	}
	return evt;
};
addEvent.__FixEvent.preventDefault =function () {//阻止浏览器默认行为
	this.returnValue = false;//这里的this指向了某个事件对象，而不是__FixEvent
};
addEvent.__FixEvent.stopPropagation =function () {//停止事件传播
	this.cancelBubble = true;
};
//delEvent用于从对象上删除事件处理函数
function delEvent(evtype,fn,useCapture) {
	var obj = this;
	if (typeof useCapture!="undefined" && obj.addEventListener) {
		obj.removeEventListener(evtype,fn,!!useCapture);
	} else {
		if (obj.__EventHandles && obj.__EventHandles[evtype]) {
			delete obj.__EventHandles[evtype][fn.__EventID];
		}
	}
	return obj;
}
NS.addEvent=HEP.addEvent = addEvent;
NS.delEvent =HEP.delEvent  =delEvent;
//DOMContentLoaded事件
function addLoadEvent(fn) {
	if (addLoadEvent.__Done) {
		fn();
		return NS;
	}
	if (!fn.__EventID) {
		fn.__EventID=addEvent.__EventCounter++;
	};
	addLoadEvent.__EventHandles[fn.__EventID] = fn;
	if (addLoadEvent.__Added) {
		return NS;
	}
	if (document.addEventListener) {
		document.addEventListener("DOMContentLoaded", addLoadEvent.__ExecFns, false);
	} else if (Browser.IE) {
		/*@cc_on @*/
		/*@if (@_win32)
			document.write("<script id=\"__XT_IE_Load\" defer=\"defer\" src=\"javascript:void(0)\"><\/scr"+"ipt>");
			addLoadEvent.__IEScript = document.getElementById("__XT_IE_Load");
			addLoadEvent.__IEScript.onreadystatechange = function() {
				if (this.readyState == "complete") {
					addLoadEvent.__ExecFns();
				}
			};
		/*@end @*/
	} else if (Browser.KHTML) {
		addLoadEvent.__KTimer = setInterval(function () {
			if (/loaded|complete/.test(document.readyState)) {
				addLoadEvent.__ExecFns();
			}
		},10);
	}
	addEvent.call(window,"load",addLoadEvent.__ExecFns);
	addLoadEvent.__Added = true;
	return NS;
}
addLoadEvent.__EventHandles=[];
addLoadEvent.__ExecFns = function () {
	if (addLoadEvent.__Done) {return;}
	addLoadEvent.__Done=true;
	var fns=addLoadEvent.__EventHandles;
	for (var i=0,len=fns.length;i<len;i++) {
		if (fns[i]) {
			fns[i]();
			delete fns[i];
		}
	}
	if (addLoadEvent.__IEScript) {
		addLoadEvent.__IEScript.onreadystatechange = null;
		addLoadEvent.__IEScript.del();
		addLoadEvent.__IEScript=null;
	}
	if (addLoadEvent.__KTimer) {
		clearInterval(addLoadEvent.__KTimer);
	}
};
function delLoadEvent(fn) {
	delete addLoadEvent.__EventHandles[fn.__EventID];
	return NS;
}
NS.addLoadEvent = addLoadEvent;
NS.delLoadEvent = delLoadEvent;
}//Event End

{//Style Start
//getRealStyle 用以获取元素的CSS定义样式的实际值
function getRealStyle(name) {
	obj = this;
	if (obj.style[name]) {
		return obj.style[name];
	} else if (document.defaultView && document.defaultView.getComputedStyle) {
		name = name.replace(/([A-Z])/g,"-$1").toLowerCase();
		var s = document.defaultView.getComputedStyle(obj,"");
		return s && s.getPropertyValue(name);
	} else {
		return obj.currentStyle && obj.currentStyle[name];
	}
}
NS.getRealStyle = HEP.getRealStyle = getRealStyle;
//getStyleSheet用来获取页面的样式表
function getStyleSheet() {
	if (!document.styleSheets) {
		return null;
	}
	var args = arguments;
	var sheets= document.styleSheets;
	if (typeof args[0]=="number") {
		return getStyleSheet.__FixStyleSheet(sheets[args[0]]);
	}
	var property = "";
	if (String(args[0]).indexOf(".")>-1) {
		property="href";
	} else {
		property="id";
	}
	for (var i=0,len = sheets.length;i<len;i++) {
		if (sheets[i] && sheets[i][property]==args[0]) {
			return sheets[i];
		}
	}
}
getStyleSheet.__FixStyleSheet = function (sheet) {
	if (sheet.addRule) {//IE
		sheet.ownerNode =sheet.owningElement;
		sheet.cssRules = sheet.rules;
		sheet.insertRule = getStyleSheet.__insertRule;
		sheet.deleteRule = getStyleSheet.__deleteRule;
	}
	return sheet;
};
getStyleSheet.__insertRule = function (cssText,index) {
	var arr =arguments.callee.re.exec(cssText);
	var selector = String(arr[1]).trim();
	var declarations = String(arr[2]).trim();
	this.addRule(selector,declarations,index);
	return this;
};
getStyleSheet.__deleteRule=function (index) {
	this.removeRule(index);
};
(getStyleSheet.__insertRule.re =new RegExp()).compile("([^{]+)(\\{[^}]+\\})","i");
NS.getStyleSheet=getStyleSheet;
//setStyle方法可以一次给元素style属性增加多个CSS
function setStyle(cssObj,obj) {
	obj = this;
	for (var i in cssObj) {
		if (cssObj.hasOwnProperty(i)) {
			obj.style[i]=cssObj[i];
		}
	}
	return obj;
}
NS.setStyle = HEP.setStyle = setStyle;
function hide() {
	this.style.display="none";
}
NS.hide = HEP.hide = hide;
function show() {
	this.style.display = "block";
}
NS.show = HEP.show = show;
}//Style End

{//Node Start
//attr当只传入属性名时返回属性值,当传入属性名与属性值时则设置对象的属性值
function attr(name,val) {
	var args = arguments;
	var quiksName = {"class":"className","for":"htmlFor","float":"cssFloat","text":"cssText"}[name] || name;
	if (args.length===1)  {
		if (this.getAttribute) {
			return this.getAttribute(name);
		} else {
			return this[quiksName] || "";
		}
	} else if (args.length===2) {
		if (this.setAttribute) {
			this.setAttribute(name,val)
		} else {
			this[quiksName]=val;
		}
	}
	return this;
}
NS.attr=HEP.attr = attr;
//clearSpaceNode 清除空文本节点
function clearSpaceNode() {
	if (this.__NoWhiteSpace) {return;}
	var obj = this.firstChild;
	var tmp;
	if (!obj) {return;}
	do {
		tmp = obj.nextSibling;
		if (obj.nodeType===3 && !arguments.callee.re.test(obj.nodeValue)) {
			this.removeChild(obj);
		}
		obj = tmp;
	} while (obj)
	obj=tmp=null;
	this.__NoWhiteSpace =true;//设置一个标记表明已经清理过空文本节点了
}
(clearSpaceNode.re = new RegExp()).compile("\\S","i");
NS.clearSpaceNode=clearSpaceNode;
//walk方法获取节点的兄弟节点,默认获取元素节点,可指定节点类型或元素类型
function walk(filter) {
	clearSpaceNode.call(this.parentNode);
	var args = arguments;
	if (filter) {
		return this[args[2]];
	}
	var nextObj=this;
	do {
		nextObj=nextObj[args[2]];
	} while (nextObj && nextObj.nodeType != 1)
	return $.getObj(nextObj);
}
function next() {
	var args = arguments;
	return walk.apply(this,[args[0],args[1],"nextSibling"]);
}
function prev() {
	var args = arguments;
	return walk.apply(this,[args[0],args[1],"previousSibling"]);
}
function getChildren(filter) {
	clearSpaceNode.call(this);
	var nodes = this.childNodes;
	if (filter) {
		return nodes;
	}
	var reArr=[];
	for (var i=0,len=nodes.length;i<len;i++) {
		if (nodes[i].nodeType == 1) {
			reArr.push($.getObj(nodes[i]));
		}
	}
	return reArr;
}
function first(filter) {
	return this.getChildren(filter)[0];
}
function last(filter) {
	var children = this.getChildren(filter);
	return children[children.length-1];
}
function placeNode(node,fn,__this) {
	fn.call(__this,frag(node));
	return __this;
}
function append(node) {
	return placeNode(node,function (one) {this.appendChild(one)},this);
}
function prepend(node) {
	return placeNode(node,function (one) {this.insertBefore(one,this.firstChild)},this);
}
function before(node) {
	return placeNode(node,function (one) {this.parentNode.insertBefore(one,this)},this);
}
function after(node) {
	if (this.nextSibling) {
		placeNode(node,function (one) {this.parentNode.insertBefore(one,this.nextSibling)},this);
	} else {
		placeNode(node,function (one) {this.parentNode.appendChild(one)},this);
	}
	return this;
}
function del() {
	this.parentNode.removeChild(this);
}
function frag(nodes) {
	var tempFrag =document.createDocumentFragment();
	if (nodes.nodeType) {nodes=[nodes];}
	for (var i=0,len=nodes.length;i<len;i++) {
		if (nodes[i]) {
			tempFrag.appendChild(nodes[i]);
		}
	}
	return tempFrag;
}
NS.next = HEP.next = next;
NS.prev = HEP.prev = prev;
NS.first = HEP.first = first;
NS.last =HEP.last = last;
NS.append = HEP.append =append;
NS.prepend=HEP.prepend = prepend;
NS.before =HEP.before =before;
NS.after = HEP.after = after;
NS.del = HEP.del = del;
NS.getChildren = HEP.getChildren = getChildren;
NS.frag = frag;
}//Node End

{//ClassName Start
function hasClass(cn,obj) {//测试是否具有某类名
	obj = obj || this;
	return (new RegExp("\\b"+cn+"\\b")).test(obj.className);
}
function addClass(cn,obj) {//添加一个类名
	obj = obj || this;
	obj.className += " " + cn;
	return obj;
}
function delClass(cn,obj) {//删除一个类名
	obj = obj || this;
	obj.className = obj.className.replace(new RegExp("\\b"+cn+"\\b"),"");
	return obj;
}
NS.hasClass = HEP.hasClass = hasClass;
NS.addClass= HEP.addClass = addClass;
NS.delClass = HEP.delClass = delClass;
}//ClassName End


{//透明度设置
function opacityTrans(start,finish,add) {
	var obj=this;
	var times = Math.abs((finish-start)/add);
	for (var i=0;i<=times;i++) {
		(function () {
			var val = i*add+start;
			setTimeout(function () {
				setOpacity(obj,val);
			},i*50);
		})();
	}
}
var setOpacity = (document.documentElement.filters)?function (obj,val) {
	obj.style.filter = "alpha(opacity="+val+")";
}:function (obj,val) {
	obj.style.opacity = val/100+"";
};
NS.opacityTrans = HEP.opacityTrans = opacityTrans;
NS.setOpacity = HEP.setOpacity = setOpacity;
function trans(start,finish,timeout) {
	//for ()
}

}


{//Cookie Start
function getCookie(cookieName) {
	var re = new RegExp("\\b"+cookieName+"=([^;]*)\\b");
	var arr =  re.exec(document.cookie);
	return arr?decodeURI(arr[1]):"";
}
function setCookie(name,value,expires,path,domain,secure) {
	var str = name+"="+encodeURI(value);
	if (expires) {str += "; expires="+expires.toGMTString();}
	if (path) {str += "; path="+path;}
	if (domain) {str += "; domain="+domain;}
	if (secure) {str += "; secure";}
	document.cookie = str;
}
function delCookie(cookieName) {
	var expires = new Date();
	expires.setTime(expires.getTime()-1);//将expires设为一个过去的日期，浏览器会自动删除它
	document.cookie = cookieName+"=; expires="+expires.toGMTString();
}
NS.getCookie = getCookie;
NS.setCookie = setCookie;
NS.delCookie = delCookie;
}//Cookie End



//添加到收藏夹代码
function addFav(address,name) {
	try {return window.sidebar.addPanel(name,address,"");}
	catch(e) {
		try {return window.external.addFavorite(address,name);}
		catch(e) {}
	}
}
NS.addFav=addFav;












/*
命名空间结束
window.XT对象作为参数传入
*/
})(window.XT);