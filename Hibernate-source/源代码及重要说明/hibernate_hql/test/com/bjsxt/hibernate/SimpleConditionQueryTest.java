package com.bjsxt.hibernate;

import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import junit.framework.TestCase;

/**
 * 条件查询
 * @author Administrator
 *
 */
public class SimpleConditionQueryTest extends TestCase {

	public void testQuery1() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			//可以拼字符串
			List students = session.createQuery("select s.id, s.name from Student s where s.name like '%1%'").list();
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
	
	public void testQuery2() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
//			Query query = session.createQuery("select s.id, s.name from Student s where s.name like ?");
//			query.setParameter(0, "%1%");
//			List students = query.list();
			
			//可以使用？方式传递参数
			//参数的索引从0开始
			//传递的参数值，不用单引号引起来
			//注意方法链编程
			List students = session.createQuery("select s.id, s.name from Student s where s.name like ?")
			       					.setParameter(0, "%1%")
			       					.list();
			
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

	public void testQuery3() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			//使用 ：参数名称 的方式传递参数值
			List students = session.createQuery("select s.id, s.name from Student s where s.name like :myname")
			       					.setParameter("myname", "%1%")
			       					.list();
			
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
	
	public void testQuery4() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			//使用 ：参数名称 的方式传递参数值
			List students = session.createQuery("select s.id, s.name from Student s where s.name like :myname and s.id=:myid")
			       					.setParameter("myname", "%1%")
			       					.setParameter("myid", 12)
			       					.list();
			
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
	
	public void testQuery5() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			//支持in，需要使用setParameterList进行参数传递
			List students = session.createQuery("select s.id, s.name from Student s where s.id in(:myids)")
									.setParameterList("myids", new Object[]{1, 2, 3, 4, 5})
			       					.list();
			
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

	public void testQuery6() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			//查询2008年2月创建的学生
			List students = session.createQuery("select s.id, s.name from Student s where date_format(s.createTime, '%Y-%m')=?")
									.setParameter(0, "2008-02")
			       					.list();
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

	public void testQuery7() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			//查询2008-01-10到2008-02-15创建的学生
			List students = session.createQuery("select s.id, s.name from Student s where s.createTime between ? and ?")
									.setParameter(0, sdf.parse("2008-01-10 00:00:00"))
									.setParameter(1, sdf.parse("2008-02-15 23:59:59"))
			       					.list();
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
