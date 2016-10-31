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
			
			List classesList = session.createQuery("select c from Classes c").list();
			for (Iterator iter=classesList.iterator(); iter.hasNext();) {
				Classes classes = (Classes)iter.next();
				System.out.println("classes.name=" + classes.getName());
				for (Iterator iter1=classes.getStudents().iterator(); iter1.hasNext();) {
					Student student = (Student)iter1.next();
					System.out.println("student.name=" + student.getName());
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
