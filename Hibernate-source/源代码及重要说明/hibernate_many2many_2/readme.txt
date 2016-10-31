hibernate多对多关联映射(双向User<---->Role)

映射方法：
		<set name="roles" table="t_user_role">
			<key column="userid"/>
			<many-to-many class="com.bjsxt.hibernate.Role" column="roleid"/>
		</set>
table属性值必须和单向关联中的table属性值一致
<key>中column属性值要与单向关联中的<many-to-many>标签中的column属性值一致
在<many-to-many>中的column属性值要与单向关联中<key>标签的column属性值一致
	