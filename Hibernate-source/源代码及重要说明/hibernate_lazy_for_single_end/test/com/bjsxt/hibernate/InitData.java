package com.bjsxt.hibernate;

import org.hibernate.Session;

public class InitData {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			Group group = new Group();
			group.setName("尚学堂");
			
			session.save(group);
			
			User user1 = new User();
			user1.setName("菜10");
			user1.setGroup(group);
			
			User user2 = new User();
			user2.setName("容祖儿");
			user2.setGroup(group);
			
			//可以正确存储
			session.save(user1);
			session.save(user2);
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			HibernateUtils.closeSession(session);
		}
	}

}
