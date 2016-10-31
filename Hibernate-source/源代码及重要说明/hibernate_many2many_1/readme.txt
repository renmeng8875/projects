hibernate多对多关联映射(单向User---->Role)

具体映射方式：
	<set name="roles" table="t_user_role">
		<key column="userid"/>
		<many-to-many class="com.bjsxt.hibernate.Role" column="roleid"/>
	</set>
	