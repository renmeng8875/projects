package com.bjsxt.hibernate;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import junit.framework.TestCase;

import org.hibernate.Session;

/**
 * 设置集合上的lazy=false,其它默认
 * @author Administrator
 *
 */
public class CellectionlazyTest3 extends TestCase {


	public void testLoad1() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			//不会发出sql
			Classes classes = (Classes)session.load(Classes.class, 1);
			
			//会发出sql,会发出两条sql分别加载Classes和Student
			System.out.println("classes.name=" + classes.getName());
			
			//不会发出sql
			Set students = classes.getStudents();
			
			//不会发出sql
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
	
	public void testLoad2() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			//不会发出sql
			Classes classes = (Classes)session.load(Classes.class, 1);
			
			//会发出sql,会发出两条sql分别加载Classes和Student
			System.out.println("classes.name=" + classes.getName());
			
			//不会发出sql
			Set students = classes.getStudents();
			
			//不会发出sql
			System.out.println("student.count=" + students.size());
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			HibernateUtils.closeSession(session);
		}
	}		
}
