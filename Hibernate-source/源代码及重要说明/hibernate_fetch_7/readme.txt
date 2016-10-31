hibernate抓取策略,batch-szie在集合上的应用


batch-size属性，可以批量加载实体类，参见：Classes.hbm.xml
<set name="students" inverse="true" cascade="all" batch-size="5">

