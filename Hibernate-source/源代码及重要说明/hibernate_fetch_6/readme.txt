hibernate抓取策略，,batch-szie在<class>上的应用

batch-size属性，可以批量加载实体类，参见：Classes.hbm.xml
<class name="Classes" table="t_classes" batch-size="3">
