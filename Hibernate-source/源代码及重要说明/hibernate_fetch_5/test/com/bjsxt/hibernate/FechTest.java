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
	
	/**
	 * Hibernate: select students0_.classesid as classesid1_, students0_.id as id1_, 
	 * students0_.id as id1_0_, students0_.name as name1_0_, 
	 * students0_.classesid as classesid1_0_ 
	 * from t_student students0_ 
	 * where students0_.classesid in (select classes0_.id from t_classes classes0_ where classes0_.id in (1 , 2 , 3))
	 */
	public void testFetch2() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			List classesList = session.createQuery("select c from Classes c where c.id in(1, 2, 3)").list();
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
