/**
 * 单例: 扩展原有的function的功能
 * @type 
 */
Ext.Function = {
	/**
	 * 克隆一个方法,并且返回新的方法
	 * @param {} method
	 * @return {}
	 */
	clone:function(method){
		return function(){
			return method.apply(this,arguments);
		}
	},
	/**
	 * 别名策略
	 * @param {} object
	 * @param {} methodName
	 * @return {}
	 */
	alias:function(object,methodName){
		return function(){
			return object[methodName].apply(object,arguments);
		}
	}
}