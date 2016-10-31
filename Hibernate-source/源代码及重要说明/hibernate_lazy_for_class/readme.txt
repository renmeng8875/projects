hibernate lazy策略可以使用在：
	* <class>标签上，可以取值：true/false
	* <property>标签上，可以取值：true/false需要类增强工具
	* <set><list>标签上，可以取值：true/false/extra
	* <one-to-one><many-to-one>单端关联上，可以取值：false/proxy/noproxy
	
lazy概念：只有真正使用该对象时，才会创建，对于hibernate而言，正真使用的时候才会发出sql

hibernate支持lazy策略只有在session打开状态下有效

<class>标签上的lazy特性只对普通属性起作用


	
	