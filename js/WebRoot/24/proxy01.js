/**
 * 模仿EXTJS Store Proxy 之间的关系
 */
(function(){
	//定义命名空间
	var Ext = Ext||{};
	Ext.date = Ext.date||{};
	/**
	 * 建立model
	 */
	Ext.date.Model = function(fields){
		this.fields = fields;
	}
	/**
	 * model 模型
	 * proxy 代理
	 */
	Ext.date.Store = function(model,proxy){
		//数据载体
		var data = [];
		this.model = model;
		this.proxy = proxy;
		//加载数据
		this.load = function(){
			var d = this.proxy.request();
			//数据
			for (var i = 0; i < d.length; i++) {
				var o = {};
				//Model
				for (var k = 0; k < model.fields.length; k++) {
					//document.writeln("0["+model.fields[k]["name"]+"] = "+d[i][model.fields[k]["name"]]+"<br>");
					o[model.fields[k]["name"]] = 
						d[i][model.fields[k]["name"]]
				}
				data.push(o);
			}
		}
		//根据索引得到model
		this.getAt = function(index){
			return data[index];
		}
		//得到所有数据的count
		this.getCount = function(){
			return data.length;		
		}
		//清楚所有数据
		this.removeAll = function(){
			data = [];
		}
		//遍历
		this.each = function(fn,scope){
			for (var i = 0; i < data.length; i++) {
				if(scope){
					fn.call(scope,data[i]);
				}else{
					fn.call(this,data[i]);
				}
			}
		}
	}
	//定义Ajax的本体
	Ext.Ajax  = Ext.Ajax||function(){};
	Ext.Ajax.prototype.request = function(type,extraParams,method,url){
		//1.得到跨浏览器的XHR对象,发送请求
		//2.验证请求的状态等等复杂的操作
		//3.那么我们认为这个本体是一个大型的复杂的对象
		//4.应该在这里使用惰性代理
		return [{id:'001',name:'EXTJS'},{id:'002',name:'JS'}]
	}
	//代理类
	Ext.Ajax.proxy = function(){
		var ajax = null;
		//构造函数
		this._init = function(){
			ajax = new Ext.Ajax();		
		}
		this.request = function(type,extraParams,method,url){
			this._init();
			return ajax.request(type,extraParams,method,url);
		}
	}
	//实验
	var person = new Ext.date.Model([{
		name:"name"
	},{
		name:"id"
	}])
	var personStore = new Ext.date.Store(person,new Ext.Ajax.proxy());
	
	personStore.load();
	alert(personStore.getCount());
	alert(personStore.getAt(0).name);
	personStore.each(function(model){
		document.write(model.name+"<br>")
	})
	
})()











