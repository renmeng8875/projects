hibernate抓取策略（单端代理的批量抓取）

保持默认，同fetch="select",如：
<many-to-one name="classes" column="classesid" fetch="select"/>

fetch="select",另外发送一条select语句抓取当前对象关联实体或集合
