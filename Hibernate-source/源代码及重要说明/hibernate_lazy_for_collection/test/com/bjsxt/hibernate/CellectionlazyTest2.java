package com.bjsxt.hibernate;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import junit.framework.TestCase;

import org.hibernate.Session;

/**
 * 设置<class>标签上的lazy=false
 * @author Administrator
 *
 */
public class CellectionlazyTest2 extends TestCase {


	public void testLoad1() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			//会发出sql
			Classes classes = (Classes)session.load(Classes.class, 1);
			
			//不会发出sql
			System.out.println("classes.name=" + classes.getName());
			
			//不会发出sql
			Set students = classes.getStudents();
			
			//会发出sql
			for (Iterator iter=students.iterator(); iter.hasNext();) {
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
