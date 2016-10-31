hibernate多对一关联映射

关联映射的本质：
	* 将关联关系映射到数据库，所谓的关联关系是对象模型在内存中的一个或多个引用

<many-to-one>会在多的一端加入一个外键，指向一的一端，这个外键是由<many-to-one>
中的column属性定义的，如果忽略了这个属性那么默认的外键与实体的属性一致

<many-to-one>标签的定义示例：
	 * <many-to-one name="group" column="groupid"/>
	 
理解级联的含义？
	* 是对象的连锁操作	 
	 	
	