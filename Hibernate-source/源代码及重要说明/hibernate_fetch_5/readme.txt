hibernate抓取策略（集合代理的批量抓取）

设置fetch="subselect",如：
<set name="students" inverse="true" cascade="all" fetch="subselect">

fetch="subselect",另外发送一条select语句抓取在前面查询到的所有实体对象的关联集合




