<%@ page language="java" contentType="text/cache-manifest; charset=UTF-8" pageEncoding="UTF-8"%>
CACHE MANIFEST
# #开头表示是注释
# 第一行必须是CACHE MANIFEST
# 注释和中间的空行将被忽视

# CACHE: 组表示下面的Url资源都是需要被缓存的，
# 可以是绝对路径,也可以是相对路径
CACHE:
app.js
http://www.baidu.com/img/baidu_sylogo1.gif

# NETWORK:组表示下面的资源是网络白名单，每次请求都通过网络
# 而不使用application cache进行缓存
# 一般ajax请求都需要加在白名单中，不然会请求不到
# 
NETWORK:
ajax.jsp

# 当然也可以设置除了CACHE:中指定的资源外所有资源在网络白名单中,设置如下：
# NETWORK:
# *

# FALLBACK用于指定候补页面
# 下面的表示当/html5/这个目录下的文件没找到(404)的时候，就用offline.html代替
FALLBACK:
/html5/ /offline.html