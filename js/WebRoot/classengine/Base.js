/**
 * Base 一切类的基础类
 * @type 
 */
var Base = Ext.Base = function(){};
Base.prototype = {
	//默认的构造函数
	constructor:function(config){
		//默认创建实例的时候全部用config重新替换本类的参数
		Ext.apply(this,config);
	}
}
//可以有一些对Ext的扩展
Ext.apply(Ext,{
	FiresttoUpperCase:function(str){
		if(str){
			toStr = str.substring(0,1);
			toUpStr = toStr.toUpperCase();
			str = toUpStr+str.substring(1,str.length);
		}
		return str;
	}
})