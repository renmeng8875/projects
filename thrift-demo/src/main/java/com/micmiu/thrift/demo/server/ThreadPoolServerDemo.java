package com.micmiu.thrift.demo.server;
 
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;

import com.micmiu.thrift.demo.HelloWorldImpl;
import com.micmiu.thrift.demo.HelloWorldService;
import com.micmiu.thrift.demo.HelloWorldService.Iface;
import com.micmiu.thrift.demo.HelloWorldService.Processor;
 
/**
 * blog http://www.micmiu.com
 *
 * @author Michael
 *
 */
public class ThreadPoolServerDemo {
	public static final int SERVER_PORT = 8090;
 
	public void startServer() {
		try {
			System.out.println("TThreadPoolServer start ....");
 
			TProcessor tprocessor = new HelloWorldService.Processor<HelloWorldService.Iface>(new HelloWorldImpl());
 
			 TServerSocket serverTransport = new TServerSocket(SERVER_PORT);
			 TThreadPoolServer.Args ttpsArgs = new TThreadPoolServer.Args(serverTransport);
			 ttpsArgs.processor(tprocessor);
			 ttpsArgs.protocolFactory(new TBinaryProtocol.Factory());
 
			// 线程池服务模型，使用标准的阻塞式IO，预先创建一组线程处理请求。
			 TServer server = new TThreadPoolServer(ttpsArgs);
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
		ThreadPoolServerDemo server = new ThreadPoolServerDemo();
		server.startServer();
	}
 
}