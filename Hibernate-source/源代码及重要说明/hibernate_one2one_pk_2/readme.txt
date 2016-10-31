hibernate一对一主键关联映射（双向关联Person<---->IdCard）

需要在idcard映射文件中加入<one-to-one>标签指向person，指示hibernate如何加载person
默认根据主键加载
