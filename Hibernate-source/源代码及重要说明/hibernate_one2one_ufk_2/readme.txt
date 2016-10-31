hibernate一对一唯一外键关联映射（双向关联Person<---->IdCard）

一对一唯一外键关联双向，需要在另一端（idcard），添加<one-to-one>标签，指示hibernate如何加载
其关联对象，默认根据主键加载person，外键关联映射中，因为两个实体采用的是person的外键维护的关系，
所以不能指定主键加载person，而要根据person的外键加载，所以采用如下映射方式：
<one-to-one name="person" property-ref="idCard"/>

