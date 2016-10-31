package com.bjsxt.hibernate;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;

import junit.framework.TestCase;

/**
 * 对象导航查询
 * @author Administrator
 *
 */
public class ObjectNavQueryTest extends TestCase {

	public void testQuery1() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			List students = session.createQuery("select s.name from Student s where s.classes.name like '%1%'").list();
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
}
