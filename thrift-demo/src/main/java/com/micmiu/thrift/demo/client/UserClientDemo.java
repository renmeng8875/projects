package com.micmiu.thrift.demo.client;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import com.micmiu.thrift.demo.User;
import com.micmiu.thrift.demo.UserService;

public class UserClientDemo {
	public static final String SERVER_IP = "localhost";
	public static final int SERVER_PORT = 8090;
	public static final int TIMEOUT = 30000;
 
	/**
	 *
	 * @param userName
	 */
	public void startClient(String userName) {
		TTransport transport = null;
		try {
			transport = new TSocket(SERVER_IP, SERVER_PORT, TIMEOUT);
			// 协议要和服务端一致
			TProtocol protocol = new TBinaryProtocol(transport);
			// TProtocol protocol = new TCompactProtocol(transport);
			// TProtocol protocol = new TJSONProtocol(transport);
			UserService.Client client = new UserService.Client(protocol);
			transport.open();
			User u = new User();
			u.setId(123456);
			u.setUsername(userName);
			u.setAge(18);
			client.saveUser(u);
			User result = client.getUser(123456);
			System.out.println("come from server: " + result.toString());
		} catch (TTransportException e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
		} finally {
			if (null != transport) {
				transport.close();
			}
		}
	}
 
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		UserClientDemo client = new UserClientDemo();
		client.startClient("renmeng");
 
	}
 
}
