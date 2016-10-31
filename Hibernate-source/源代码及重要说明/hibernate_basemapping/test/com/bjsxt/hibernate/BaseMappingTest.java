package com.bjsxt.hibernate;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.Transaction;

import junit.framework.TestCase;

public class BaseMappingTest extends TestCase {

	public void testSave1() {
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateUtils.getSession();
			tx = session.beginTransaction();

			User1 user = new User1();
			user.setName("李四");
			user.setPassword("123");
			user.setCreateTime(new Date());
			user.setExpireTime(new Date());
			
			session.save(user);
			tx.commit();
		}catch(Exception e) {
			e.printStackTrace();
			tx.rollback();
		}finally {
			HibernateUtils.closeSession(session);
		}
	}
	
	public void testSave2() {
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateUtils.getSession();
			tx = session.beginTransaction();

			User2 user = new User2();
			user.setName("张三1");
			user.setPassword("123");
			user.setCreateTime(new Date());
			user.setExpireTime(new Date());
			
			session.save(user);
			tx.commit();
		}catch(Exception e) {
			e.printStackTrace();
			tx.rollback();
		}finally {
			HibernateUtils.closeSession(session);
		}
	}
	
	public void testSave3() {
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateUtils.getSession();
			tx = session.beginTransaction();

			User3 user = new User3();
			user.setId("001");
			user.setName("张三");
			user.setPassword("123");
			user.setCreateTime(new Date());
			user.setExpireTime(new Date());
			
			session.save(user);
			tx.commit();
		}catch(Exception e) {
			e.printStackTrace();
			tx.rollback();
		}finally {
			HibernateUtils.closeSession(session);
		}
	}	
}
