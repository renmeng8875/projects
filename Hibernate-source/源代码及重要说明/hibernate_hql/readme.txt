hibernate查询语言hql

在hql中关键字不区分大小写，但是属性和类名区分大小写

1、简单属性查询【重要】
	* 单一属性查询，返回结果集属性列表，元素类型和实体类中相应的属性类型一致
	* 多个属性查询,返回的集合元素是对象数组,数组元素的类型和对应的属性在实体类中的类型一致
	  数组的长度取决与select中属性的个数
	* 如果认为返回数组不够对象化，可以采用hql动态实例化Student对象
	参见：SimplePropertyQueryTest.java  

2、实体对象查询【重要】
	* N + 1问题，在默认情况下，使用query.iterate查询，有可以能出现N+1问题
	  所谓的N+1是在查询的时候发出了N+1条sql语句
	  1: 首先发出一条查询对象id列表的sql
	  N: 根据id列表到缓存中查询，如果缓存中不存在与之匹配的数据，那么会根据id发出相应的sql语句
	* list和iterate的区别？
		* list每次都会发出sql语句，list会向缓存中放入数据，而不利用缓存中的数据
		* iterate：在默认情况下iterate利用缓存数据，但如果缓存中不存在数据有可以能出现N+1问题
	参见：SimpleObjectQueryTest1.java/SimpleObjectQueryTest2.java
	
3、条件查询【重要】	
	* 可以采用拼字符串的方式传递参数
	* 可以采用 ？来传递参数（索引从0开始）
	* 可以采用 :参数名 来传递参数
	* 如果传递多个参数，可以采用setParamterList方法
	* 在hql中可以使用数据库的函数，如：date_format
	参见：SimpleConditionQueryTest.java 	
		  
4、hibernate也支持直接使用sql进行查询
	参见：SqlQueryTest.java

5、外置命名查询
	* 在映射文件中采用<query>标签来定义hql
	* 在程序中采用session.getNamedQuery()方法得到hql查询串
	参见：Student.hbm.xml、NameQueryTest.java
	
6、查询过滤器	
	* 在映射文件中定义过滤器参数
	* 在类的映射中使用这些参数
	* 在程序中启用过滤器
	参见：Student.hbm.xml、FilterQueryTest.java
	
7、分页查询【重要】	
	* setFirstResult()，从0开始
	* setMaxResults,每页显示多少条数据
	参见：PageQueryTest.java
			  
8、对象导航查询，在hql中采用 . 进行导航【重要】
	参见：ObjectNavQueryTest.java
	
9、连接查询【重要】
	* 内连
	* 外连接（左连接/右连接）	
	参见：JoinQueryTest.java
	
10、统计查询【重要】
	参见：StatQueryTest.java
	
11、DML风格的操作（尽量少用，因为和缓存不同步）
	参见：DMLQueryTest.java