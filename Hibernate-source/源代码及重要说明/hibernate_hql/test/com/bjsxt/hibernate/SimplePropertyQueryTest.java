package com.bjsxt.hibernate;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;

import junit.framework.TestCase;

/**
 * 简单属性查询
 * @author Administrator
 *
 */
public class SimplePropertyQueryTest extends TestCase {
	
	/**
	 * 单一属性查询
	 */
	public void testQuery1() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			//返回结果集属性列表，元素类型和实体类中相应的属性类型一致
			List students = session.createQuery("select name from Student").list();
			for (Iterator iter=students.iterator(); iter.hasNext();) {
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
	 * 多个属性查询
	 */
	public void testQuery2() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			//查询多个属性，其集合元素是对象数组
			//数组元素的类型和对应的属性在实体类中的类型一致
			//数组的长度取决与select中属性的个数
			List students = session.createQuery("select id, name from Student").list();
			for (Iterator iter=students.iterator(); iter.hasNext();) {
				Object[] obj = (Object[])iter.next();
				System.out.println(obj[0] + "," + obj[1]);
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
	 * 返回Student实体对象
	 */
	public void testQuery3() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			//如果认为返回数组不够对象化，可以采用hql动态实例化Student对象
			//此时list中为Student对象集合
			List students = session.createQuery("select new Student(id, name) from Student").list();
			for (Iterator iter=students.iterator(); iter.hasNext();) {
				Student student = (Student)iter.next();
				System.out.println(student.getId() + "," + student.getName());
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
	 * 使用别名
	 */
	public void testQuery4() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			//可以使用别名
			List students = session.createQuery("select s.id, s.name from Student s").list();
			for (Iterator iter=students.iterator(); iter.hasNext();) {
				Object[] obj = (Object[])iter.next();
				System.out.println(obj[0] + "," + obj[1]);
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
	 * 使用别名
	 */
	public void testQuery5() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			//可以使用as命名别名
			List students = session.createQuery("select s.id, s.name from Student as s").list();
			for (Iterator iter=students.iterator(); iter.hasNext();) {
				Object[] obj = (Object[])iter.next();
				System.out.println(obj[0] + "," + obj[1]);
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
