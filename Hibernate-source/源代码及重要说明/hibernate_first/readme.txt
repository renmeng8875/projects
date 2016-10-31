第一个hibernate项目

1、新建java项目

2、创建User Library,加入如下jar
	* HIBERNATE_HOME/hibernate3.jar
	* HIBERNATE_HOME/lib/*.jar
	* MySql jdbc驱动
	
3、创建hibernate配置文件hibernate.cfg.xml，为了便于调试最好加入log4j配置文件

4、定义实体类

5、定义User类的映射文件User.hbm.xml

6、将User.hbml.xml文件加入到hibernate.cfg.xml文件中

7、编写hbm2ddl工具类，将实体类生成数据库表

8、开发客户端
	
为了方便跟踪sql执行，在hibernate.cfg.xml文件中加入<property name="hibernate.show_sql">true</property>

