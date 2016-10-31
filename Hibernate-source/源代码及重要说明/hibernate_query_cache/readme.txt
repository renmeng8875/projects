hibernate查询缓存

查询缓存是针对普通属性结果集的缓存
对实体对象的结果集只缓存id

查询缓存的生命周期，当前关联的表发生修改，那么查询缓存生命周期结束

查询缓存的配置和使用：
	* 在hibernate.cfg.xml文件中启用查询缓存，如：
	<property name="hibernate.cache.use_query_cache">true</property>
	* 在程序中必须手动启用查询缓存，如：
	query.setCacheable(true);
	