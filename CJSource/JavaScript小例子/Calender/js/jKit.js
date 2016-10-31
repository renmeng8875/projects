(function () {
	var window=this,undefined,jKit,_$,_jKit=window.jKit,cache=[],
	
	idSelectorRegExp=/^\#([a-z]\w*)$/i,//测试ID选择器的正则表达式,第一个子匹配为ID

	//测试类名选择器的正则表达式,第一个子匹配为标签名,第二个子匹配为类名
	classNameSelectorRegExp=/^([a-z](?:[a-z]*|[1-6]))?\.([a-z]\w*)$/i,
	
	//测试标签选择器的正则表达式
	tagNameSelectorRegExp=/^(?:[a-z]+[1-6]*|\*)$/i;
	
	
	if (window.$) {
		_$=window.$;
	}
	
	
	
	jKit=window.jKit=window.$=function (exp,context) {
		cache=[];
		if (exp instanceof Function) {
			jKit(window).addEvent("load",function () {
				exp(jKit);
			});
		}
		if (typeof exp == "string") {
			exp = jKit.$(exp,context);
		}
		return new jKit.jKitObject(arguments.length>1?arguments:exp);
	};
	
	jKit.extend=function (dest,from) {
		if (!from) {from=dest;dest=jKit;}
		for (var i in from) {
			if (typeof from[i] != "undefined") {
				dest[i]=from[i];
			}
		}
		return dest;
	};
	
	jKit.extend({
		noConflict:function (fore){
			window.$=_$;
			if (force) {
				window.jKit = _jKit;
			}
			return jKit;
		},
		$:function (exp,context) {
			/**jKit.$ ,一个功能稍强的DOM元素选择器
			 * 
			 * @param exp String 一个简单的CSS表达式,仅支持ID,标签,类名选择器,且深度只有一层
			 * @param context Object 上下文对象,默认为document
			 * @return Obejct | Node |  ObjectArray | NodeList 返回一个DOM元素或元素列表,仅当找不到元素时,才返回document
			 * 
			 * 例:
			 * 选择ID为main的元素
			 * $("#main")
			 *  选择标签为div的所有元素
			 *  $("div")
			 *  选择拥有类名highlight的所有元素
			 *  $(".highlight")
			 *  选择所有拥有类名correct且标签为span的元素
			 *  $("span.correct")
			 */
			if (idSelectorRegExp.test(exp)) {//测试是否是ID选择器
				return document.getElementById($.idSelectorRegExp.exec(exp)[1]);
			}
			context=context || document;//如果没有提供上下文对象,则默认为document
			if (tagNameSelectorRegExp.test(exp)) {//测试是否是标签选择器
				return context.getElementsByTagName(exp);
			}
			//最后假设它是类名选择器,用正则表达式解析出其中的标签名及类名
			var matches=classNameSelectorRegExp.exec(exp);
			if (matches) {
				/*如果matches不为null,也就是它是一个类名选择器时
				 *如果没有标签名,则为"*",也就是在所有元素中查找
				 *matches[1] ,第一个子匹配为标签名
				 * matches[2],第二个子匹配为类名
				 */
				var tags=context.getElementsByTagName(matches[1] || "*");
				
				//创建正则表达式,测试元素是否含有指定类名,注意,KHTML引擎中re.compile方法始终返回undefined
				var re=new RegExp("\\b"+matches[2]+"\\b");
				var elements=[];//将要返回的元素集合
				for (var i=0,len=tags.length;i<len;i++) {
					if (re.test(tags[i].className)) {
						elements.push(tags[i]);
					}
				}
				return elements;
			}
			return document;//如果什么都不匹配,则返回document
		},
		makeArray:function (arr) {
			if (arr instanceof Array) {return arr;}
			var ret=[];
			if (arr instanceof String || !arr.length || arr==window || arr instanceof Function) {
				ret[0]=arr;
			} else {
				while(ret.length!=arr.length) {
					ret.push(arr[ret.length]);
				}
			}
			return ret;
		},
		each:function (list,fn,args) {
			var i=0,len=list.length,arr=[];
			if (typeof len=="undefined") {
				for (i in list) {
					if (just(list[i],i)===false) break;
				}
			} else {
				for (;i<len;) {
					if (just(list[i],i++)===false) break;
				}
			}
			return arr;
			function just(one,index) {
				res = fn.apply(one,[index,args]);
				if (res===false) {
					return false;
				} else if (typeof res!="undefined") {
					arr.push(one);
				}
			}
		},
		jKitObject:function (obj) {
			if (typeof obj != "object") return null;
			if (obj.length && obj!=window) {
				Array.prototype.push.apply(this,jKit.makeArray(obj));
			} else {
				this.push(obj);
			}
			return this;
		},
	});
	jKit.extend(jKit.jKitObject.prototype,{
		delClass:function (className) {
			/**delClass 删除元素某个类名
			 * 
			 * @param className String 类名
			 * 
			 */
			return this.each(function () {
				this.className = this.className.replace(new RegExp("\\b"+className+"\\b"),"");
			});
		},
		addClass:function (className) {
			/**addClass 向元素中添加类名
			 * 
			 * @param className String 类名
			 * 
			 * 这个函数不会重复添加同一个类名
			 */
			return this.each(function (obj) {
				if (!(new RegExp("\\b"+className+"\\b")).test(this.className)) this.className +=" "+ className;
			});
		},
		css:function () {
			/**css 获取定义在元素上的样式层叠最终值及设置元素样式
			 * 
			 * 
			 * 
			 */
			var args=arguments,properties,tmp;
			if (args.length==1 && typeof args[0]=="string") {
				return this[0] && 
				(
				(tmp=this[0].style) && tmp[args[0]] ||
				(tmp=document.defaultView) && (tmp=tmp.getComputedStyle) && 
				(tmp=tmp(this[0],"")) &&
				tmp.getPropertyValue(args[0].replace(/([A-Z])/g,"-$1").toLowerCase())
				|| (tmp=this[0].currentStyle) && tmp[args[0]]
				);
			}
			
			if (args.length==2) {
				properties={};
				properties[args[0]]=args[1];
			} else if (args.length==1 && typeof args[0]=="object") {
				properties=args[0];
			}
			
			return this.each(function () {
				jKit.extend(this.style,properties);
			});
		},
		addEvent:function (evtype,fn) {
			/**addEvent 给DOM元素注册事件处理函数
			 * 
			 * @param evtype String 不带"on"前缀的事件名称
			 * @param fn Functin  事件处理函数
			 * 
			 * 这个函数可以给DOM元素添加事件监听函数
			 * 同时,保持函数内部的this指向该DOM元素,且函数第一个参数为事件对象
			 */
			return this.each(function () {
				var obj=this;
				if (obj.addEventListener) {
					obj.addEventListener(evtype,function (evt) {
						fn.call(obj,evt);
					},false);
				} else if (obj.attachEvent) {
					obj.attachEvent("on"+evtype,function () {
						fn.call(obj,window.event);
					});
				}
			});
		},
		push:[].push,
		unshift:[].unshift,
		pop:[].pop,
		slice:[].slice,
		splice:[].splice,
		shift:[].shift,
		each:function (fn,args) {
			jKit.each(this,fn,args);
			return this;
		},
		find:function (selector) {
			cache.push(this);
			return new jKit.jKitObject(jKit.$(selector,this[0]));
		},
		end:function () {
			return cache.pop();
		},
		append:function () {
			var args=arguments;
			if (args.length==1 && args[0].length) {
				args=args[0];
			}
			return this[0].appendChild(this.frag(args)) && this;
		},
		empty:function () {
			return this.each(function () {
				this.innerHTML="";
			});
		},
		attr:function (name,val) {
			var args=arguments;
			var properties={};
			if (args.length==1 && typeof name=="string") {
				return this[0] && (this[0][name] || this[0].getAttribute(name));
			} else if (args.length==2) {
				properties[name]=val;
			} else if (args.length==1 && typeof args[0]=="object") {
				properties=args[0];
			}
			return this.each(function () {
				jKit.extend(this,properties);
			});
		},
		frag:function () {
			var args=arguments;
			if (args.length==1 && args[0].length) {
				args=args[0];
			} else if (!args.length) {
				args=this;
			}
			var fragment=(this[0].ownerDocument || this[0]).createDocumentFragment();
			for (var i=0,len=args.length;i<len;) {
				fragment.appendChild(args[i++]);
			}
			return fragment;
		},
		debug:function (index) {
			console.dir(typeof index=="undefined"?this:this[index]);
		}
	});
	
	jKit.jKitObject.prototype.toString=jKit.jKitObject.prototype.valueOf =function () {
		return "[object jKitObject]";
	};
})();