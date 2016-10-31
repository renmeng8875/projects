package com.bjsxt.hibernate;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import org.hibernate.CacheMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import junit.framework.TestCase;

public class CacheLevel2Test extends TestCase {

	/**
	 * 开启查询缓存，关闭二级缓存
	 * 
	 * 开启一个session，分别调用query.list
	 */
	public void testCache1() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			Query query = session.createQuery("select s.name from Student s");
			//启用查询查询缓存
			query.setCacheable(true);
			
			List names = query.list(); 
			for (Iterator iter=names.iterator();iter.hasNext(); ) {
				String name = (String)iter.next();
				System.out.println(name);
			}
			
			System.out.println("-------------------------------------");
			query = session.createQuery("select s.name from Student s");
			//启用查询查询缓存
			query.setCacheable(true);
			
			//没有发出查询sql，因为启用了查询缓存
			names = query.list(); 
			for (Iterator iter=names.iterator();iter.hasNext(); ) {
				String name = (String)iter.next();
				System.out.println(name);
			}

			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			HibernateUtils.closeSession(session);
		}
	}	
	
	/**
	 * 开启查询缓存，关闭二级缓存
	 * 
	 * 开启两个session，分别调用query.list
	 */
	public void testCache2() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			Query query = session.createQuery("select s.name from Student s");
			//启用查询查询缓存
			query.setCacheable(true);
			
			List names = query.list(); 
			for (Iterator iter=names.iterator();iter.hasNext(); ) {
				String name = (String)iter.next();
				System.out.println(name);
			}
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			HibernateUtils.closeSession(session);
		}
		
		System.out.println("-------------------------------------");
		
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			Query query = session.createQuery("select s.name from Student s");
			//启用查询查询缓存
			query.setCacheable(true);
			
			//不会发出查询sql，因为查询缓存的生命周期和session无关
			List names = query.list(); 
			for (Iterator iter=names.iterator();iter.hasNext(); ) {
				String name = (String)iter.next();
				System.out.println(name);
			}
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			HibernateUtils.closeSession(session);
		}
	}		
	
	/**
	 * 开启查询缓存，关闭二级缓存
	 * 
	 * 开启两个session，分别调用query.iterate
	 */
	public void testCache3() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			Query query = session.createQuery("select s.name from Student s");
			//启用查询查询缓存
			query.setCacheable(true);
			 
			for (Iterator iter=query.iterate();iter.hasNext(); ) {
				String name = (String)iter.next();
				System.out.println(name);
			}
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			HibernateUtils.closeSession(session);
		}
		
		System.out.println("-------------------------------------");
		
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			Query query = session.createQuery("select s.name from Student s");
			//启用查询查询缓存
			query.setCacheable(true);
			 
			//查询缓存只对query.list()起作用，query.iterate不起作用，也就是query.iterate不使用
			//查询缓存
			for (Iterator iter=query.iterate();iter.hasNext(); ) {
				String name = (String)iter.next();
				System.out.println(name);
			}
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			HibernateUtils.closeSession(session);
		}
	}
	
	/**
	 * 关闭查询缓存，关闭二级缓存
	 * 
	 * 开启两个session，分别调用query.list查询实体对象
	 */
	public void testCache4() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			Query query = session.createQuery("select s from Student s");
			//启用查询查询缓存
			//query.setCacheable(true);
			
			List students = query.list(); 
			for (Iterator iter=students.iterator();iter.hasNext(); ) {
				Student student = (Student)iter.next();
				System.out.println(student.getName());
			}
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			HibernateUtils.closeSession(session);
		}
		
		System.out.println("-------------------------------------");
		
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			Query query = session.createQuery("select s from Student s");
			//启用查询查询缓存
			//query.setCacheable(true);
			//会发出查询sql，因为list默认每次都会发出查询sql
			List students = query.list(); 
			for (Iterator iter=students.iterator();iter.hasNext(); ) {
				Student student = (Student)iter.next();
				System.out.println(student.getName());
			}
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			HibernateUtils.closeSession(session);
		}
	}		

	/**
	 * 开启查询缓存，关闭二级缓存
	 * 
	 * 开启两个session，分别调用query.list查询实体对象
	 */
	public void testCache5() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			Query query = session.createQuery("select s from Student s");
			//启用查询查询缓存
			query.setCacheable(true);
			
			List students = query.list(); 
			for (Iterator iter=students.iterator();iter.hasNext(); ) {
				Student student = (Student)iter.next();
				System.out.println(student.getName());
			}
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			HibernateUtils.closeSession(session);
		}
		
		System.out.println("-------------------------------------");
		
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			Query query = session.createQuery("select s from Student s");
			//启用查询查询缓存
			query.setCacheable(true);
			
			//会发出n条查询语句，因为开启了查询缓存，关闭了二级缓存，那么查询缓存会缓存实体对象的id
			//所以hibernate会根据实体对象的id去查询相应的实体，如果缓存中不存在相应的
			//实体那么将发出根据实体id查询的sql语句，否则不会发出sql使用缓存中的数据
			List students = query.list(); 
			for (Iterator iter=students.iterator();iter.hasNext(); ) {
				Student student = (Student)iter.next();
				System.out.println(student.getName());
			}
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			HibernateUtils.closeSession(session);
		}
	}	
	
	/**
	 * 开启查询缓存，开启二级缓存
	 * 
	 * 开启两个session，分别调用query.list查询实体对象
	 */
	public void testCache6() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			Query query = session.createQuery("select s from Student s");
			//启用查询查询缓存
			query.setCacheable(true);
			
			List students = query.list(); 
			for (Iterator iter=students.iterator();iter.hasNext(); ) {
				Student student = (Student)iter.next();
				System.out.println(student.getName());
			}
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			HibernateUtils.closeSession(session);
		}
		
		System.out.println("-------------------------------------");
		
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			Query query = session.createQuery("select s from Student s");
			//启用查询查询缓存
			query.setCacheable(true);
			
			//不会发出查询sql，因为开启了二级缓存和查询缓存，查询缓存缓存了实体对象的id列表
			//hibernate会根据实体对象的id列表到二级缓存中取得相应的数据
			List students = query.list(); 
			for (Iterator iter=students.iterator();iter.hasNext(); ) {
				Student student = (Student)iter.next();
				System.out.println(student.getName());
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
