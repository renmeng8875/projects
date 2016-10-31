每个子类映射成一张表

1、理解如何映射
		这种策略是使用joined-subclass标签来定义子类的。父类、子类，每个类都对应一张数据库表。
	在父类对应的数据库表中，实际上会存储所有的记录，包括父类和子类的记录；在子类对应的数据库表中，
	这个表只定义了子类中所特有的属性映射的字段。子类与父类，通过相同的主键值来关联。实现这种策略的时候，
	有如下步骤：
	父类用普通的<class>标签定义即可
	父类不再需要定义discriminator字段
	子类用<joined-subclass>标签定义，在定义joined-subclass的时候，需要注意如下几点：
	Joined-subclass标签的name属性是子类的全路径名
	Joined-subclass标签需要包含一个key标签，这个标签指定了子类和父类之间是通过哪个字段来关联的。
	如：<key column=”PARENT_KEY_ID”/>，这里的column，实际上就是父类的主键对应的映射字段名称。
	Joined-subclass标签，既可以被class标签所包含（这种包含关系正是表明了类之间的继承关系），
	也可以与class标签平行。 当Joined-subclass标签的定义与class标签平行的时候，需要在Joined-subclass
	标签中，添加extends属性，里面的值是父类的全路径名称。子类的其它属性，像普通类一样，定义在joined-subclass标签的内部。
	