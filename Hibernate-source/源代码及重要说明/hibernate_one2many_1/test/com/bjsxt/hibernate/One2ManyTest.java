package com.bjsxt.hibernate;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import junit.framework.TestCase;

import org.hibernate.Session;

public class One2ManyTest extends TestCase {

	public void testSave1() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			Student student1 = new Student();
			student1.setName("10");
			session.save(student1);
			
			Student student2 = new Student();
			student2.setName("祖儿");
			session.save(student2);
			
			Set students = new HashSet();
			students.add(student1);
			students.add(student2);
			
			Classes classes = new Classes();
			classes.setName("尚学堂");
			classes.setStudents(students);
			
			//可以正确保存
			session.save(classes);
			
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			HibernateUtils.closeSession(session);
		}
	}	
	
	public void testLoad1() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			Classes classes = (Classes)session.load(Classes.class, 1);
			System.out.println("classes.name=" + classes.getName());
			Set students = classes.getStudents();
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
