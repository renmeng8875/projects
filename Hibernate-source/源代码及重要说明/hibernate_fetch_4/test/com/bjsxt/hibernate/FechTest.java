package com.bjsxt.hibernate;

import java.util.Iterator;

import org.hibernate.Session;

import junit.framework.TestCase;

public class FechTest extends TestCase {

	public void testFetch1() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			Classes classes = (Classes)session.load(Classes.class, 1);
			System.out.println("classes.name=" + classes.getName());
			for (Iterator iter=classes.getStudents().iterator(); iter.hasNext();) {
				Student student = (Student)iter.next();
				System.out.println("student.name=" + student.getName());
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
