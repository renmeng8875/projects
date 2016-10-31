package com.bjsxt.hibernate;

import org.hibernate.Session;

public class InitData {

	public static void main(String[] args) {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			Classes classes = new Classes();
			classes.setName("ÉÐÑ§ÌÃ");
			session.save(classes);
			
			Student student1 = new Student();
			student1.setName("10");
			student1.setClasses(classes);
			session.save(student1);
			
			Student student2 = new Student();
			student2.setName("×æ¶ù");
			student2.setClasses(classes);
			session.save(student2);
			
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			HibernateUtils.closeSession(session);
		}
	}	
}
