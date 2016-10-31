/********************
** 验证框架插件
   example :
    $("#frm").initValid({
        required : ['name','email',$('#name input')],
        email : ['email'],
        min_value : [
            {id : 'age',params : 0}
        ],
        max_value :  [
            {id : $("#number"),params : 120}
        ],
        pwd_equals : [{id:'pwd1',params:'pwd2',msg:'输入不一致'}],
        must_select : [{id:'city',msg:'城市必须选择'}]
    });
*********************/
(function(){
    $.extend({
        // 初始化验证器
        initValid : function(rules){
            // 判断是否选择了元素
            if (!this.length) {
			    throw "没有选择任何东西";
			    return;
		    }
		    if(!rules){
		        throw "没有任何验证规则";
			    return;
		    }
		    var self = $(this[0]);
		    // form ，添加form验证
		    if(this[0].tagName.toUpperCase() == "FORM"){
		    	self.bind("submit",function(){
		            return $(this).valid();
		        });
		    }
		    var childrenIds = new Array();
		    // 循环绑定验证事件
		    for(var k in rules){
		        var _array = rules[k];
                for(var i=0;i<_array.length;i++){         
                    if(typeof _array[i] == "string"){
                        // 没有参数
                        var target = $("#" + _array[i]);
                        $._initValidator(target,k,null,null);
                        childrenIds.push(target);
                    }else if(_array[i] instanceof $){
                    	// 是jquery对象
                    	_array[i].each(function(){
                    		
                    		var target = $(this);
                    		// 如果没有id，自动创建id。
                        	if(target.attr("id") == ""){
                        		tid = "jv_" + new Date().getTime() + "_" + parseInt(Math.random()*10000);
                        		target.attr("id",tid);
                        	}
                    		$._initValidator(target,k,null,null);
                            childrenIds.push(target);
                    	});	
                    }else{
                    	var params = _array[i].params != null ? _array[i].params : null;
                    	var msg = _array[i].msg != null ? _array[i].msg : null;
                        if(_array[i].id instanceof $){
                        	_array[i].id.each(function(){
                        		var target = $(this);
                        		$._initValidator(target,k,params,msg);
                                childrenIds.push(target);
                        	});
                        }else{
                        	var target = $("#" + _array[i].id);
                        	$._initValidator(target,k,params,msg);
                            childrenIds.push(target);
                        }
                    }
                }
		    }
		    
		    self.data($.validator.setting.data_key,childrenIds);
        },
        // 验证
        valid : function(){
            if(!this.length) return true;
            try{
                var _self = $(this[0]);
                var ret = true; // 验证结果

                //var _self = $(this);
                var _validator = _self.data($.validator.setting.data_key);
                if(!_validator)
                    throw this.id + " 没有绑定验证器";

                if(_validator instanceof Array){
                    // 绑定的数据是juqery对象组成的数组
                    //var ids = _validator.split(";");
                    for(var i=0;i<_validator.length;i++){
                         ret = _validator[i].valid() && ret;
                    }
                }else{
                    var value = _self.val();
                    if(_self[0].tagName == "SELECT")
                        value = _self.attr("selectedIndex");
                    // 绑定的是validator对象

                    for(var i=0;i<_validator.rules.length;i++){
                        var _validClass = _validator.rules[i].validClass;
                        var _params = _validator.rules[i].params;
                        
                        var _match = $.validator.funs[_validClass];
                        
                        var _ret = true;
                        //alert((typeof _match).toString());
                        if(typeof _match == "function"){     // 是函数
                            _ret = _match.call(null,value,_params);
                        }else{    //是正则表达式
                            _ret = value.match(_match);   
                        }
                        if(!_ret){
                            // 验证失败
                            _validator.currentRule = i;
                            
                            $.validator.setError(_self);
                        }else{
                            // 验证通过
                            $.validator.removeError(_self);
                        }
                        
                        ret = ret && _ret;
                        // 如果1个元素有多个验证规则，
                        // 但1个规则失败的时候就跳出改元素的其他规则验证
                        if(!_ret) break;
                    }
                }
                return (!ret?false:ret);
            }catch(e){
                throw e;
                return false;
            }
        },
        // 清空验证绑定
        clearValid :function(){
            if(!this.length) return true;
            
            try{
                // 循环验证
                this.each(function(ret){
                    var _self = $(this);
                    _validator = _self.data($.validator.setting.data_key);
                    
                    if(_validator instanceof Array){
                        // 绑定的数据数组
                    	for(var i=0;i<_validator.length;i++){
                            ret = _validator[i].clearValid() && ret;
                       }
                    }else{
                        // 去除所有的事件,因为用到clearValid一般是页面跳转，
                        // 或者删除元素才使用这个，所以可以去除所有
                        _self.unbind();
                    }
                    _self.removeData($.validator.setting.data_key);
                });
            }catch(e){
                throw e;
                return false;
            }
        }
    });
    $._initValidator = function(target,ruleClass,params,msg){
    	// 绑定数据
        var _validator = target.data($.validator.setting.data_key);
        if(!_validator){
            _validator = new $.validator();
        }
        
        _validator.addRule(ruleClass, params,msg);
        
        target.data($.validator.setting.data_key,_validator);
        // 绑定blur事件
        target.blur(function(){
            $(this).valid();
        });
	};
    // 验证容器
    $.validator = function(){
        // 该控件对应的所有验证规则
        this.rules = [];
        // 当前验证出错的规则index
        this.currentRule = 0;
        // 添加验证规则
        this.addRule = function(validClass,params,msg){
            this.rules[this.rules.length] = {
                'validClass' : validClass,
                'params' : params,
                'msg' : msg
            };
        };
    };
    // 设置显示错误
    $.validator.setError = function(target){
        target.addClass($.validator.setting.fail_class);
        var icon_target = target;
        if($.validator.setting.create_icon){    //创建图标
        	var tid = target.attr("id");
            iconId = $.validator.setting.icon_prexId + tid;
            
            if(!document.getElementById(iconId)){ // 判断是否已创建   
                spnIcon = document.createElement("SPAN");
                spnIcon.id = iconId;
                spnIcon.innerHTML = "&nbsp;";
                spnIcon.className = $.validator.setting.icon_class;
                
                target.after(spnIcon);
            }
            
            icon_target = $("#" + iconId);
            icon_target.show();
        }
        
        function mover(evt){
            $.validator.msg.show.call($.validator.msg,icon_target,evt);
        };
        function mout(evt){
        	$.validator.msg.hide();
        };
        
        icon_target[0].onmouseover = mover;
        icon_target[0].onmouseout = mout;
        icon_target.bind("blur",function(evt){
        	target.valid();
        });
        
        target[0].onmouseover = mover;
        target[0].onmouseout = mout;
        target.bind("blur",function(evt){
        	target.valid();
        });
    };
    // 清除错误
    $.validator.removeError = function(target){
        target.removeClass($.validator.setting.fail_class);
        var icon_target = target;
        if($.validator.setting.create_icon){
            iconId = $.validator.setting.icon_prexId + target.attr("id");
            icon_target = $("#" + iconId);
            icon_target.hide();
        }

        var emptyFn = function(){};
        if(icon_target.length>0){
        	icon_target[0].onmouseover = emptyFn;
        	icon_target[0].onmouseout = emptyFn;
        }
        if(target.length>0){
        	target[0].onmouseover = emptyFn;
        	target[0].onmouseout = emptyFn;
        }

        // 此处隐藏错误提示框，因为已unbind了mouseleave，所以不会自动消失
        $.validator.msg.hide();
    };
    //消息显示对象
    $.validator.msg = {
        init : function(){
            divP = document.createElement("DIV");
            divP.id = $.validator.setting.msg_id;
            divT = document.createElement("DIV");
            divT.id = $.validator.setting.msg_text_id;
            divP.appendChild(divT);
            document.body.appendChild(divP);
        },
        show : function(target,evt){
            if(!document.getElementById($.validator.setting.msg_id)) 
                this.init();
            // 默认情况下target是图标，不是输入框 
            var dataTarget =  target;
            if($.validator.setting.create_icon){
                dataTarget = $("#" + target.attr("id").substring($.validator.setting.icon_prexId.length));
            }
            _validator = dataTarget.data($.validator.setting.data_key);
            rule = _validator.rules[_validator.currentRule];
            var msg = rule.msg?rule.msg:$.validator.messages[rule.validClass];   
            _msg = $.validator.format(msg,rule.params);
            
            $("#" + $.validator.setting.msg_text_id).html(_msg);
            
            // 设置绝对位置            
            _msgbox = $("#"+$.validator.setting.msg_id);
            var rect = target[0].getBoundingClientRect();
            var h = rect.bottom - rect.top;  
            _msgbox.css("top",evt.pageY + h + "px");
            _msgbox.css("left",evt.pageX + 10 + "px");
            _msgbox.show();
        },
        hide : function(){
            $("#" + $.validator.setting.msg_id).hide();
        }
    };
    //  格式化文字
    $.validator.format=function(msg,params){
        if(params==null) return msg;
        if(!params.length){  // 只有1个数据
           return msg.replace(new RegExp("\\{0\\}", "g"), params);
            
        }
        for(var i=0;i<params.length;i++){
            msg = msg.replace(new RegExp("\\{" + i + "\\}", "g"), params[i]);
        }
        return msg;
    };
    // 默认配置
    $.validator.setting={
        // 是否创建错误图标
        create_icon :true,
        msg_id : 'validator_msg',
        msg_text_id : 'validator_msg_text',
        data_key : 'validator',
        icon_prexId : 'vi_',
        icon_class : 'validator_icon',
        fail_class : 'validator_fail'
    };
    // 默认提示信息
    $.validator.messages={
        'required':'请输入值.',
        'int':'只能输入整数.',
        'positive_int' : '只能输入正整数',
        'float':'只能输入数字.',
        'positive_float' : '只能输入正数',
        'email':'请输入有效的邮件地址.',
        'url':'请输入有效的URL地址.',
        'pattern':'输入的值不匹配',
        'min_value':'最小值为 {0}',
        'max_value':'最大值为 {0}',
        'equals':'输入必须与 {0} 一致',
        'must_select':'必须选择1个',
        'min_length' : "长度不能小于 {0}",
        'max_length' : "长度不能大于 {0}",
        'pwd_equals' : '二次输入的密码不一致'
    };
    /**包含验证的所有函数**/
    $.validator.funs={
        'required' : /[\S]+/,
        'int' :  /^[-]?[\d]*/,
        'positive_int' : /^[\d]*$/,
        'float' : function(v){return !isNaN(v);},
        'positive_float' : /^\d*(\.\d+)?$/,
        'email' : /\w{1,}[@][\w\-]{1,}([.]([\w\-]{1,})){1,3}$/,
        'url' : /^(http|https|ftp):\/\/(([A-Z0-9][A-Z0-9_-]*)(\.[A-Z0-9][A-Z0-9_-]*)+)(:(\d+))?\/?/i,
        'pattern' : function(v,p){return v.match(p);},
        'min_value' : function(v,params){return v>=params;},
        'max_value' : function(v,params){return v<=params;},
        'equals' : function(v,params){return v==params;},
        'must_select' : function(v){return v>=1;},
        "min_length" : function(v,params){return v.length >= params;},
        "max_length" : function(v,params){return v.length <= params;},
        'pwd_equals' : function(v,params){return v == $("#"+params).val();}
    };
})();