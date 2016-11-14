package com.micmiu.thrift.demo.client;

import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;

import com.micmiu.thrift.demo.User;
import com.micmiu.thrift.demo.UserService;
import com.micmiu.thrift.demo.UserService.Client;
import com.micmiu.thrift.demo.validator.CollectSinkValidator;
import com.yy.ent.mobile.thrift.client.RandomThriftClient;
import com.yy.ent.mobile.thrift.client.failover.FailoverChecker;
import com.yy.ent.mobile.thrift.client.pool.DefaultThriftConnectionPool;
import com.yy.ent.mobile.thrift.client.pool.ThriftConnectionFactory;

public class ClientPool {
	
	public static Client bulidTClient(){
		GenericKeyedObjectPoolConfig lbsPoolConfig = new GenericKeyedObjectPoolConfig();
		lbsPoolConfig.setMaxIdlePerKey(3);
		lbsPoolConfig.setMaxTotal(3);
		lbsPoolConfig.setMaxTotal(50);
		lbsPoolConfig.setMaxIdlePerKey(1);
		lbsPoolConfig.setTestOnBorrow(true);
		lbsPoolConfig.setJmxEnabled(false);
		
		
		ThriftConnectionFactory lbsPoolfactory = new ThriftConnectionFactory(3000);
		DefaultThriftConnectionPool lbsPoolProvider = new DefaultThriftConnectionPool(lbsPoolfactory,lbsPoolConfig);
		CollectSinkValidator connValidator = new CollectSinkValidator();
		FailoverChecker lbsFailoverChecker = new FailoverChecker(connValidator);
		RandomThriftClient lbsThriftClient = new RandomThriftClient(lbsFailoverChecker,lbsPoolProvider,"127.0.0.1:8090");
		
		Client client = null;
		try {
			client = lbsThriftClient.iface(UserService.Client.class);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return client;
	}
	
	public static void main(String[] args) throws Exception {
		Client client =  bulidTClient();
		User result = client.getUser(123456);
		System.out.println(result);
		
	}
	
	

}
