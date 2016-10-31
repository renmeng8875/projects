window.onload=function () {
	var fm=document.getElementById('regForm');
	var fieldset=fm.getElementsByTagName('fieldset')[0];
	fieldset.style.display="none";
	fm.agreeClause.onclick=function () {
		var dl=document.getElementById("clause");
		dl.style.display="none";
		fieldset.style.display="block";
		initForm();
	};
	
	function initForm() {
		var inputs=fm.elements;
		
		for (var i=0;i<inputs.length;i++) {
			if (inputs[i].type=='text' || inputs[i].type=='password') {
				inputs[i].noticeNode=inputs[i].parentNode.getElementsByTagName('em')[0];
				inputs[i].warningNode=inputs[i].parentNode.getElementsByTagName('strong')[0];
				if (!inputs[i].noticeNode) continue;
				/*inputs[i].onfocus=function () {
					if (!this.noticeNode) return;
					this.noticeNode.style.display="inline";
					this.warningNode.style.display="none";
				};
				inputs[i].onblur=function () {
					this.noticeNode.style.display="none";
				};*/
				

			}
		}
		
		var otherHobbyLabel=document.getElementById('otherHobbyLabel');
		var otherHobbyCheckbox=document.getElementById('other');
		if (otherHobbyCheckbox.checked) {
			otherHobbyLabel.style.display="block";
		}
		otherHobbyCheckbox.onclick=function () {
			if (this.checked) {
				otherHobbyLabel.style.display="block";
			} else {
				otherHobbyLabel.style.display="none";
			}
		};
		
		
		var by=fm.birthYear,
				bm=fm.birthMonth,
				bd=fm.birthDay;
				
		by.onchange=function () {
			var y=+this.value,m=+bm.value;
			if (m!=2) {
				return;
			}
			if (isOverYear(y) && bd.options.length!=30) {//29
				bd.options.length=29;
				bd.add(new Option(29,29),null);
			} else if (bd.options.length!=29) {//28
				bd.options.length=29;
			}
		};
		initSelect(by,1980,2000);
		initSelect(bm,1,12);
		
		var m30={4:1,6:1,9:1,11:1};			
		bm.onchange=function () {
			var m=this.value;
			if (m==2) {
				var y=+by.value,d;
				if (!y || isOverYear(y)) {
					d=29;
				} else d=28;
				initSelect(bd,1,d);
			} else if (m in m30) {
				initSelect(bd,1,30);
			} else {
				initSelect(bd,1,31);
			}
		};
		
		function initSelect(select,start,end) {
			select.options.length=1;
			for (var i=start;i<=end;i++) {
				//select.add(new Option(i,i),null);
				appendOpt(select,new Option(i,i));
			}
		}
		
		function appendOpt(select,opt) {
			try {
				select.add(opt,null);//FX
			} catch(e) {
				select.add(opt);
			}
		}
		
		var textarea=fm.motto,
				curLenSpan=document.getElementById('curLen'),
				maxLenSpan=document.getElementById('maxLen'),
				leftLenSpan=document.getElementById('leftLen'),
				maxLen=+maxLenSpan.innerHTML,
				textareaLimit=document.getElementById('textareaLimit'),
				curLen=textarea.value.length,
				leftLen=maxLen-curLen;
		
		curLenSpan.innerHTML=curLen;
		leftLenSpan.innerHTML=leftLen;
		textareaLimit.style.display="inline";
		
		textarea.onkeydown=function () {
			curLen=this.value.length;
			if (curLen>=maxLen) {
				this.value=this.value.substring(0,maxLen-1);
				curLen=maxLen;
			}
			
			leftLen=maxLen-curLen;
			curLenSpan.innerHTML=curLen;
			leftLenSpan.innerHTML=leftLen;
			
		};
		textarea.onpaste=function () {
			return false;
		};
		
		
		var userName=new ValidateInput(fm.userName,/^\w{2,16}$/);
		var pwd=new ValidateInput(fm.userPwd,/^\w{6,16}$/);

		/*fm.onsubmit=function (evt) {
			
			//先阻止浏览器默认跳转行为
			if (evt) {//W3C
				evt.preventDefault();
			} else {//IE
				evt.returnValue=false;
			}
			
			var userName=fm.userName.value;
			
			
			if (!/^\w{2,16}$/.test(userName)) {
				fm.userName.focus();
				fm.userName.warningNode.style.display="inline";
				return;
			}
			fm.userName.warningNode.style.display="none";
			
			var pwd=fm.userPwd.value;
			if (!/^\w{6,16}$/.test(pwd)) {
				fm.userPwd.focus();
				fm.userPwd.warningNode.style.display="inline";
				return;
			}
			fm.userPwd.warningNode.style.display="none";
			
			var rePwd=fm.rePwd.value;
			if (rePwd!=pwd) {
				fm.rePwd.focus();
				fm.rePwd.warningNode.style.display="inline";
				return;
			}
			fm.rePwd.warningNode.style.display="none";
			
			var qq=fm.qq.value;
			if (!/^\d{5,10}$/.test(qq)) {
				fm.qq.focus();
				fm.qq.warningNode.style.display="inline";
				return;
			}
			fm.qq.warningNode.style.display="none";
			
			var email=fm.email.value;
			if (!/^\w{6,18}@([a-z0-9-]+\.)+[a-z]{1,6}$/.test(email)) {
				fm.email.focus();
				fm.email.warningNode.style.display="inline";
				return;
			}
			fm.email.warningNode.style.display="none";
			
			
			
			
			
			this.submit();
			
			
		};*/
		fm.onsubmit=function () {
			if (!userName.validate()) return false; 
			if (!pwd.validate())  return false; 
			return false;
		};
		
		
	}
};

