生成文件命令
thrift-0.9.3.exe -r -gen java demoHello.thrift

//此工程中加入了自定义实现的thrift客户端对象池，以提高性能