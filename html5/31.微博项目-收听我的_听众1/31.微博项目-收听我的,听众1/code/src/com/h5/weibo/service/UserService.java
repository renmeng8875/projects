package com.h5.weibo.service;

import java.util.Iterator;

import org.hibernate.Query;
import org.hibernate.Session;
import com.h5.weibo.dao.HibernateUtil;
import com.h5.weibo.model.User;
import com.h5.weibo.utils.EncryptUtil;

public class UserService {
	/**
	 * 登录
	 * @return 登录失败返回null，登录成功就返回user对象
	 */
	public static User login(String userName,String pwd) {
		Session s = HibernateUtil.getCurrentSession();
		
		Query q = s.createQuery("from User where userName=?");
		q.setText(0, userName);
		User u = (User) q.uniqueResult();
		if(u != null) {
			// 比较密码
			String md5Pwd = EncryptUtil.getMD5Str(pwd);
			if(md5Pwd.equals(u.getPwd())) {
				// 登录成功
				return u;
			}
		}
		
		return null;
	}
	
	/**
	 * 注册
	 * @return 0:成功，-1：用户名已经存在,-100:后台异常
	 */
	public static int register(User u) {
		Session s = HibernateUtil.getCurrentSession();
		
		// 判断用户名是否存在(hql)
		Query q = s.createQuery("select id from User where userName=?");
		q.setText(0, u.getUserName());
		Iterator iter = q.iterate();
		if(iter.hasNext())
			return -1;
		
		// 密码进行MD5加密
		u.setPwd(EncryptUtil.getMD5Str(u.getPwd()));
		try {
			HibernateUtil.beginTransation();
			s.save(u);
			HibernateUtil.commitTransation();
			
			return 0;
		}catch(Exception e) {
			e.printStackTrace();
			HibernateUtil.rollbackTransation();
		}finally {
			HibernateUtil.closeSession();
		}
		
		return -100;
	}
	
	
	public static boolean saveUser(User u) {
		Session s = HibernateUtil.getCurrentSession();
		try {
			HibernateUtil.beginTransation();
			s.saveOrUpdate(u);
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
}
