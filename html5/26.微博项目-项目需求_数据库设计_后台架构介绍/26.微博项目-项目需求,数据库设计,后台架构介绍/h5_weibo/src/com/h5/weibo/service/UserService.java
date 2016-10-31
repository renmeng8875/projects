package com.h5.weibo.service;

import org.hibernate.Session;

import com.h5.weibo.dao.HibernateUtil;
import com.h5.weibo.model.User;

public class UserService {
	/**
	 * 添加用户
	 * @param user
	 * @return
	 */
	public static boolean addUser(User user) {
		try {
			Session s = HibernateUtil.getCurrentSession();
			HibernateUtil.beginTransation();
			s.save(user);
			HibernateUtil.commitTransation();
			
			return true;
		}catch(Exception e) {
			e.printStackTrace();
			HibernateUtil.rollbackTransation();
		}finally {
			HibernateUtil.closeSession();
		}
		
		return false;
	}
	
	public static void main(String[] agrs) {
		User u = new User();
		u.setUserName("BR");
		u.setNickName("h5+css3");
		u.setPwd("1234");
		
		boolean b = addUser(u);
		System.out.print(b);
		System.out.print(u.getId());
	}
}
