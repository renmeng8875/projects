package com.bjsxt.hibernate;

import org.hibernate.Session;

import junit.framework.TestCase;

/**
 * DML风格的操作
 * @author Administrator
 *
 */
public class DMLQueryTest extends TestCase {

	public void testQuery1() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			session.createQuery("update Student s set s.name=? where s.id < ?")
					.setParameter(0, "李四")
					.setParameter(1, 5)
					.executeUpdate();
			
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			HibernateUtils.closeSession(session);
		}
	}		
}
