package com.bjsxt.hibernate;

import org.hibernate.Session;

import junit.framework.TestCase;

public class Many2OneTest extends TestCase {

	public void testSave1() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			Group group = new Group();
			group.setName("尚学堂");
			
			User user1 = new User();
			user1.setName("菜10");
			user1.setGroup(group);
			
			User user2 = new User();
			user2.setName("容祖儿");
			user2.setGroup(group);
			
			//不能成功保存，抛出TransientObjectException异常
			//因为Group为Tran	sient状态，oid没有分配值
			//persistent状态的对象是不能引用transient状态的对象的
			session.save(user1);
			session.save(user2);
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			HibernateUtils.closeSession(session);
		}
	}	
	
	public void testSave2() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			Group group = new Group();
			group.setName("尚学堂");
			
			session.save(group);
			
			User user1 = new User();
			user1.setName("菜10");
			user1.setGroup(group);
			
			User user2 = new User();
			user2.setName("容祖儿");
			user2.setGroup(group);
			
			//可以正确存储
			session.save(user1);
			session.save(user2);
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			HibernateUtils.closeSession(session);
		}
	}	
	
	public void testSave3() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			Group group = new Group();
			group.setName("尚学堂");
			
			User user1 = new User();
			user1.setName("菜10");
			user1.setGroup(group);
			
			User user2 = new User();
			user2.setName("容祖儿");
			user2.setGroup(group);
			
			//不会抛出异常，因为采用了cascade属性，所以它会先保存Group
			//采用cascade属性是解决TransientObjectException异常的一种手段			
			session.save(user1);
			session.save(user2);
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			HibernateUtils.closeSession(session);
		}
	}	
	
	public void testLoad1() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			User user = (User)session.load(User.class, 3);
			System.out.println("user.name=" + user.getName());
			System.out.println("user.group.name=" + user.getGroup().getName());
			
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			HibernateUtils.closeSession(session);
		}
	}	
	
}
