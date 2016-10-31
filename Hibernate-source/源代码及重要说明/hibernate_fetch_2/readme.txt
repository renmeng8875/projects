hibernate抓取策略（单端代理的批量抓取）

设置fetch="join",如：
<many-to-one name="classes" column="classesid" fetch="join"/>

fetch="join",hibernate会通过select语句使用外连接来加载其关联实体或集合

此时lazy会失效


