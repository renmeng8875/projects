(function () {
	addEvent(window,'load',function () {
		var c=new Calc($("keyboard"));
		/*c.pushExp(8);//第一次输入8
		c.pushExp("+");//第二次输入一个加号
		c.pushExp(9);//第三次输入9
		c.pushExp("*");
		c.pushExp(10);
		alert(c.evalValue());*/
	});
	
	// Num1 Operate1 Num2
	//exp [Num1,"+",Num2]
	//exp=[];//保存用户输入的表达式
	// 0 输入Num1
	// 1 输入op 运算符（输入一个数字，则作为字符连接到0位后面）
	// 2 输入Num2
	// 3 (再输入一个运算符)将上次输入进行计算，求结果
	//(并将2位清除，开始新的运算)并放入0位
	//然后将新运算符放在1位
	
	function Calc(container) {
		this.numBtns=getByClass("num",container);
		this.exp=[];
		this.mem;
		this.replaceFlag=true;//新的输入是否替换文本框中的值的标志
		var map={
			clearBtn:"clear",
			bsBtn:"backspace",
			sqrtBtn:"sqrt",
			divideBtn:"divide",
			multiplyBtn:"multiply",
			MCBtn:"MC",
			minusBtn:"minus",
			MRBtn:"MR",
			addBtn:"add",
			MSBtn:"MS",
			evalBtn:"eval",
			MAddBtn:"MAdd",
			output:"output"
		};
		for (var i in map) {
			this[i]=getByClass(map[i],container)[0];
		}
		
		var buttons=container.getElementsByTagName("span");
		for (var i=0;i<buttons.length;i++) {
			buttons[i].style.backgroundPosition="-"+buttons[i].offsetLeft+"px -"+buttons[i].offsetTop+"px";
		}
		addEvent(container,"mouseover",function (evt) {
			if (evt.target.tagName=="SPAN") {
				addClass(evt.target,"hover");
			}
		});
		addEvent(container,"mouseout",function (evt) {
			if (evt.target.tagName=="SPAN") {
				delClass(evt.target,"hover");
				delClass(evt.target,"down");
			}
		});
		addEvent(container,"mousedown",function (evt) {
			if (evt.target.tagName=="SPAN") {
				addClass(evt.target,"down");
			}
		});
		var _this=this;
		for (var i=0;i<this.numBtns.length;i++) {
			addEvent(this.numBtns[i],"click",function () {
				_this.input(this.innerHTML);
			});
		}
		
		addEvent(this.divideBtn,"click",invoke("addExp",this,"/"));
		addEvent(this.multiplyBtn,"click",invoke("addExp",this,"*"));
		addEvent(this.minusBtn,"click",invoke("addExp",this,"-"));
		addEvent(this.addBtn,"click",invoke("addExp",this,"+"));
		
		addEvent(this.clearBtn,"click",invoke("clear",this));
		addEvent(this.evalBtn,"click",invoke("evalValue",this));
		
	}
	
	Calc.prototype={
		pushExp:function (n) {
			if (this.exp.length==3) {
				result=eval(this.exp.join(""));
				this.exp=[];//新的运算开始
				this.exp[0]=result;
				this.exp[1]=n;
			} else {
				this.exp.push(n);
			}
		},
		clear:function () {//清除内容
			this.output.value="0";
			this.exp=[];
			this.replaceFlag=true;
		},
		input:function (num) {//用户输入数字
			if (this.replaceFlag)
				this.output.value=num;
			else 
				this.output.value+=num;
				
			if (!this.exp.length || this.exp.length==1) {
				this.exp[0]=this.output.value;
			} else {
				this.exp[2]=this.output.value;
			}
				
			this.replaceFlag=false;
		},
		sqrt:function () {
		},
		addExp:function (op) {//添加一个运算符,+ - X /
			if (!this.exp.length) {//没有任何元素的时候，不需要添加运算符
				return;
			} else if (this.exp.length==1 || this.exp.length==2) {
				//有一个数字或重新输入运算符的情况
				this.exp[1]=op;
			} else if (this.exp.length==3) {
				result=this.evalValue();
				this.exp[1]=op;
			}
			this.replaceFlag=true;
			
		},
		evalValue:function () {//=，请算表达式的值
			var result=eval(this.exp.join(""));
			this.exp=[result];
			this.output.value=result;
			return result;
		},
		isValid:function () {//判断当前输入是否有效
		},
		formatOuput:function () {//(过滤输入)将输出进行格式
		}
		
	};
})();