hibernate一级缓存
	
一级缓存很短和session的生命周期一致，一级缓存也叫session级的缓存或事务级缓存

那些方法支持一级缓存：
	* get()
	* load()
	* iterate（查询实体对象）
	
如何管理一级缓存：
	* session.clear(),session.evict()
	
如何避免一次性大量的实体数据入库导致内存溢出
	* 先flush，再clear
	
如果数据量特别大，考虑采用jdbc实现，如果jdbc也不能满足要求可以考虑采用数据本身的特定导入工具					