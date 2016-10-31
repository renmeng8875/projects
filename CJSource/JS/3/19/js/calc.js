(function () {
	addEvent(window,'load',function () {
		var c=new Calc($("keyboard"));
		/*c.pushExp(8);//��һ������8
		c.pushExp("+");//�ڶ�������һ���Ӻ�
		c.pushExp(9);//����������9
		c.pushExp("*");
		c.pushExp(10);
		alert(c.evalValue());*/
	});
	
	// Num1 Operate1 Num2
	//exp [Num1,"+",Num2]
	//exp=[];//�����û�����ı��ʽ
	// 0 ����Num1
	// 1 ����op �����������һ�����֣�����Ϊ�ַ����ӵ�0λ���棩
	// 2 ����Num2
	// 3 (������һ�������)���ϴ�������м��㣬����
	//(����2λ�������ʼ�µ�����)������0λ
	//Ȼ�������������1λ
	
	function Calc(container) {
		this.numBtns=getByClass("num",container);
		this.exp=[];
		this.mem;
		this.replaceFlag=true;//�µ������Ƿ��滻�ı����е�ֵ�ı�־
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
				this.exp=[];//�µ����㿪ʼ
				this.exp[0]=result;
				this.exp[1]=n;
			} else {
				this.exp.push(n);
			}
		},
		clear:function () {//�������
			this.output.value="0";
			this.exp=[];
			this.replaceFlag=true;
		},
		input:function (num) {//�û���������
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
		addExp:function (op) {//���һ�������,+ - X /
			if (!this.exp.length) {//û���κ�Ԫ�ص�ʱ�򣬲���Ҫ��������
				return;
			} else if (this.exp.length==1 || this.exp.length==2) {
				//��һ�����ֻ�������������������
				this.exp[1]=op;
			} else if (this.exp.length==3) {
				result=this.evalValue();
				this.exp[1]=op;
			}
			this.replaceFlag=true;
			
		},
		evalValue:function () {//=��������ʽ��ֵ
			var result=eval(this.exp.join(""));
			this.exp=[result];
			this.output.value=result;
			return result;
		},
		isValid:function () {//�жϵ�ǰ�����Ƿ���Ч
		},
		formatOuput:function () {//(��������)��������и�ʽ
		}
		
	};
})();