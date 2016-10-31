/**
 * 1.注释方法
 * 最简单,但是功能也是最弱的
 * 他利用inerface和implement"文字"
 * 把他们用注解的方式显示的表现出来
 */
(function(){
	/**
	 * 用注释来定义一个接口
	 * interface PersonDao(){
	 * 	function add(obj);
	 *  function remove(obj);
	 *  function find(id);
	 * }
	 */
	//我们用注释的方式来实现他
	/**
	 * PersonDaoImpl implement interface
	 */
	var PersonDaoImpl = function(){
		
	}
	PersonDaoImpl.prototype.add = function(obj){
		//..
	}
	PersonDaoImpl.prototype.remove = function(obj){
		//..
	}
	PersonDaoImpl.prototype.find = function(id){
		//..
	}
	/**
	 * 千万不要感觉他是没有任何意义的
	 * 1.大型的项目靠得就是规范和标准
	 * 2.这样的写法会交你的程序员在没有写实现之前有充分时间做代码的设计和架构
	 * 3.缺点:要认为的遵守
	 */
})()



