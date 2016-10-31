hibernate二级缓存

二级缓存也称进程级的缓存或SessionFactory级的缓存，二级缓存可以被所有的session共享
二级缓存的生命周期和SessionFactory的生命周期一致，SessionFactory可以管理二级缓存

二级缓存的配置和使用：
	* 将echcache.xml文件拷贝到src下
	* 开启二级缓存，修改hibernate.cfg.xml文件
		<property name="hibernate.cache.use_second_level_cache">true</property>
	* 指定缓存产品提供商，修改hibernate.cfg.xml文件
		<property name="hibernate.cache.provider_class">org.hibernate.cache.EhCacheProvider</property>
	* 指定那些实体类使用二级缓存（两种方法）
		* 在映射文件中采用<cache>标签
		* 在hibernate.cfg.xml文件中，采用<class-cache>标签
		
二级缓存是缓存实体对象的

了解一级缓存和二级缓存的交互		
		
				 
					