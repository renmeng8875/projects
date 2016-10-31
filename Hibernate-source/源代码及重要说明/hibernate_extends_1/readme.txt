每棵继承树映射成一张表

1、理解如何映射
		因为类继承树肯定是对应多个类，要把多个类的信息存放在一张表中，必须有某种机制来区分哪些记录是属于哪个类的。
	这种机制就是，在表中添加一个字段，用这个字段的值来进行区分。用hibernate实现这种策略的时候，有如下步骤：
	父类用普通的<class>标签定义
	在父类中定义一个discriminator，即指定这个区分的字段的名称和类型
	如：<discriminator column=”XXX” type=”string”/>
	子类使用<subclass>标签定义，在定义subclass的时候，需要注意如下几点：
	Subclass标签的name属性是子类的全路径名
	在Subclass标签中，用discriminator-value属性来标明本子类的discriminator字段（用来区分不同类的字段）
	的值Subclass标签，既可以被class标签所包含（这种包含关系正是表明了类之间的继承关系），也可以与class标
	签平行。 当subclass标签的定义与class标签平行的时候，需要在subclass标签中，添加extends属性，里面的值
	是父类的全路径名称。子类的其它属性，像普通类一样，定义在subclass标签的内部。

2、理解如何存储
	存储的时候hibernate会自动将鉴别字段值插入到数据库中，在加载数据的时候，hibernate能根据这个鉴别值
	正确的加载对象
	
多态查询：在hibernate加载数据的时候能鉴别出正真的类型（instanceOf）

get支持多态查询
load只有在lazy=false，才支持多态查询 
hql支持多态查询	
	
	