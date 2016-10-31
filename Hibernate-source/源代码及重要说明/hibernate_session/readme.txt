测试实体对象的生命周期

junit简介：
	* 编写测试类xxxTest，需要继承TestCase
	* 编写单元测试方法，测试方法必须以test开头，测试方法不能含有参数和返回值，如：
	  public void testHello1() {}
	* 最好单元测试的代码单独建立一个目录
	
了解Hibernate中CRUD操作

了解get和load的区别？
	* get不支持lazy，load支持lazy
	* 采用get加载数据，如果没有匹配的数据，返回null，而load则抛出异常
	
transient状态的特征？
	* 在数据库中没有与之匹配的数据
	* 没有纳入session的管理
	
persistent状态的特征？
	* persistent状态的对象在数据库中有与之匹配的数据
	* 纳入了session的管理
	* 在清理缓存（脏数据检查）的时候,会和数据库同步
	
detached状态的特征？
	* 在数据库中有与之匹配的数据
	* 没有纳入session的管理	 		
	
	