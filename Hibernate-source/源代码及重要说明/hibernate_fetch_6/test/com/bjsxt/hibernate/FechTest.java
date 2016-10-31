package com.bjsxt.hibernate;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;

import junit.framework.TestCase;

public class FechTest extends TestCase {

	public void testFetch1() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			List students = session.createQuery("select s from Student s where s.id in(:ids)")
				   .setParameterList("ids", new Object[]{1, 11, 21, 31, 41, 51, 61, 71, 81, 91})
				   .list();
			for (Iterator iter=students.iterator(); iter.hasNext();) {
				Student  student = (Student)iter.next();
				System.out.println("student.name=" + student.getName());
				System.out.println("classes.name=" + student.getClasses().getName());
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
