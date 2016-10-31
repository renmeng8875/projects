/**谷歌计算器
 * 只实现了其中的传统计算器部分,因为谷歌计算器直接输入表达式,需要强大的数据库及查询技术支持
 * 这里的传统计算器功能也很简陋,仅仅能做加减乘除等一元,二元运算
 * 一个简简单单的计算器,也费了不少功夫,主要是花时间看了下Windows计算器的帮助文档
 * (现在计算器怎么用都不清楚了,如果你也不清楚,那就去看Windows的帮助文档吧^0^)
 */
(function () {
	/**主函数
	 * 同样是一个匿名自执行函数,将所有变量及函数都限定在局部作用域中
	 */
	function $(exp,context) {
		/**$ ,一个功能稍强的DOM元素选择器
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
		if ($.idSelectorRegExp.test(exp)) {//测试是否是ID选择器
			return document.getElementById($.idSelectorRegExp.exec(exp)[1]);
		}
		context=context || document;//如果没有提供上下文对象,则默认为document
		if ($.tagNameSelectorRegExp.test(exp)) {//测试是否是标签选择器
			return context.getElementsByTagName(exp);
		}
		//最后假设它是类名选择器,用正则表达式解析出其中的标签名及类名
		var matches=$.classNameSelectorRegExp.exec(exp);
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
	}
	$.idSelectorRegExp=/^\#([a-z]\w*)$/i;//测试ID选择器的正则表达式,第一个子匹配为ID

	//测试类名选择器的正则表达式,第一个子匹配为标签名,第二个子匹配为类名
	$.classNameSelectorRegExp=/^([a-z](?:[a-z]*|[1-6]))?\.([a-z]\w*)$/i;

	//测试标签选择器的正则表达式
	$.tagNameSelectorRegExp=/^(?:[a-z]+[1-6]*|\*)$/i;

	function $C(className) {
		/**$C 返回类名包含className的第一个元素
		 *
		 * @param className String 类名
		 * @return Object DOM元素
		 *
		 *为了方便,写了个这个函数
		 */
		return $("."+className)[0];//主要功能仍使用$
	}
	
	
	//为了解决JavaScript浮点运算不精确的问题写了这两个函数
	Math.multiply=function () {//多个数相乘
		for (
		var i=0,dotPos,str,
		nums=Array.prototype.slice.apply(arguments),
		len=nums.length,
		powers=0,result=1;
		i<len;
		dotPos=(str=nums[i].toString()).indexOf('.')+1,
		dotPos>0 && (powers+= str.length-dotPos),
		result*=str.replace('.',''),
		i++
		) {}
		return result/Math.pow(10,powers);
	};
	Math.divide=function (a,b) {//两个数相除
		var tmp,powers;
		(tmp=(a=String(a)).split('.')[1]) && (powers= tmp.length),
		(tmp=(b=String(b)).split('.')[1]) && (powers+=tmp.length);
		return a.replace('.','')/b.replace('.','')/Math.pow(10,powers);
	};
	
	
	
	addEvent(window,"load",function () {
		//页面加载完成后,初始化所有
		var buttons = $("span",$("#keyboard"));
		for (var i=0,len=buttons.length;i<len;i++) {
			/*为了方便设置按钮的背景,我将其全部使用了position定位
			 * 而元素的left,top的值取负,正好对应着背景的position
			 * 这些工作完全可以用CSS完成,并且也并没有多复杂
			 */
			buttons[i].style.backgroundPosition = "-"+getRealStyle(buttons[i],"left")+" -"+getRealStyle(buttons[i],"top");

			//注册事件,在按钮按下弹起,及鼠标放上去时变换样式,相应样式仅仅是更改背景而已
			addEvent(buttons[i],"mouseover",hoverButton);
			addEvent(buttons[i],"mousedown",downButton);
			addEvent(buttons[i],"mouseup",hoverButton);
			addEvent(buttons[i],"mouseout",resetButton);
		}
		initCalc();//初始化计算器
		function hoverButton() {
			//鼠标放上去时,删除down类名,添加hover类名
			delClass("down",this);
			addClass("hover",this);
		}
		function downButton() {
			//鼠标放按下时,删除hover类名,添加down类名
			delClass("hover",this);
			addClass("down",this);
		}
		function resetButton() {
			//移出时,删除hover,down类名
			delClass("hover",this);
			delClass("down",this);
		}
		function getRealStyle(obj,name) {
			/**getRealStyle 获取定义在元素上的样式层叠最终值
			 *
			 * @param obj Object 一个DOM元素
			 * @name name String 样式名称
			 *
			 */
			if (obj.style[name]) {
				//由于行内style属性优先级最高,所以如果在这上面定义了样式,就可以认为是元素的最终样式了
				return obj.style[name];
			} else if (document.defaultView && document.defaultView.getComputedStyle) {
				/*W3C标准中,获取元素实际样式的方法
				 * 注意,传入的name是一个驼峰式命名的样式名
				 * 而getPropertyValue需要一个和CSS中一样的名称
				 * 所以需要通过正则表达式将驼峰命名转换成用"-"分隔单词的形式
				 *
				 *
				 */
				name = name.replace(/([A-Z])/g,"-$1").toLowerCase();
				var s = document.defaultView.getComputedStyle(obj,"");
				return s && s.getPropertyValue(name);
			} else {
				//IE中通过currentStyle对象获取元素实际样式
				return obj.currentStyle && obj.currentStyle[name];
			}
		}

		function addClass(className,obj) {
			/**addClass 向元素中添加类名
			 *
			 * @param className String 类名
			 * @param obj Object DOM元素
			 *
			 * 这个函数不会重复添加同一个类名
			 */
			if (!(new RegExp("\\b"+className+"\\b")).test(obj.className)) obj.className +=" "+ className;
		}
		function delClass(className,obj) {
			/**delClass 删除元素某个类名
			 *
			 * @param className String 类名
			 * @param obj Object DOM元素
			 *
			 */
			obj.className = obj.className.replace(new RegExp("\\b"+className+"\\b"),"");
		}
	});

	function initCalc() {
		/** initCalc 初始化计算器
		 * Memory 数字存储器,MS,MC,M+,MR这些按钮会用的
		 * (不知道这些按钮做什么用的?去看Windows的计算器帮助文档)
		 *
		 * exp 一个数组,只放三个元素,为一个二元计算式的三个部分:
		 * 两个数字及一个运算符,最后通过eval计算出式子的值,如5+6会初作为[5,"+",6]放在exp中
		 * exp.last属性,是存储的上次计算式的后两个项目,如上面的例子则为"+"和6,用于当用户不断按=时的重复计算
		 *
		 * output 输出(一个Input元素),同时还要处理用户的直接输入
		 * backspaceInterval,backspace按键重复的计时器ID
		 * 当用户按住退格键不放时,通过setInterval达到不断退格的目的
		 *
		 * insertNumInterval 与backspaceInterval一样,当用户按住一个数字键不放时不断重复的Interval ID
		 *
		 * insertReplace 设定一个标志,表示下次按键输入是追加到output还是替换其值
		 * 即判断当前是在继续同一个数字还是要输入一个新数字,当按下=,+等运算键时,需要将其设为true
		 * true表示替换,false表示追加
		 *
		 * nums 所有的数字按键,包括小数点
		 *
		 *
		 */
		var Memory=0,
		exp=[],
		output=$("#output"),
		backspaceInterval,
		insertNumInterval,
		insertReplace,
		nums = $(".num");

		//当用户亲自输入数字时,需要检查其有效性
		addEvent(output,"keyup",formatInput);
		addEvent(output,"click",formatInput);

		//初始将其值设为0
		output.value="0";
		output.oncontextmenu=function () {
			return false;//阻止"粘贴"操作
		};
		for (var i=0,len=nums.length;i<len;i++) {
			//给所有数字按键添加事件,按下时开始重复输入,松开时停止重复
			addEvent(nums[i],"mousedown",setInsertNumInterval);
			addEvent(nums[i],"mouseup",clearInsertNumInterval);
			addEvent(nums[i],"mouseout",clearInsertNumInterval);
			addEvent(nums[i],"click",clearInsertNumInterval);
		}
		function formatInput() {
			/**formatInput检查output的value的有效性并修复其值,之后再将值添加到exp数组正确的位置
			 * 第一步先将不能出现在数字中的字符去掉
			 * 当有负号进,将其它位置的负号(不正确的位置)去掉,再向最前面加个负号
			 * (这样,如果用户依次输入 "67-" ,将会被换成 -67)
			 */
			val=output.value.replace(/[^0-9\.\-]/g,"");
			if (val.indexOf("-")>-1) {
				val = "-"+val.replace(/-/g,"");
			}
			//当用户多次输入小数点时,将两个小数点替换成一个,并保留其间的数字
			val=val.replace(/(\..*)\./g,"$1");
			//假如以小数点开头,则自动在前面加个0
			val=val.replace(/^\./,"0.");
			if (val.indexOf(".")< 0) {
				//如果不是小数,则将数字的前导的多个0去掉,如-0007将换成-7,并保留了负号
				val = val.replace(/^(\-*)0(?=[^\.])/,"$1");
			}
			//当exp中不足两个元素时,1个或0个,则将output的值作为第一个数
			if (exp.length< 2 ) exp[0]=val;
			//否则,作为第三个数,第二个应该 为运算符
			else exp[2]=val;
			//再将处理后的值放到output中
			output.value = val;
		}

		function setInsertNumInterval() {
			/**setInsertNumInterval 重复输入数字
			 *
			 */
			clearInsertNumInterval();
			var num = this.innerHTML;//获取按键
			insertNum(num);//先执行下,如click事件,只输入一次
			insertNumInterval=setInterval(function () {
				//通过insertNum数字添加进output,标签内的文本即为对应的数字
				insertNum(num);
			},300);
			insertReplace=false;//当输入一个数后,则应该下次输入是追加上去,需要将标志设为false
		}
		function clearInsertNumInterval() {
			/**clearInsertNumInterval 当按键松开时,清除insertNumInterval
			 *
			 */
			clearInterval(insertNumInterval);
		}

		function insertNum(num) {
			if (insertReplace) {//判断是该替换还是该追加
				output.value = num;
			} else {
				output.value += num;
			}
			formatInput();//添加后,需要检查有效性,并将数字加入exp
		}
		addEvent($C("clear"),"click",function () {
			//清除按钮,将output的值设为空,同时清空表达式exp
			exp=[];
			output.value="0";
		});
		addEvent($C("backspace"),"mousedown",function () {
			//退格键
			backspaceChars();
			backspaceInterval=setInterval(backspaceChars,100);
		});
		addEvent($C("backspace"),"mouseup",function () {
			//退格键松开时clearInterval
			clearInterval(backspaceInterval);
		});
		function backspaceChars() {
			//退格功能实现,截取字符串,如果空了,则将0放在上面,最后同样要检查数字并加入exp
			output.value= output.value.substr(0,output.value.length-1) || "0";
			formatInput();
		}
		addEvent($C("eval"),"click",function () {
			//=按钮,计算exp的值
			insertReplace=true;//按=前,将标志设为true,即用计算结果替换原来的值
			var val=0;
			try {//因为使用eval,极容易出错
				if (exp.length==3) {//长度为3,是完整的表达式
					switch (exp[1]) {
						case '*':
							val=Math.multiply(exp[0],exp[2]);
						case '/':
							val=Math.divide(exp[0,exp[2]]);
						default:
							val=eval(exp.join(""));
					}
					val=correctNum(val) || "0";//判断计算结果是否为有效的数字,不是则输出0
				} else if (exp.length==1 && exp.last.length==2) {
					//如果exp中只有一个项目,但exp.last存在,则情况应该是用户不断按=重复计算
					val=correctNum(eval(exp[0]+exp.last[0]+exp.last[1])) || "0";
				}
				if (exp[1] && exp[2]) {//将这次的表达式后两个项目(如果有的话)放入exp.last中,以备用户按过一次=后再按
					exp.last=[exp[1],exp[2]];
				}
				exp.length=1;//最后将exp长度设为1,去掉后两个
				
				insertNum(val);//将结果显示,并将这个结果作为exp的第一个项目以备下次运算
				
			} catch(e) {exp=[];insertNum(0);}
			insertReplace=true;//insertNum执行后,会将标志设为false,需要再设为true,即当用户按过=后,再直接按数字键进行下次运算
		});
		addEvent($C("sqrt"),"click",function () {
			//开平方
			insertReplace=true;//设置标志,用结果替换当前值

			//将其转换成字符串,转换失败则使用0替代
			var num=Number(output.value) || 0;
			num = Math.sqrt(num) || 0;//当遇到一些不能使用sqrt的数时(负数),返回0
			insertNum(num);//始终将显示数字,并将数字加入exp的任务交给insertNum
			insertReplace=true;//同样的,不知道有没有方法改进了,总是要改两次标志
		});
		addEvent($C("MC"),"click",function () {
			//清除记忆
			insertReplace=true;//因为Windows计算器按了这个键后,你再输入数字就会替换当前数字,所以....
			Memory=0;//将Memory设为0
		});
		addEvent($C("MR"),"click",function () {
			//读取数字
			insertReplace=true;//.....
			Memory=Number(Memory) || 0;//应该不会出现记忆中不是数字的情况
			insertNum(Memory);//交给insertNum
		});
		addEvent($C("MS"),"click",function () {
			//保存数字到记忆
			insertReplace=true;//.....
			Memory=output.value || 0;//没有值则保存0,用新值替换旧值
		});
		addEvent($C("MAdd"),"click", function () {
			//将当前输入的数字与记忆中的数字相加并替换以前的记忆,但不显示记忆中的数字
			var curNum=Number(output.value);
			Memory=curNum+(Number(Memory)||0);
			insertReplace=true;//....
		});
		addEvent($C("divide"),"click",function () {
			//除法,传递除号/给expCalc处理去
			expCalc("/");
		});
		addEvent($C("multiply"),"click",function () {
			//乘法,传递乘号*给expCalc处理去
			expCalc("*");
		});
		addEvent($C("minus"),"click",function () {
			//减法特殊些,如果用户什么都没输入的话,则表示输入一个负号
			//我在这里没有判断当用户想减去一个负数的情况,让他自己去想吧,我告诉他规律,即负负得正
			if (Number(output.value)===0) {
				output.value="-";
			} else {
				expCalc("-");
			}
		});
		addEvent($C("add"),"click",function () {
			//加法
			expCalc("+");
		});
		function expCalc(str) {
			/**expCalc 添加运算符
			 *
			 *@param str String 一个字符串形式的运算符
			 */
			insertReplace=true;
			if (exp.length==3) {//exp长度为3时,表明已经是一个完整的运算式,则应计算其结果再进行下次计算
				try {//其实这里有与=按键上的事件处理函数重叠的地方,还可以分离出来
					exp[0]=correctNum(eval(exp.join(""))) || "0";//计算结果并存入第一个位置
				} catch(e) {
					exp[0]=0;//应该不会出错吧
				}
				exp.length=1;//将之前的表达式后面的去掉
				insertNum(exp[0]);//显示数字
			}
			exp[1]=str;//将exp第二个项目设为该运算符
			insertReplace=true;
		}
	}


	function addEvent(obj,evtype,fn) {
		/**addEvent 给DOM元素注册事件处理函数
		 *
		 * @param obj Object DOM元素
		 * @param evtype String 不带"on"前缀的事件名称
		 * @param fn Functin  事件处理函数
		 *
		 * 这个函数可以给DOM元素添加事件监听函数
		 * 同时,保持函数内部的this指向该DOM元素,且函数第一个参数为事件对象
		 */
		if (obj.addEventListener) {
			obj.addEventListener(evtype,function (evt) {
				fn.call(obj,evt);
			},false);
		} else if (obj.attachEvent) {
			obj.attachEvent("on"+evtype,function () {
				fn.call(obj,window.event);
			});
		} else {
			//这种情况简直是臆想,因为如果上面两种注册事件函数的方法都不支持,那么也不会存在evt及window.event事件对象了
			obj["on"+evtype]=function (evt) {
				fn.call(obj,evt || window.event);
			};
		}
	}
	function correctNum(number) {
		/**correctNum 测试给定数字或字符串是否是一个有效的数字
		 *
		 * @param number Number | String 给定字符串或数字(包括特殊值Infinity或NaN等)
		 * @return Boolean | Number | String 如果给定参数是一个有效的数字,则返回该参数,否则返回false
		 *
		 */
		var num = Number(number);
		//最重要的是Infinity,因为该值转换成布尔值是true
		if (isNaN(num) || !isFinite(num) || typeof num=="undefined" || num===null) {
			return false;
		}
		return number;
	}
	
	
	
	
})();