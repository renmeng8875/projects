hibernate一对一主键关联映射（单向关联Person---->IdCard）

一对一主键关联映射:让两个实体对象的id保持相同，这样可以避免多余的字段被创建

具体映射：

	<id name="id">
		<!-- person的主键来源idCard，也就是共享idCard的主键 -->
		<generator class="foreign">
			<param name="property">idCard</param>
		</generator>
	</id>
	<property name="name"/>
	<!-- one-to-one标签的含义，指示hibernate怎么加载它的关联对象，默认根据主键加载，
	constrained="true"，	表明当前主键上存在一个约束，person的主键作为外键参照了idCard	
	 -->
	<one-to-one name="idCard" constrained="true"/>