function isOverYear(y) {
	return y%4==0 && y%100!=0 || y%400==0;
}

function ValidateForm(form) {
	this.elements=[];
	this.form=form;
	var _this=this;
	form.onsubmit=function () {
	};
}
//element 必须要有isValid方法和validate方法
ValidateForm.prototype.addElement=function (element) {
	this.elements.push(element);
}

//先面向对象分析，然后再面向对象编程
//存在哪些	对象
//先从客户端程序员的角度出发

//new ValidateInput("提示信息","错误信息");
/**
input HTML Input元素
validator 验证输入内容是否有效的函数/或正则表达式  /或者是预定义验证器
*/

function ValidateInput(input,validator) {
	if 
	//具有验证功能的文本输入域
	this.input=input;
	this.validator=validator;
	
	//notice 包含提示信息的EM元素
	this.notice=input.parentNode.getElementsByTagName('em')[0];
	//warning 包含错误信息的STRONG元素
	this.warning=input.parentNode.getElementsByTagName('strong')[0];
	var _this=this;
	input.onfocus=invoke(this,'showNotice');
	input.onblur=invoke(this,'hideNotice');
	
}



// 调用指定对象的指定方法




ValidateInput.prototype={
	showNotice:function () {
		//显示提示信息
		this.notice.style.display="inline";
	},
	hideNotice:function () {
		//隐藏提示信息
		this.notice.style.display="none";
	},
	showWarning:function () {
		//显示错误信息
		this.warning.style.display="inline";
	},
	hideWarning:function () {
		//隐藏错误信息
		this.warning.style.display="none";
	},
	isValid:function () {
		//当前输入内容是否有效，有效返回true,否则false
		if (typeof this.validator =='function')
			return this.validator(this.input.value);
		else
			return this.validator.test(this.input.value);
	},
	validate:function () {
		//进行验证，无效的话显示对应的错误信息,有效返回true,否则返回false
		var v=this.isValid();
		if (v) {
			//当前内容有效，则隐藏错误信息
			this.hideWarning();
		} else {
			this.showWarning();
			this.input.focus();
		}
		return v;
	}
};


function invoke(obj,method) {
	return function () {
		//这里是绑定的事件监听函数
		obj[method]();
	};
}