package com.bjsxt.hibernate;

import org.hibernate.Session;

public class InitData {

	public static void main(String[] args) {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			Group group = new Group();
			group.setName("ÉÐÑ§ÌÃ");
			
			session.save(group);
			
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			HibernateUtils.closeSession(session);
		}
	}
}
