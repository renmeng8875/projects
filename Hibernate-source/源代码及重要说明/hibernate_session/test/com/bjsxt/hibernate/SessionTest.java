package com.bjsxt.hibernate;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.Transaction;

import junit.framework.TestCase;

public class SessionTest extends TestCase {

	public void testHello1() {
		System.out.println("-----------SessionTest.Hello1()------------");
		//throw new java.lang.RuntimeException();
	}
	
	public void testHello2() {
		System.out.println("-----------SessionTest.testHello2()------------");
		
		//this.assertEquals("hello", "hello111");
	}
	
	public void testSave1() {
		Session session = null;
		Transaction tx = null;
		User user = null;
		try {
			session = HibernateUtils.getSession();
			tx = session.beginTransaction();
			
			//Transient状态
			user = new User();
			user.setName("李四");
			user.setPassword("123");
			user.setCreateTime(new Date());
			user.setExpireTime(new Date());
			
			//persistent状态,当属性发生改变的时候，hibernate会自动和数据库同步
			session.save(user);
			
			user.setName("王五");
			//session.update(user);
			tx.commit();
		}catch(Exception e) {
			e.printStackTrace();
			tx.rollback();
		}finally {
			HibernateUtils.closeSession(session);
		}
		
		//detached状态
		user.setName("张三");
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
	 		//persistent状态
			session.update(user);
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			HibernateUtils.closeSession(session);
		}		
	}
	
	public void testReadByGetMethod1() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			//马上发出查询sql，加载User对象
			User user = (User)session.get(User.class, "402880d01b9bf210011b9bf2a2ff0001");
			System.out.println("user.name=" + user.getName());
			
			//persistent状态,当属性发生改变的时候，hibernate会自动和数据库同步
			user.setName("龙哥");
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			HibernateUtils.closeSession(session);
		}
	}
	
	public void testReadByGetMethod2() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			//采用get加载数据，如果数据库中不存在相应的数据，返回null
			User user = (User)session.get(User.class, "asdfsafsdfdsf");

			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			HibernateUtils.closeSession(session);
		}
	}
	
	public void testReadByLoadMethod1() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			//不会发出查询sql，因为load方法实现了lazy（懒加载或延迟加载）
			//延迟加载：只有真正使用这个对象的时候，才加载（发出sql语句）
			//hibernate延迟加载实现原理是代理方式
			User user = (User)session.load(User.class, "402880d01b9bf210011b9bf2a2ff0001");
			System.out.println("user.name=" + user.getName());
			
			//persistent状态,当属性发生改变的时候，hibernate会自动和数据库同步
			user.setName("发哥");
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			HibernateUtils.closeSession(session);
		}
	}
	
	public void testReadByLoadMethod2() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			//采用load加载数据，如果数据库中没有相应的数据
			//那么抛出ObjectNotFoundException
			User user = (User)session.load(User.class, "55555555");
			
			System.out.println(user.getName());
			
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			throw new java.lang.RuntimeException();
		}finally {
			HibernateUtils.closeSession(session);
		}
	}
	
	public void testUpdate1() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			//手动构造的detached状态的对象
			User user = new User();
			user.setId("402880d01b9be8dc011b9be9b23d0001");
			user.setName("德华");
			
			session.update(user);
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			HibernateUtils.closeSession(session);
		}
	}	
	

	public void testDelete1() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
//			//手动构造的detached状态的对象
//			User user = new User();
//			user.setId("402880d01b9be8dc011b9be9b23d0001");
//			user.setName("德华");
//			session.delete(user);
			
			User user = (User)session.load(User.class, "402880d01b9be8dc011b9be9b23d0001");
			session.delete(user);
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			HibernateUtils.closeSession(session);
		}
		
		//transient状态
	}	
	
}
