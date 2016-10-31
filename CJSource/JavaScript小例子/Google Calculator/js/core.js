/**�ȸ������
 * ֻʵ�������еĴ�ͳ����������,��Ϊ�ȸ������ֱ��������ʽ,��Ҫǿ������ݿ⼰��ѯ����֧��
 * ����Ĵ�ͳ����������Ҳ�ܼ�ª,���������Ӽ��˳���һԪ,��Ԫ����
 * һ����򵥵��ļ�����,Ҳ���˲��ٹ���,��Ҫ�ǻ�ʱ�俴����Windows�������İ����ĵ�
 * (���ڼ�������ô�ö��������,�����Ҳ�����,�Ǿ�ȥ��Windows�İ����ĵ���^0^)
 */
(function () {
	/**������
	 * ͬ����һ��������ִ�к���,�����б������������޶��ھֲ���������
	 */
	function $(exp,context) {
		/**$ ,һ��������ǿ��DOMԪ��ѡ����
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
		if ($.idSelectorRegExp.test(exp)) {//�����Ƿ���IDѡ����
			return document.getElementById($.idSelectorRegExp.exec(exp)[1]);
		}
		context=context || document;//���û���ṩ�����Ķ���,��Ĭ��Ϊdocument
		if ($.tagNameSelectorRegExp.test(exp)) {//�����Ƿ��Ǳ�ǩѡ����
			return context.getElementsByTagName(exp);
		}
		//��������������ѡ����,��������ʽ���������еı�ǩ��������
		var matches=$.classNameSelectorRegExp.exec(exp);
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
	}
	$.idSelectorRegExp=/^\#([a-z]\w*)$/i;//����IDѡ������������ʽ,��һ����ƥ��ΪID

	//��������ѡ������������ʽ,��һ����ƥ��Ϊ��ǩ��,�ڶ�����ƥ��Ϊ����
	$.classNameSelectorRegExp=/^([a-z](?:[a-z]*|[1-6]))?\.([a-z]\w*)$/i;

	//���Ա�ǩѡ������������ʽ
	$.tagNameSelectorRegExp=/^(?:[a-z]+[1-6]*|\*)$/i;

	function $C(className) {
		/**$C ������������className�ĵ�һ��Ԫ��
		 *
		 * @param className String ����
		 * @return Object DOMԪ��
		 *
		 *Ϊ�˷���,д�˸��������
		 */
		return $("."+className)[0];//��Ҫ������ʹ��$
	}
	
	
	//Ϊ�˽��JavaScript�������㲻��ȷ������д������������
	Math.multiply=function () {//��������
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
	Math.divide=function (a,b) {//���������
		var tmp,powers;
		(tmp=(a=String(a)).split('.')[1]) && (powers= tmp.length),
		(tmp=(b=String(b)).split('.')[1]) && (powers+=tmp.length);
		return a.replace('.','')/b.replace('.','')/Math.pow(10,powers);
	};
	
	
	
	addEvent(window,"load",function () {
		//ҳ�������ɺ�,��ʼ������
		var buttons = $("span",$("#keyboard"));
		for (var i=0,len=buttons.length;i<len;i++) {
			/*Ϊ�˷������ð�ť�ı���,�ҽ���ȫ��ʹ����position��λ
			 * ��Ԫ�ص�left,top��ֵȡ��,���ö�Ӧ�ű�����position
			 * ��Щ������ȫ������CSS���,����Ҳ��û�жิ��
			 */
			buttons[i].style.backgroundPosition = "-"+getRealStyle(buttons[i],"left")+" -"+getRealStyle(buttons[i],"top");

			//ע���¼�,�ڰ�ť���µ���,��������ȥʱ�任��ʽ,��Ӧ��ʽ�����Ǹ��ı�������
			addEvent(buttons[i],"mouseover",hoverButton);
			addEvent(buttons[i],"mousedown",downButton);
			addEvent(buttons[i],"mouseup",hoverButton);
			addEvent(buttons[i],"mouseout",resetButton);
		}
		initCalc();//��ʼ��������
		function hoverButton() {
			//������ȥʱ,ɾ��down����,���hover����
			delClass("down",this);
			addClass("hover",this);
		}
		function downButton() {
			//���Ű���ʱ,ɾ��hover����,���down����
			delClass("hover",this);
			addClass("down",this);
		}
		function resetButton() {
			//�Ƴ�ʱ,ɾ��hover,down����
			delClass("hover",this);
			delClass("down",this);
		}
		function getRealStyle(obj,name) {
			/**getRealStyle ��ȡ������Ԫ���ϵ���ʽ�������ֵ
			 *
			 * @param obj Object һ��DOMԪ��
			 * @name name String ��ʽ����
			 *
			 */
			if (obj.style[name]) {
				//��������style�������ȼ����,��������������涨������ʽ,�Ϳ�����Ϊ��Ԫ�ص�������ʽ��
				return obj.style[name];
			} else if (document.defaultView && document.defaultView.getComputedStyle) {
				/*W3C��׼��,��ȡԪ��ʵ����ʽ�ķ���
				 * ע��,�����name��һ���շ�ʽ��������ʽ��
				 * ��getPropertyValue��Ҫһ����CSS��һ��������
				 * ������Ҫͨ��������ʽ���շ�����ת������"-"�ָ����ʵ���ʽ
				 *
				 *
				 */
				name = name.replace(/([A-Z])/g,"-$1").toLowerCase();
				var s = document.defaultView.getComputedStyle(obj,"");
				return s && s.getPropertyValue(name);
			} else {
				//IE��ͨ��currentStyle�����ȡԪ��ʵ����ʽ
				return obj.currentStyle && obj.currentStyle[name];
			}
		}

		function addClass(className,obj) {
			/**addClass ��Ԫ�����������
			 *
			 * @param className String ����
			 * @param obj Object DOMԪ��
			 *
			 * ������������ظ����ͬһ������
			 */
			if (!(new RegExp("\\b"+className+"\\b")).test(obj.className)) obj.className +=" "+ className;
		}
		function delClass(className,obj) {
			/**delClass ɾ��Ԫ��ĳ������
			 *
			 * @param className String ����
			 * @param obj Object DOMԪ��
			 *
			 */
			obj.className = obj.className.replace(new RegExp("\\b"+className+"\\b"),"");
		}
	});

	function initCalc() {
		/** initCalc ��ʼ��������
		 * Memory ���ִ洢��,MS,MC,M+,MR��Щ��ť���õ�
		 * (��֪����Щ��ť��ʲô�õ�?ȥ��Windows�ļ����������ĵ�)
		 *
		 * exp һ������,ֻ������Ԫ��,Ϊһ����Ԫ����ʽ����������:
		 * �������ּ�һ�������,���ͨ��eval�����ʽ�ӵ�ֵ,��5+6�����Ϊ[5,"+",6]����exp��
		 * exp.last����,�Ǵ洢���ϴμ���ʽ�ĺ�������Ŀ,�������������Ϊ"+"��6,���ڵ��û����ϰ�=ʱ���ظ�����
		 *
		 * output ���(һ��InputԪ��),ͬʱ��Ҫ�����û���ֱ������
		 * backspaceInterval,backspace�����ظ��ļ�ʱ��ID
		 * ���û���ס�˸������ʱ,ͨ��setInterval�ﵽ�����˸��Ŀ��
		 *
		 * insertNumInterval ��backspaceIntervalһ��,���û���סһ�����ּ�����ʱ�����ظ���Interval ID
		 *
		 * insertReplace �趨һ����־,��ʾ�´ΰ���������׷�ӵ�output�����滻��ֵ
		 * ���жϵ�ǰ���ڼ���ͬһ�����ֻ���Ҫ����һ��������,������=,+�������ʱ,��Ҫ������Ϊtrue
		 * true��ʾ�滻,false��ʾ׷��
		 *
		 * nums ���е����ְ���,����С����
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

		//���û�������������ʱ,��Ҫ�������Ч��
		addEvent(output,"keyup",formatInput);
		addEvent(output,"click",formatInput);

		//��ʼ����ֵ��Ϊ0
		output.value="0";
		output.oncontextmenu=function () {
			return false;//��ֹ"ճ��"����
		};
		for (var i=0,len=nums.length;i<len;i++) {
			//���������ְ�������¼�,����ʱ��ʼ�ظ�����,�ɿ�ʱֹͣ�ظ�
			addEvent(nums[i],"mousedown",setInsertNumInterval);
			addEvent(nums[i],"mouseup",clearInsertNumInterval);
			addEvent(nums[i],"mouseout",clearInsertNumInterval);
			addEvent(nums[i],"click",clearInsertNumInterval);
		}
		function formatInput() {
			/**formatInput���output��value����Ч�Բ��޸���ֵ,֮���ٽ�ֵ��ӵ�exp������ȷ��λ��
			 * ��һ���Ƚ����ܳ����������е��ַ�ȥ��
			 * ���и��Ž�,������λ�õĸ���(����ȷ��λ��)ȥ��,������ǰ��Ӹ�����
			 * (����,����û��������� "67-" ,���ᱻ���� -67)
			 */
			val=output.value.replace(/[^0-9\.\-]/g,"");
			if (val.indexOf("-")>-1) {
				val = "-"+val.replace(/-/g,"");
			}
			//���û��������С����ʱ,������С�����滻��һ��,��������������
			val=val.replace(/(\..*)\./g,"$1");
			//������С���㿪ͷ,���Զ���ǰ��Ӹ�0
			val=val.replace(/^\./,"0.");
			if (val.indexOf(".")< 0) {
				//�������С��,�����ֵ�ǰ���Ķ��0ȥ��,��-0007������-7,�������˸���
				val = val.replace(/^(\-*)0(?=[^\.])/,"$1");
			}
			//��exp�в�������Ԫ��ʱ,1����0��,��output��ֵ��Ϊ��һ����
			if (exp.length< 2 ) exp[0]=val;
			//����,��Ϊ��������,�ڶ���Ӧ�� Ϊ�����
			else exp[2]=val;
			//�ٽ�������ֵ�ŵ�output��
			output.value = val;
		}

		function setInsertNumInterval() {
			/**setInsertNumInterval �ظ���������
			 *
			 */
			clearInsertNumInterval();
			var num = this.innerHTML;//��ȡ����
			insertNum(num);//��ִ����,��click�¼�,ֻ����һ��
			insertNumInterval=setInterval(function () {
				//ͨ��insertNum������ӽ�output,��ǩ�ڵ��ı���Ϊ��Ӧ������
				insertNum(num);
			},300);
			insertReplace=false;//������һ������,��Ӧ���´�������׷����ȥ,��Ҫ����־��Ϊfalse
		}
		function clearInsertNumInterval() {
			/**clearInsertNumInterval �������ɿ�ʱ,���insertNumInterval
			 *
			 */
			clearInterval(insertNumInterval);
		}

		function insertNum(num) {
			if (insertReplace) {//�ж��Ǹ��滻���Ǹ�׷��
				output.value = num;
			} else {
				output.value += num;
			}
			formatInput();//��Ӻ�,��Ҫ�����Ч��,�������ּ���exp
		}
		addEvent($C("clear"),"click",function () {
			//�����ť,��output��ֵ��Ϊ��,ͬʱ��ձ��ʽexp
			exp=[];
			output.value="0";
		});
		addEvent($C("backspace"),"mousedown",function () {
			//�˸��
			backspaceChars();
			backspaceInterval=setInterval(backspaceChars,100);
		});
		addEvent($C("backspace"),"mouseup",function () {
			//�˸���ɿ�ʱclearInterval
			clearInterval(backspaceInterval);
		});
		function backspaceChars() {
			//�˸���ʵ��,��ȡ�ַ���,�������,��0��������,���ͬ��Ҫ������ֲ�����exp
			output.value= output.value.substr(0,output.value.length-1) || "0";
			formatInput();
		}
		addEvent($C("eval"),"click",function () {
			//=��ť,����exp��ֵ
			insertReplace=true;//��=ǰ,����־��Ϊtrue,���ü������滻ԭ����ֵ
			var val=0;
			try {//��Ϊʹ��eval,�����׳���
				if (exp.length==3) {//����Ϊ3,�������ı��ʽ
					switch (exp[1]) {
						case '*':
							val=Math.multiply(exp[0],exp[2]);
						case '/':
							val=Math.divide(exp[0,exp[2]]);
						default:
							val=eval(exp.join(""));
					}
					val=correctNum(val) || "0";//�жϼ������Ƿ�Ϊ��Ч������,���������0
				} else if (exp.length==1 && exp.last.length==2) {
					//���exp��ֻ��һ����Ŀ,��exp.last����,�����Ӧ�����û����ϰ�=�ظ�����
					val=correctNum(eval(exp[0]+exp.last[0]+exp.last[1])) || "0";
				}
				if (exp[1] && exp[2]) {//����εı��ʽ��������Ŀ(����еĻ�)����exp.last��,�Ա��û�����һ��=���ٰ�
					exp.last=[exp[1],exp[2]];
				}
				exp.length=1;//���exp������Ϊ1,ȥ��������
				
				insertNum(val);//�������ʾ,������������Ϊexp�ĵ�һ����Ŀ�Ա��´�����
				
			} catch(e) {exp=[];insertNum(0);}
			insertReplace=true;//insertNumִ�к�,�Ὣ��־��Ϊfalse,��Ҫ����Ϊtrue,�����û�����=��,��ֱ�Ӱ����ּ������´�����
		});
		addEvent($C("sqrt"),"click",function () {
			//��ƽ��
			insertReplace=true;//���ñ�־,�ý���滻��ǰֵ

			//����ת�����ַ���,ת��ʧ����ʹ��0���
			var num=Number(output.value) || 0;
			num = Math.sqrt(num) || 0;//������һЩ����ʹ��sqrt����ʱ(����),����0
			insertNum(num);//ʼ�ս���ʾ����,�������ּ���exp�����񽻸�insertNum
			insertReplace=true;//ͬ����,��֪����û�з����Ľ���,����Ҫ�����α�־
		});
		addEvent($C("MC"),"click",function () {
			//�������
			insertReplace=true;//��ΪWindows�����������������,�����������־ͻ��滻��ǰ����,����....
			Memory=0;//��Memory��Ϊ0
		});
		addEvent($C("MR"),"click",function () {
			//��ȡ����
			insertReplace=true;//.....
			Memory=Number(Memory) || 0;//Ӧ�ò�����ּ����в������ֵ����
			insertNum(Memory);//����insertNum
		});
		addEvent($C("MS"),"click",function () {
			//�������ֵ�����
			insertReplace=true;//.....
			Memory=output.value || 0;//û��ֵ�򱣴�0,����ֵ�滻��ֵ
		});
		addEvent($C("MAdd"),"click", function () {
			//����ǰ���������������е�������Ӳ��滻��ǰ�ļ���,������ʾ�����е�����
			var curNum=Number(output.value);
			Memory=curNum+(Number(Memory)||0);
			insertReplace=true;//....
		});
		addEvent($C("divide"),"click",function () {
			//����,���ݳ���/��expCalc����ȥ
			expCalc("/");
		});
		addEvent($C("multiply"),"click",function () {
			//�˷�,���ݳ˺�*��expCalc����ȥ
			expCalc("*");
		});
		addEvent($C("minus"),"click",function () {
			//��������Щ,����û�ʲô��û����Ļ�,���ʾ����һ������
			//��������û���жϵ��û����ȥһ�����������,�����Լ�ȥ���,�Ҹ���������,����������
			if (Number(output.value)===0) {
				output.value="-";
			} else {
				expCalc("-");
			}
		});
		addEvent($C("add"),"click",function () {
			//�ӷ�
			expCalc("+");
		});
		function expCalc(str) {
			/**expCalc ��������
			 *
			 *@param str String һ���ַ�����ʽ�������
			 */
			insertReplace=true;
			if (exp.length==3) {//exp����Ϊ3ʱ,�����Ѿ���һ������������ʽ,��Ӧ���������ٽ����´μ���
				try {//��ʵ��������=�����ϵ��¼��������ص��ĵط�,�����Է������
					exp[0]=correctNum(eval(exp.join(""))) || "0";//�������������һ��λ��
				} catch(e) {
					exp[0]=0;//Ӧ�ò�������
				}
				exp.length=1;//��֮ǰ�ı��ʽ�����ȥ��
				insertNum(exp[0]);//��ʾ����
			}
			exp[1]=str;//��exp�ڶ�����Ŀ��Ϊ�������
			insertReplace=true;
		}
	}


	function addEvent(obj,evtype,fn) {
		/**addEvent ��DOMԪ��ע���¼�������
		 *
		 * @param obj Object DOMԪ��
		 * @param evtype String ����"on"ǰ׺���¼�����
		 * @param fn Functin  �¼�������
		 *
		 * ����������Ը�DOMԪ������¼���������
		 * ͬʱ,���ֺ����ڲ���thisָ���DOMԪ��,�Һ�����һ������Ϊ�¼�����
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
			//���������ֱ������,��Ϊ�����������ע���¼������ķ�������֧��,��ôҲ�������evt��window.event�¼�������
			obj["on"+evtype]=function (evt) {
				fn.call(obj,evt || window.event);
			};
		}
	}
	function correctNum(number) {
		/**correctNum ���Ը������ֻ��ַ����Ƿ���һ����Ч������
		 *
		 * @param number Number | String �����ַ���������(��������ֵInfinity��NaN��)
		 * @return Boolean | Number | String �������������һ����Ч������,�򷵻ظò���,���򷵻�false
		 *
		 */
		var num = Number(number);
		//����Ҫ����Infinity,��Ϊ��ֵת���ɲ���ֵ��true
		if (isNaN(num) || !isFinite(num) || typeof num=="undefined" || num===null) {
			return false;
		}
		return number;
	}
	
	
	
	
})();