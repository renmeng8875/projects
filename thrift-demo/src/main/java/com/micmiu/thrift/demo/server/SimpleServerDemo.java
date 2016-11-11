package com.micmiu.thrift.demo.server;
 
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;

import com.micmiu.thrift.demo.HelloWorldImpl;
import com.micmiu.thrift.demo.HelloWorldService;
import com.micmiu.thrift.demo.HelloWorldService.Iface;
import com.micmiu.thrift.demo.HelloWorldService.Processor;
import com.micmiu.thrift.demo.UserService;
import com.micmiu.thrift.demo.UserServiceImpl;
 
/**
 * blog http://www.micmiu.com
 *
 * @author Michael
 *
 */
public class SimpleServerDemo {
	public static final int SERVER_PORT = 8090;
 
	public void startServer() {
		try {
			System.out.println("HelloWorld TSimpleServer start ....");
 
			TProcessor tprocessor = new HelloWorldService.Processor<HelloWorldService.Iface>(new HelloWorldImpl());
			
			//TProcessor tprocessor = new UserService.Processor<UserService.Iface>(new UserServiceImpl());
			
			TServerSocket serverTransport = new TServerSocket(SERVER_PORT);
			TServer.Args tArgs = new TServer.Args(serverTransport);
			tArgs.processor(tprocessor);
			tArgs.protocolFactory(new TBinaryProtocol.Factory());
			// tArgs.protocolFactory(new TCompactProtocol.Factory());
			// tArgs.protocolFactory(new TJSONProtocol.Factory());
			// 简单的单线程服务模型，一般用于测试
			TServer server = new TSimpleServer(tArgs);
			
			server.serve();
 
		} catch (Exception e) {
			System.out.println("Server start error!!!");
			e.printStackTrace();
		}
	}
 
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SimpleServerDemo server = new SimpleServerDemo();
		server.startServer();
	}
 
}