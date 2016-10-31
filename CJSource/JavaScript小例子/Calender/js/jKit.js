(function () {
	var window=this,undefined,jKit,_$,_jKit=window.jKit,cache=[],
	
	idSelectorRegExp=/^\#([a-z]\w*)$/i,//����IDѡ������������ʽ,��һ����ƥ��ΪID

	//��������ѡ������������ʽ,��һ����ƥ��Ϊ��ǩ��,�ڶ�����ƥ��Ϊ����
	classNameSelectorRegExp=/^([a-z](?:[a-z]*|[1-6]))?\.([a-z]\w*)$/i,
	
	//���Ա�ǩѡ������������ʽ
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
			/**jKit.$ ,һ��������ǿ��DOMԪ��ѡ����
			 * 
			 * @param exp String һ���򵥵�CSS���ʽ,��֧��ID,��ǩ,����ѡ����,�����ֻ��һ��
			 * @param context Object �����Ķ���,Ĭ��Ϊdocument
			 * @return Obejct | Node |  ObjectArray | NodeList ����һ��DOMԪ�ػ�Ԫ���б�,�����Ҳ���Ԫ��ʱ,�ŷ���document
			 * 
			 * ��:
			 * ѡ��IDΪmain��Ԫ��
			 * $("#main")
			 *  ѡ���ǩΪdiv������Ԫ��
			 *  $("div")
			 *  ѡ��ӵ������highlight������Ԫ��
			 *  $(".highlight")
			 *  ѡ������ӵ������correct�ұ�ǩΪspan��Ԫ��
			 *  $("span.correct")
			 */
			if (idSelectorRegExp.test(exp)) {//�����Ƿ���IDѡ����
				return document.getElementById($.idSelectorRegExp.exec(exp)[1]);
			}
			context=context || document;//���û���ṩ�����Ķ���,��Ĭ��Ϊdocument
			if (tagNameSelectorRegExp.test(exp)) {//�����Ƿ��Ǳ�ǩѡ����
				return context.getElementsByTagName(exp);
			}
			//��������������ѡ����,��������ʽ���������еı�ǩ��������
			var matches=classNameSelectorRegExp.exec(exp);
			if (matches) {
				/*���matches��Ϊnull,Ҳ��������һ������ѡ����ʱ
				 *���û�б�ǩ��,��Ϊ"*",Ҳ����������Ԫ���в���
				 *matches[1] ,��һ����ƥ��Ϊ��ǩ��
				 * matches[2],�ڶ�����ƥ��Ϊ����
				 */
				var tags=context.getElementsByTagName(matches[1] || "*");
				
				//����������ʽ,����Ԫ���Ƿ���ָ������,ע��,KHTML������re.compile����ʼ�շ���undefined
				var re=new RegExp("\\b"+matches[2]+"\\b");
				var elements=[];//��Ҫ���ص�Ԫ�ؼ���
				for (var i=0,len=tags.length;i<len;i++) {
					if (re.test(tags[i].className)) {
						elements.push(tags[i]);
					}
				}
				return elements;
			}
			return document;//���ʲô����ƥ��,�򷵻�document
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
			/**delClass ɾ��Ԫ��ĳ������
			 * 
			 * @param className String ����
			 * 
			 */
			return this.each(function () {
				this.className = this.className.replace(new RegExp("\\b"+className+"\\b"),"");
			});
		},
		addClass:function (className) {
			/**addClass ��Ԫ�����������
			 * 
			 * @param className String ����
			 * 
			 * ������������ظ����ͬһ������
			 */
			return this.each(function (obj) {
				if (!(new RegExp("\\b"+className+"\\b")).test(this.className)) this.className +=" "+ className;
			});
		},
		css:function () {
			/**css ��ȡ������Ԫ���ϵ���ʽ�������ֵ������Ԫ����ʽ
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
			/**addEvent ��DOMԪ��ע���¼�������
			 * 
			 * @param evtype String ����"on"ǰ׺���¼�����
			 * @param fn Functin  �¼�������
			 * 
			 * ����������Ը�DOMԪ������¼���������
			 * ͬʱ,���ֺ����ڲ���thisָ���DOMԪ��,�Һ�����һ������Ϊ�¼�����
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