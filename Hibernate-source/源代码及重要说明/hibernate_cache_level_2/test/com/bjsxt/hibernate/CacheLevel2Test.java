package com.bjsxt.hibernate;

import java.io.Serializable;

import org.hibernate.CacheMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import junit.framework.TestCase;

public class CacheLevel2Test extends TestCase {

	/**
	 * 开启两个session，分别调用load
	 */
	public void testCache1() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			Student student = (Student)session.load(Student.class, 1);
			System.out.println("student.name=" + student.getName());
			
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			HibernateUtils.closeSession(session);
		}
		
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			//不会发出sql，因为开启了二级缓存，session是共享二级缓存的
			Student student = (Student)session.load(Student.class, 1);
			System.out.println("student.name=" + student.getName());
			
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			HibernateUtils.closeSession(session);
		}
		
	}	
	
	/**
	 * 开启两个session，分别调用get
	 */
	public void testCache2() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			Student student = (Student)session.get(Student.class, 1);
			System.out.println("student.name=" + student.getName());
			
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			HibernateUtils.closeSession(session);
		}
		
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			//不会发出sql，因为开启了二级缓存，session是共享二级缓存的
			Student student = (Student)session.get(Student.class, 1);
			System.out.println("student.name=" + student.getName());
			
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			HibernateUtils.closeSession(session);
		}
	}	
	
	/**
	 * 开启两个session，分别调用load,在使用SessionFactory清除二级缓存
	 */
	public void testCache3() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			Student student = (Student)session.load(Student.class, 1);
			System.out.println("student.name=" + student.getName());
			
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			HibernateUtils.closeSession(session);
		}
		
		//管理二级缓存
		SessionFactory factory = HibernateUtils.getSessionFactory();
		//factory.evict(Student.class);
		factory.evict(Student.class, 1);
		
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			//会发出查询sql，因为二级缓存中的数据被清除了
			Student student = (Student)session.load(Student.class, 1);
			System.out.println("student.name=" + student.getName());
			
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			HibernateUtils.closeSession(session);
		}
	}
	
	
	/**
	 * 一级缓存和二级缓存的交互
	 */
	public void testCache4() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			//仅向二级缓存读数据，而不向二级缓存写数据
			session.setCacheMode(CacheMode.GET);
			Student student = (Student)session.load(Student.class, 1);
			System.out.println("student.name=" + student.getName());
			
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			HibernateUtils.closeSession(session);
		}
		
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			//发出sql语句，因为session设置了CacheMode为GET，所以二级缓存中没有数据
			Student student = (Student)session.load(Student.class, 1);
			System.out.println("student.name=" + student.getName());
			
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			HibernateUtils.closeSession(session);
		}
		
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			//只向二级缓存写数据，而不从二级缓存读数据
			session.setCacheMode(CacheMode.PUT);
			
			//会发出查询sql，因为session将CacheMode设置成了PUT
			Student student = (Student)session.load(Student.class, 1);
			System.out.println("student.name=" + student.getName());
			
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			HibernateUtils.closeSession(session);
		}
		
	}	
}
