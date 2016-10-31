package com.bjsxt.hibernate;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;

import junit.framework.TestCase;

/**
 * Õ≥º∆≤È—Ø
 * @author Administrator
 *
 */
public class StatQueryTest extends TestCase {

	public void testQuery1() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
//			List students =session.createQuery("select count(*) from Student").list();
//			Long count = (Long)students.get(0);
//			System.out.println(count);

			Long count = (Long)session.createQuery("select count(*) from Student").uniqueResult();
			System.out.println(count);
			
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
			
			List students =session.createQuery("select c.name, count(s) from Student s join s.classes c " +
					"group by c.name order by c.name").list();
			for (Iterator iter=students.iterator(); iter.hasNext();) {
				Object[] obj = (Object[])iter.next();
				System.out.println(obj[0] + ", " + obj[1]);
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
