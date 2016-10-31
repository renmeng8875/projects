hibernate抓取策略（集合代理的批量抓取）

设置fetch="join",如：
<set name="students" inverse="true" cascade="all" fetch="join">

fetch="join",hibernate会通过select语句使用外连接来加载其关联实体或集合

此时lazy会失效


