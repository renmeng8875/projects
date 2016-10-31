每个具体类映射成一张表

1、理解如何映射
	这种策略是使用union-subclass标签来定义子类的。每个子类对应一张表，而且这个表的信息是完备的，
	即包含了所有从父类继承下来的属性映射的字段（这就是它跟joined-subclass的不同之处，
	joined-subclass定义的子类的表，只包含子类特有属性映射的字段）。实现这种策略的时候，有如下步骤：
	父类用普通<class>标签定义即可
	子类用<union-subclass>标签定义，在定义union-subclass的时候，需要注意如下几点：
	Union-subclass标签不再需要包含key标签（与joined-subclass不同）
	Union-subclass标签，既可以被class标签所包含（这种包含关系正是表明了类之间的继承关系），
	也可以与class标签平行。 当Union-subclass标签的定义与class标签平行的时候，需要在Union-subclass
	标签中，添加extends属性，里面的值是父类的全路径名称。子类的其它属性，像普通类一样，
	定义在Union-subclass标签的内部。这个时候，虽然在union-subclass里面定义的只有子类的属性，
	但是因为它继承了父类，所以，不需要定义其它的属性，在映射到数据库表的时候，依然包含了父类的所
	有属性的映射字段。
	
注意：在保存对象的时候id是不能重复的（不能使用自增生成主键）	