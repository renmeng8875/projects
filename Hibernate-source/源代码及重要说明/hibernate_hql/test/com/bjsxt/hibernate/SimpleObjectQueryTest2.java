package com.bjsxt.hibernate;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;

import junit.framework.TestCase;

/**
 * 实体对象查询
 * @author Administrator
 *
 */
public class SimpleObjectQueryTest2 extends TestCase {

	public void testQuery1() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			/**
			 * 采用list查询发出一条查询语句，取得Student对象数据、
			 * 
			 * Hibernate: select student0_.id as id1_, student0_.name as name1_, 
			 * student0_.createTime as createTime1_, student0_.classesid as classesid1_ 
			 * from t_student student0_
			 * 
			 */
			List students = session.createQuery("from Student").list();
			for (Iterator iter=students.iterator(); iter.hasNext();) {
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
	
	public void testQuery2() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			/**
			 * 出现N+1问题
			 * 
			 * 1：发出查询id列表的sql
			 *   Hibernate: select student0_.id as col_0_0_ from t_student student0_
			 * 
			 * N：在依次发出根据id查询Student对象的sql
			 * Hibernate: select student0_.id as id1_0_, student0_.name as name1_0_, 
			 * student0_.createTime as createTime1_0_, student0_.classesid as classesid1_0_ 
			 * from t_student student0_ where student0_.id=?
			 *  
			 */
			Iterator iter = session.createQuery("from Student").iterate();
			while(iter.hasNext()) {
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
	
	public void testQuery3() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			List students = session.createQuery("from Student").list();
			for (Iterator iter=students.iterator(); iter.hasNext();) {
				Student student = (Student)iter.next();
				System.out.println(student.getName());
			}
			System.out.println("---------------------------------------------");
			
			/**
			 * 不会出现N+1问题
			 * 
			 * 因为list操作已经将Student对象放到了一级缓存中，所以再次使用iterate操作的时候
			 * 它首先发出一条查询id列表的sql，在根据id到缓存中去数据，只有在缓存中找不到相应的
			 * 数据时，才会发出sql到数据库中查询
			 * 
			 */
			Iterator iter = session.createQuery("from Student").iterate();
			while(iter.hasNext()) {
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
	
	public void testQuery4() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			List students = session.createQuery("from Student").list();
			for (Iterator iter=students.iterator(); iter.hasNext();) {
				Student student = (Student)iter.next();
				System.out.println(student.getName());
			}
			System.out.println("---------------------------------------------");
			
			/**
			 * 再次发出查询sql
			 * 
			 * 在默认情况下list每次都会向数据库发出查询对象的sql，除非配置查询缓存，所以下面的list操作
			 * 虽然在一级缓存中已经有了对象数据，但list默认情况下不会利用缓存，而再次发出sql
			 * 
			 * 默认情况下，list会向缓存中放入数据，但不会利用数据
			 * 
			 */
			students = session.createQuery("from Student").list();
			for (Iterator iter=students.iterator(); iter.hasNext();) {
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
