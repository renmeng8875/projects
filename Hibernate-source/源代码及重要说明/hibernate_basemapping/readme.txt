hibernate基本映射

实体类---表
实体类中的普通属性---表字段

采用<class>标签映射成数据库表，通过<property>标签将普通属性映射成表字段
所谓普通属性指不包括自定义类、集合和数组等

注意：如果实体类和实体类中的属性和sql中的关键字重复，必须采用table或column重新命名

实体类的设计原则：
	* 实现一个默认的（即无参数的）构造方法（constructor）
	* 提供一个标识属性（identifier property）（可选）
	* 使用非final的类 (可选)
	* 为持久化字段声明访问器(accessors)	

主键生成策略：
	uuid、native和assigned