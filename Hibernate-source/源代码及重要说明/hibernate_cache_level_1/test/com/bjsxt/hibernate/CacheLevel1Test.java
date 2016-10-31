package com.bjsxt.hibernate;

import java.io.Serializable;

import org.hibernate.Session;

import junit.framework.TestCase;

public class CacheLevel1Test extends TestCase {

	/**
	 * 在同一个session中发出两次load查询
	 */
	public void testCache1() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			Student student = (Student)session.load(Student.class, 1);
			System.out.println("student.name=" + student.getName());
			
			//不会发出sql，因为load使用缓存
			student = (Student)session.load(Student.class, 1);
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
	 * 在同一个session中发出两次get查询
	 */
	public void testCache2() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			Student student = (Student)session.get(Student.class, 1);
			System.out.println("student.name=" + student.getName());
			
			//不会发出sql，因为get使用缓存
			student = (Student)session.get(Student.class, 1);
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
	 * 在同一个session中发出两次iterate查询实体对象
	 */
	public void testCache3() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			Student student = (Student)session.createQuery("from Student s where s.id=1").iterate().next();
			System.out.println("student.name=" + student.getName());
			
			//会发出查询id的sql，不会发出查询实体对象的sql，因为iterate使用缓存
			student = (Student)session.createQuery("from Student s where s.id=1").iterate().next();
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
	 * 在同一个session中发出两次iterate查询实体对象
	 */
	public void testCache4() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			String name = (String)session.createQuery("select s.name from Student s where s.id=1").iterate().next();
			System.out.println("student.name=" + name);
			
			//iterate查询普通属性，一级缓存不会缓存，所以发出sql
			//一级缓存是缓存实体对象的
			name = (String)session.createQuery("select s.name from Student s where s.id=1").iterate().next();
			System.out.println("student.name=" + name);
			
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			HibernateUtils.closeSession(session);
		}
	}			
	
	/**
	 * 开启两个session中发出load查询
	 */
	public void testCache5() {
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
			
			//会发出查询语句，session间不能共享一级缓存的数据
			//因为它会伴随session的生命周期存在和消亡
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
	 * 在同一个session中先save，在发出load查询save过的数据
	 */
	public void testCache6() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			Student stu = new Student();
			stu.setName("王五");
			
			Serializable id = session.save(stu);
			
			//不会发出sql，因为save是使用缓存的
			Student student = (Student)session.load(Student.class, id);
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
	 * 向数据库中批量加入1000条数据
	 */
	public void testCache7() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			for (int i=0; i<1000; i++) {
				Student student = new Student();
				student.setName("s_" + i);
				session.save(student);
				//每20条数据就强制session将数据持久化
				//同时清除缓存，避免大量数据造成内存溢出
				if ( i % 20 == 0) {
					session.flush();
					session.clear();
				}
			}
			
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			HibernateUtils.closeSession(session);
		}
	}	
	
}
