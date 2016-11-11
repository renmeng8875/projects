package com.micmiu.thrift.demo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.thrift.TException;

public  class UserServiceImpl implements UserService.Iface{

	private static Map<Integer,User> db = new ConcurrentHashMap<Integer, User>(10);
	
	@Override
	public void saveUser(User user) throws TException {
		db.put(user.getId(), user);
		
	}

	@Override
	public User getUser(int id) throws TException {
		User u = db.get(id);
		u.setUsername("server-db"+u.getUsername());
		return u;
	}

	

}
