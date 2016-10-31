hihernate一对多关联映射（双向Classes<----->Student）

一对多双向关联映射：
	* 在一一端的集合上使用<key>，在对方表中加入一个外键指向一一端
	* 在多一端采用<many-to-one>
	
注意：<key>标签指定的外键字段必须和<many-to-one>指定的外键字段一致，否则引用字段的错误
	
如果在”一“一端维护一对多关联关系，hibernate会发出多余的udpate语句，所以我们一般在多
的一端来维护关联关系

关于inverse属性：
	inverse主要用在一对多和多对多双向关联上，inverse可以被设置到集合标签<set>上，
	默认inverse为false，所以我们可以从”一“一端和”多“一端维护关联关系，
	如果设置成inverse为true，则我们只能从多一端来维护关联关系
	
	注意：inverse属性，只影响数据的存储，也就是持久化
		
inverse和cascade
	* inverse是关联关系的控制方向
	* cascade操作上的连锁反应 